package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerMainTurnInitialActionRepresentation;

public class MainTurnInitialAction extends GameAction{
    private final Player player;

    public MainTurnInitialAction(@JsonProperty("player") Player player) {
        this.player = player;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.gameState.mainTurn.initialMessage",
            player.getName()
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerMainTurnInitialActionRepresentation(player);
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
