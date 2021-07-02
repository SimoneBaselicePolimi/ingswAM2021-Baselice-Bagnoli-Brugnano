package it.polimi.ingsw.client.servermessage;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class PlayerDisconnectedServerMessage {

    @SerializeIdOnly
    public final Player player;

    public PlayerDisconnectedServerMessage(Player player) {
        this.player = player;
    }

}
