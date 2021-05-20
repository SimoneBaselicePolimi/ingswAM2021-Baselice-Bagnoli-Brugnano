package it.polimi.ingsw.network.servermessage;

public class PlayerNameAlreadyExistsServerMessage extends ServerMessage {

    public final String invalidPlayerName;

    public final String errorMessage;

    public PlayerNameAlreadyExistsServerMessage(String playerName) {
        this.invalidPlayerName = playerName;
        this.errorMessage = String.format("There is already a player registered with name %s", playerName);
    }

}
