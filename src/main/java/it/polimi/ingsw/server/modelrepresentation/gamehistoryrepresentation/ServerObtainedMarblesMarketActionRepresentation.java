package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerMarbleColourRepresentation;

public class ServerObtainedMarblesMarketActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;
    public final ServerMarbleColourRepresentation[] marbleColours;

    public ServerObtainedMarblesMarketActionRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player,
        @JsonProperty("marbleColours") ServerMarbleColourRepresentation[] marbleColours
    ) {
        this.player = player;
        this.marbleColours = marbleColours;
    }
}
