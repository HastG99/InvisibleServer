package ru.study.packets;

public class CHandshakePacket extends AClientPacket {

    private final int version;
    private final long id;

    public CHandshakePacket(byte[] data) {
        super(data);

        version = getInt();
        id = getLong();
    }

    public int getVersion() {
        return version;
    }

    public long getId() {
        return id;
    }
}
