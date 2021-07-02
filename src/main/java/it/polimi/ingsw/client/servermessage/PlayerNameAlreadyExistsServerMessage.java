package it.polimi.ingsw.client.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerNameAlreadyExistsServerMessage extends ServerMessage {

    public final String invalidPlayerName;

    public final String errorMessage;

    public PlayerNameAlreadyExistsServerMessage(
        @JsonProperty("invalidPlayerName") String playerName
    ) {
        this.invalidPlayerName = playerName;
        this.errorMessage = String.format("There is already a player registered with name %s", playerName);
    }

}
