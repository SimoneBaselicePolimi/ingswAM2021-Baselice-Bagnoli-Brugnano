package it.polimi.ingsw.client.model;

public class FaithPathMoveActionRepresentation extends GameActionRepresentation {
    private final PlayerRepresentation player;
    private final int steps;

    public FaithPathMoveActionRepresentation(PlayerRepresentation player, int steps) {
        this.player = player;
        this.steps = steps;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }

    public int getSteps() {
        return steps;
    }
}
