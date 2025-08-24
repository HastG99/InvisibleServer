package ru.study.objects;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.study.packets.*;
import ru.study.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Session implements Runnable {

    public static final Logger LOGGER = LogManager.getLogger(Session.class);

    private boolean logged = false;

    private final Socket socket;
    private final Server server;

    private OutputStream os;
    private InputStream is;

    private long lastKeepAlive = 0;
    private int delay = 0;

    private int version;
    private long id;


    public void handleData(byte packetId, byte[] bytes) {
        if(!logged) {
            if(packetId == 0x0) {
                CHandshakePacket packet = new CHandshakePacket(bytes);

                setId(packet.getId());
                setVersion(packet.getVersion());

                LOGGER.debug("Received handshake packet: id={}, ver={}", id, version);

                this.lastKeepAlive = System.currentTimeMillis();
                server.addSession(this);
                setLogged(true);

                sendPacket(0x0, new SHandshakePacket(server.VERSION, server.ID));

                LOGGER.info("USER CONNECTED ({} v{})", id, version);

                return;
            }

            if(packetId == 0x3) {
                CPingPacket packet = new CPingPacket(bytes);

                LOGGER.debug("Received ping packet: ver={}", packet.getVersion());

                List<Long> ids = new ArrayList<>();

                server.getSessions().forEach(session -> {
                    if(session.isLogged())
                        ids.add(session.getId());
                });

                sendPacket(0x3, new SResponsePacket(server.VERSION, ids));

                LOGGER.info("PING REQUESTED");

                return;
            }

            disconnect();

            return;
        }

        switch (packetId) {
            case 0x1: {
                CKeepAlivePacket packet = new CKeepAlivePacket(bytes);
                setDelay((int) (System.currentTimeMillis() - lastKeepAlive));

                LOGGER.debug("Received keep alive packet: payload={}, delay={}ms", packet.getPayload(), delay);

                List<Long> ids = new ArrayList<>();

                server.getSessions().forEach(session -> {
                    if(session.isLogged())
                        ids.add(session.getId());
                });

                sendPacket(0x3, new SResponsePacket(server.VERSION, ids));

                return;
            }

            case 0x2: {
                CMessagePacket packet = new CMessagePacket(bytes);

                LOGGER.debug("Received message packet: {}", Arrays.toString(packet.getMessage()));

                server.message(id, packet.getMessage());
                return;
            }
        }

        LOGGER.warn("Invalid packetId: {}\n", packetId);
    }

    public void sendKeepAlive() {
        long current = System.currentTimeMillis();

        if(current - lastKeepAlive > 30_000) {
            LOGGER.info("Client delay is too long: delay={}ms", current - lastKeepAlive);

            disconnect();
            return;
        }

        lastKeepAlive = current;

        sendPacket(0x1, new SKeepAlivePacket(lastKeepAlive));
    }

    @Override
    public void run() {
        try {
            this.is = socket.getInputStream();
            this.os = socket.getOutputStream();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        DataInputStream dis = new DataInputStream(is);
        while(!socket.isClosed()) {

            try {
                if(is.available() > 0) {

                    byte packetId = dis.readByte();
                    int len = dis.readInt();

                    if (len < 1)
                        throw new IOException("len < 1");

                    byte[] bytes = new byte[len];

                    if (dis.read(bytes) == -1)
                        throw new IOException("read = -1");

                    handleData(packetId, bytes);
                }

                Thread.sleep(50);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                LOGGER.error(e);
                disconnect();
            }
        }
    }

    public void sendPacket(int packetId, AServerPacket packet) {
        try {
            DataOutputStream dos = new DataOutputStream(os);

            byte[] bytes = packet.getData();

            dos.writeByte(packetId);
            dos.writeInt(bytes.length);
            dos.write(bytes);
            dos.flush();
        } catch (Exception e) {
            disconnect();
        }
    }

    public void disconnect() {
        LOGGER.info("Client disconnected: {}", id);

        try {
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        server.removeSession(this);


    }

}
