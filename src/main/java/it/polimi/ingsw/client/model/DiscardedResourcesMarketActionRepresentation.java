package it.polimi.ingsw.client.model;

public class DiscardedResourcesMarketActionRepresentation extends GameActionRepresentation {

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
