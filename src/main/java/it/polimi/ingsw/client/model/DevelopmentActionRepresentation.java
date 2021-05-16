package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gamehistory.GameAction;

public class DevelopmentActionRepresentation extends GameAction {

    private final PlayerRepresentation player;
    private final DevelopmentCardRepresentation developmentCard;

    public DevelopmentActionRepresentation(PlayerRepresentation player, DevelopmentCardRepresentation developmentCard) {
        this.player = player;
        this.developmentCard = developmentCard;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }

    public DevelopmentCardRepresentation getDevelopmentCard() {
        return developmentCard;
    }
}
