package ru.study.packets;

import java.util.List;

public class SResponsePacket extends AServerPacket {

    public SResponsePacket(int version, List<Long> people) {
        putInt(version);
        putInt(people.size());

        for (long id : people) {
            putLong(id);
        }
    }
}
