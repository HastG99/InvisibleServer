package ru.study.packets;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class AClientPacket {

    protected final ByteBuffer buffer;

    public AClientPacket(final byte[] data) {
        this.buffer = ByteBuffer.wrap(data);
    }

    protected int getByte() {
        return buffer.get();
    }

    protected int getInt() {
        return buffer.getInt();
    }

    protected long getLong() {
        return buffer.getLong();
    }

    protected float getFloat() {
        return buffer.getFloat();
    }

    protected boolean getBoolean() {
        return buffer.get() != 0;
    }

    protected double getDouble() {
        return buffer.getDouble();
    }

    protected short getShort() {
        return buffer.getShort();
    }

    protected byte[] getData() {
        int len = buffer.getInt();

        byte[] bytes = new byte[len];
        buffer.get(bytes);

        return bytes;
    }

    protected String getString() {
        int len = buffer.getInt();

        byte[] bytes = new byte[len];
        buffer.get(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }

}

