package it.polimi.ingsw.client.model;

public class DevelopmentActionRepresentation extends GameActionRepresentation {

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
