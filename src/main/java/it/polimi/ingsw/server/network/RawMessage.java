package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.controller.Client;

public class RawMessage {

    public static final int TYPE_SIZE = 1;
    public static final int FORMAT_SIZE = 1;
    public static final int LENGTH_SIZE = 4;

    public final byte type;
    public final byte valueFormat;
    public final int valueLength;
    public final byte[] value;

    public RawMessage(byte type, byte valueFormat, int valueLength, byte[] value) {
        this.type = type;
        this.valueFormat = valueFormat;
        this.valueLength = valueLength;
        this.value = value;
    }

}
