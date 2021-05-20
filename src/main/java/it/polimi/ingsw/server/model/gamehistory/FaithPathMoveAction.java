package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerFaithPathMoveActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public class FaithPathMoveAction extends GameAction {
    private final Player player;
    private final int steps;

    public FaithPathMoveAction(Player player, int steps) {
        this.player = player;
        this.steps = steps;
    }

    @Override
    public String getActionMessage() {
        if (steps == 1)
            return Localization.getLocalizationInstance().getString(
                "gameHistory.faithPath.playerMove.singular",
                player.getName()
            );
        else
            return Localization.getLocalizationInstance().getString(
                "gameHistory.faithPath.playerMove.plural",
                player.getName(),
                steps
            );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerFaithPathMoveActionRepresentation(player.getServerRepresentation(), steps);
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
