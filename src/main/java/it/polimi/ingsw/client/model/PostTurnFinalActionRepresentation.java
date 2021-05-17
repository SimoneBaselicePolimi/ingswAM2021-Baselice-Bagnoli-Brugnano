package it.polimi.ingsw.client.model;

public class PostTurnFinalActionRepresentation extends GameActionRepresentation {
    private final PlayerRepresentation player;

    public PostTurnFinalActionRepresentation(PlayerRepresentation player) {
        this.player = player;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }
}
