package it.polimi.ingsw.client.model;

public class FaithPathLastPositionReachedActionRepresentation extends GameActionRepresentation {
    private final PlayerRepresentation player;

    public FaithPathLastPositionReachedActionRepresentation(PlayerRepresentation player) {
        this.player = player;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }
}
