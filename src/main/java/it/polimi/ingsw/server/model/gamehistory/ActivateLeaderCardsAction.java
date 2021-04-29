package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;

import java.util.Set;

public class ActivateLeaderCardsAction extends GameAction {
    private final Player player;
    private final Set<LeaderCard> leaderCards;

    public ActivateLeaderCardsAction(Player player, Set<LeaderCard> leaderCards) {
        this.player = player;
        this.leaderCards = leaderCards;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString(
            "gameHistory.leaderCard.activateCard",
            player,
            leaderCards
        );
    }
}
