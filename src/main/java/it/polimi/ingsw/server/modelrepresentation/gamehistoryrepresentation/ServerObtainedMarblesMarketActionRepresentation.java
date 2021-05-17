package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerMarbleColourRepresentation;

public class ServerObtainedMarblesMarketActionRepresentation extends ServerGameActionRepresentation {
    public final ServerPlayerRepresentation player;
    public final ServerMarbleColourRepresentation[] marbleColours;

    public ServerObtainedMarblesMarketActionRepresentation(
        ServerPlayerRepresentation player,
        ServerMarbleColourRepresentation[] marbleColours
    ) {
        this.player = player;
        this.marbleColours = marbleColours;
    }
}
