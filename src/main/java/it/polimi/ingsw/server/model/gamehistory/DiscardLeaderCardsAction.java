package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;

import java.util.Set;

public class DiscardLeaderCardsAction extends GameAction {
    private final Player player;
    private final Set<LeaderCard> leaderCards;

    public DiscardLeaderCardsAction(Player player, Set<LeaderCard> leaderCards) {
        this.player = player;
        this.leaderCards = leaderCards;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString(
            "gameHistory.leaderCard.discardCard",
            player,
            leaderCards
        );
    }
}
