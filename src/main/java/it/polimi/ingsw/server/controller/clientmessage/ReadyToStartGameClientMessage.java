package it.polimi.ingsw.server.controller.clientmessage;


import com.fasterxml.jackson.annotation.JacksonInject;
import it.polimi.ingsw.server.controller.Client;

public class ReadyToStartGameClientMessage extends ClientMessage {

    public ReadyToStartGameClientMessage(@JacksonInject Client client) {
        super(client);
    }

}
