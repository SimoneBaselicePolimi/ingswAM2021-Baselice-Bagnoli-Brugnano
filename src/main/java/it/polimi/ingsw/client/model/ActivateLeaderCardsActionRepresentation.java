package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gamehistory.GameAction;

public class ActivateLeaderCardsActionRepresentation extends GameAction {

    private final PlayerRepresentation player;
    private final LeaderCardRepresentation leaderCard;

    public ActivateLeaderCardsActionRepresentation(PlayerRepresentation player, LeaderCardRepresentation leaderCard) {
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
