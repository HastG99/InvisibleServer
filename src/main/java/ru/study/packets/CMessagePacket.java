package ru.study.packets;

public class CMessagePacket extends AClientPacket {

    private final byte[] message;

    public CMessagePacket(byte[] data) {
        super(data);
        this.message = getData();
    }

    public byte[] getMessage() {
        return message;
    }
}
