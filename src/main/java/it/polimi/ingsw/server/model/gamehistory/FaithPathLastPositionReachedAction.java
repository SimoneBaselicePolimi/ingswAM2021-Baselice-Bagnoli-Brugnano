package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerFaithPathLastPositionReachedActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public class FaithPathLastPositionReachedAction extends GameAction {
    private final Player player;

    public FaithPathLastPositionReachedAction(Player player) {
        this.player = player;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.faithPath.lastPositionReached",
            player.getName()
        );

    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerFaithPathLastPositionReachedActionRepresentation(player);
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
