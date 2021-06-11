package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;

public class DiscardLeaderCardsAction extends GameAction {
    private final Player player;
    private final LeaderCard leaderCard;

    public DiscardLeaderCardsAction(
        @JsonProperty("player") Player player,
        @JsonProperty("leaderCard") LeaderCard leaderCard
    ) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.leaderCard.discardCard",
            player,
            leaderCard
        );
    }

}
