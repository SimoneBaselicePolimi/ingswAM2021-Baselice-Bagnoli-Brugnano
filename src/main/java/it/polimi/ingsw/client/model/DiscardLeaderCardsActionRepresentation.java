package it.polimi.ingsw.client.model;

public class DiscardLeaderCardsActionRepresentation extends GameActionRepresentation {
    private final PlayerRepresentation player;
    private final LeaderCardRepresentation leaderCard;

    public DiscardLeaderCardsActionRepresentation(PlayerRepresentation player, LeaderCardRepresentation leaderCard) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }

    public LeaderCardRepresentation getLeaderCard() {
        return leaderCard;
    }
}
