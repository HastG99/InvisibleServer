package ru.study.packets;

import ru.study.objects.Session;

public class SMessagePacket extends AServerPacket{

    public SMessagePacket(long userId, byte[] bytes) {
        putLong(userId);
        putLong(System.currentTimeMillis());
        putData(bytes);
    }
}
