package ru.study.packets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class AServerPacket {

    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final DataOutputStream wrapper = new DataOutputStream(buffer);


    protected void putByte(final byte value) {
        try {
            buffer.write(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putInt(final int value) {
        try {
            wrapper.writeInt(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putFloat(final float value) {
        try {
            wrapper.writeFloat(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putBoolean(final boolean value) {
        try {
            wrapper.writeBoolean(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putDouble(final double value) {
        try {
            wrapper.writeDouble(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putShort(final int value) {
        try {
            wrapper.writeShort(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putLong(final long value) {
        try {
            wrapper.writeLong(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putString(final String value) {
        try {
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

            wrapper.writeInt(bytes.length);
            buffer.write(bytes);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putData(final byte[] value) {
        try {
            wrapper.writeInt(value.length);
            buffer.write(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }


    public byte[] getData() {
        try {
            wrapper.flush();
            return buffer.toByteArray();
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
