package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.controller.Client;

public class ClientRawMessage extends RawMessage{

    final Client sender;

    public ClientRawMessage(
        Client sender,
        byte type,
        byte valueFormat,
        int valueLength,
        byte[] value
    ) {
        super(type, valueFormat, valueLength, value);
        this.sender = sender;
    }

}
