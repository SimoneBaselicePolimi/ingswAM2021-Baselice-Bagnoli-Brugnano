package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientDiscardedResourcesMarketActionRepresentation extends ClientGameActionRepresentation {

    private final ClientPlayerRepresentation player;
    private final int numberOfResourcesDiscarded;

    public ClientDiscardedResourcesMarketActionRepresentation(ClientPlayerRepresentation player, int numberOfResourcesDiscarded) {
        this.player = player;
        this.numberOfResourcesDiscarded = numberOfResourcesDiscarded;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }

    public int getNumberOfResourcesDiscarded() {
        return numberOfResourcesDiscarded;
    }
}
