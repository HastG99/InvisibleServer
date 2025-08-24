package ru.study.packets;

public class SHandshakePacket extends AServerPacket{

    public SHandshakePacket(int version, long id) {
        putInt(version);
        putLong(id);
    }

}
