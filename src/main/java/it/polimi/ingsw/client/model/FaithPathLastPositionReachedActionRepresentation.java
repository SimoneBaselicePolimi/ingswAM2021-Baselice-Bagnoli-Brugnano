package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gamehistory.GameAction;

public class FaithPathLastPositionReachedActionRepresentation extends GameAction {
    private final PlayerRepresentation player;

    public FaithPathLastPositionReachedActionRepresentation(PlayerRepresentation player) {
        this.player = player;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }
}
