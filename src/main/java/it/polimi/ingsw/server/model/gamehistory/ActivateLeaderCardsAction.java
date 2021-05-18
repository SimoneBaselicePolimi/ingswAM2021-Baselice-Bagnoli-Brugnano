package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerActivateLeaderCardsActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public class ActivateLeaderCardsAction extends GameAction {
    private final Player player;
    private final LeaderCard leaderCard;

    public ActivateLeaderCardsAction(Player player, LeaderCard leaderCard) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString(
            "gameHistory.leaderCard.activateCard",
            player,
            leaderCard
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerActivateLeaderCardsActionRepresentation(
            player.getServerRepresentation(),
            leaderCard.getServerRepresentation());
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
