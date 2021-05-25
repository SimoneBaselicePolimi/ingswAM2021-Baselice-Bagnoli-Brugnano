package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerMarbleColourRepresentation;

public class ServerObtainedMarblesMarketActionRepresentation extends ServerGameActionRepresentation {

    public final Player player;
    public final ServerMarbleColourRepresentation[] marbleColours;

    public ServerObtainedMarblesMarketActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("marbleColours") ServerMarbleColourRepresentation[] marbleColours
    ) {
        this.player = player;
        this.marbleColours = marbleColours;
    }
}
