package it.polimi.ingsw.server.controller.servermessage;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class PlayerDisconnectedServerMessage extends ServerMessage {

    @SerializeIdOnly
    public final Player player;

    public PlayerDisconnectedServerMessage(Player player) {
        this.player = player;
    }

}
