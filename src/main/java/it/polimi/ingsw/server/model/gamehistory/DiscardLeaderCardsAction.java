package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;

public class DiscardLeaderCardsAction extends GameAction {
    private final Player player;
    private final LeaderCard leaderCard;

    public DiscardLeaderCardsAction(Player player, LeaderCard leaderCard) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString(
            "gameHistory.leaderCard.discardCard",
            player,
            leaderCard
        );
    }
}
