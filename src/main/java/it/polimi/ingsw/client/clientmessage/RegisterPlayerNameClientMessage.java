package it.polimi.ingsw.client.clientmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RegisterPlayerNameClientMessage")
public class RegisterPlayerNameClientMessage extends ClientMessage {

    public final String playerName;

    public RegisterPlayerNameClientMessage(@JsonProperty("playerName") String playerName) {
        this.playerName = playerName;
    }

}
