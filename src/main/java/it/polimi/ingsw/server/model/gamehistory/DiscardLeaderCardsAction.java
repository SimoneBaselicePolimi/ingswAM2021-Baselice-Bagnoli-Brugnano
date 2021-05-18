package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerDiscardLeaderCardsActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

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

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerDiscardLeaderCardsActionRepresentation(
            player.getServerRepresentation(),
            leaderCard.getServerRepresentation()
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
