package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientObtainedMarblesMarketActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;
    private final ClientMarbleColourRepresentation[] marbleColours;

    public ClientObtainedMarblesMarketActionRepresentation(ClientPlayerRepresentation player, ClientMarbleColourRepresentation[] marbleColours) {
        this.player = player;
        this.marbleColours = marbleColours;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }

    public ClientMarbleColourRepresentation[] getMarbleColours() {
        return marbleColours;
    }
}
