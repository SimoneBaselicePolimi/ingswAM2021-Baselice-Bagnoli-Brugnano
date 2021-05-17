package it.polimi.ingsw.client.model;

public class MainTurnInitialActionRepresentation extends GameActionRepresentation {
    private final PlayerRepresentation player;

    public MainTurnInitialActionRepresentation(PlayerRepresentation player) {
        this.player = player;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }
}
