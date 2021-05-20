package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerPostTurnFinalActionRepresentation;

public class PostTurnFinalAction extends GameAction{
    private final Player player;

    public PostTurnFinalAction(Player player) {
        this.player = player;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.gameState.postTurn.finalMessage",
            player.getName()
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerPostTurnFinalActionRepresentation(player.getServerRepresentation());
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
