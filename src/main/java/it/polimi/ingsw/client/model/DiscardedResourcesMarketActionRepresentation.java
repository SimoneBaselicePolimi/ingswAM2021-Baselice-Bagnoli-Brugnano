package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gamehistory.GameAction;

public class DiscardedResourcesMarketActionRepresentation extends GameAction {

    private final PlayerRepresentation player;
    private final int numberOfResourcesDiscarded;

    public DiscardedResourcesMarketActionRepresentation(PlayerRepresentation player, int numberOfResourcesDiscarded) {
        this.player = player;
        this.numberOfResourcesDiscarded = numberOfResourcesDiscarded;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }

    public int getNumberOfResourcesDiscarded() {
        return numberOfResourcesDiscarded;
    }
}
