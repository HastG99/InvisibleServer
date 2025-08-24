package ru.study.packets;

public class SKeepAlivePacket extends AServerPacket {

    public SKeepAlivePacket(long payload) {
        putLong(payload);
    }
}
