package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerActivateLeaderCardsActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public class ActivateLeaderCardsAction extends GameAction {
    private final Player player;
    private final LeaderCard leaderCard;

    public ActivateLeaderCardsAction(
        @JsonProperty("player") Player player,
        @JsonProperty("leaderCard") LeaderCard leaderCard
    ) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.leaderCard.activateCard",
            player,
            leaderCard
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerActivateLeaderCardsActionRepresentation(
            player,
            leaderCard.getServerRepresentation());
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
