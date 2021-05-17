package it.polimi.ingsw.client.model;

public class ObtainedMarblesMarketActionRepresentation extends GameActionRepresentation {
    private final PlayerRepresentation player;
    private final MarbleColourRepresentation [] marbleColours;

    public ObtainedMarblesMarketActionRepresentation(PlayerRepresentation player, MarbleColourRepresentation[] marbleColours) {
        this.player = player;
        this.marbleColours = marbleColours;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }

    public MarbleColourRepresentation[] getMarbleColours() {
        return marbleColours;
    }
}
