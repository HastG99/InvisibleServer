package ru.study.packets;

public class CPingPacket extends AClientPacket {
    
    private int version;

    public CPingPacket(byte[] data) {
        super(data);
        
        version = getInt();
    }

    public int getVersion() {
        return version;
    }
}
