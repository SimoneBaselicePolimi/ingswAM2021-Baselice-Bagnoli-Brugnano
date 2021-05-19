package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerSetupStartedActionRepresentation;

public class SetupStartedAction extends GameAction{

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString("gameHistory.gameState.setup.initialMessage");
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerSetupStartedActionRepresentation();
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
