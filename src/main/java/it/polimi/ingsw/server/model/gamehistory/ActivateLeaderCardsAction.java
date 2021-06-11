package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;

public class ActivateLeaderCardsAction extends GameAction {
    private final Player player;
    private final LeaderCard leaderCard;

    public ActivateLeaderCardsAction(Player player, LeaderCard leaderCard) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.leaderCard.activateCard",
            player,
            leaderCard
        );
    }

}
