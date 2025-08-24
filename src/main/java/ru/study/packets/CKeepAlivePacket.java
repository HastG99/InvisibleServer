package ru.study.packets;

public class CKeepAlivePacket extends AClientPacket{

    private long payload;

    public CKeepAlivePacket(byte[] data) {
        super(data);

        payload = getLong();
    }

    public long getPayload() {
        return payload;
    }
}
