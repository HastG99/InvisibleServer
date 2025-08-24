package ru.study.server;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.study.objects.Session;
import ru.study.packets.SMessagePacket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Data
public class Server implements Runnable {

    public final int VERSION = 1;
    public final long ID;

    private final List<Session> sessions = new CopyOnWriteArrayList<>();

    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private final int port;

    public Server(int port) {
        this.port = port;

        ID = generateId();
    }

    public static long generateId() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }

    public void run() {
        LOGGER.debug("Trying to start server: id={}, port={}", ID, port);

        try(ServerSocket serv = new ServerSocket(port)) {
            LOGGER.debug("Starting keep alive task . . .");

            new Thread(new KeepAliveTask(this)).start();

            LOGGER.debug("Server is running: {}", port);

            while(true) {
                Socket soc = serv.accept();
                Session session = new Session(soc, this);
                new Thread(session).start();
            }
        }catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            LOGGER.error(ex);
        }
    }

    public void message(long userId, byte[] bytes) {
        sessions.forEach(session -> session.sendPacket(0x2, new SMessagePacket(userId, bytes)));
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

}
