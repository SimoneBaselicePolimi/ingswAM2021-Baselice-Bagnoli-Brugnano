package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
import it.polimi.ingsw.server.model.Player;

public class ClientObtainedMarblesMarketActionRepresentation extends ClientGameActionRepresentation {
    private final Player player;
    private final ClientMarbleColourRepresentation[] marbleColours;

    public ClientObtainedMarblesMarketActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("marbleColours") ClientMarbleColourRepresentation[] marbleColours
    ) {
        this.player = player;
        this.marbleColours = marbleColours;
    }

    public Player getPlayer() {
        return player;
    }

    public ClientMarbleColourRepresentation[] getMarbleColours() {
        return marbleColours;
    }
}
