package it.polimi.ingsw.server.controller.clientmessage;

import com.fasterxml.jackson.annotation.JacksonInject;
import it.polimi.ingsw.server.controller.Client;

public class GetInitialGameRepresentationClientMessage extends ClientMessage {

    public GetInitialGameRepresentationClientMessage(
        @JacksonInject("client") Client client
    ) {
        super(client);
    }

}
