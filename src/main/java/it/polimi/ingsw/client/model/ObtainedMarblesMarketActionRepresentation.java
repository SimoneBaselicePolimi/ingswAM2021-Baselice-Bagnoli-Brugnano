package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gamehistory.GameAction;

public class ObtainedMarblesMarketActionRepresentation extends GameAction {
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
