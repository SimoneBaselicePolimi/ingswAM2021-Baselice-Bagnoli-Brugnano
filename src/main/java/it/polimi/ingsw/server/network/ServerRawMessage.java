package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.controller.Client;

public class ServerRawMessage extends RawMessage {

    final Client receiver;

    public ServerRawMessage(
        Client receiver,
        byte type,
        byte valueFormat,
        int valueLength,
        byte[] value
    ) {
        super(type, valueFormat, valueLength, value);
        this.receiver = receiver;
    }

}
