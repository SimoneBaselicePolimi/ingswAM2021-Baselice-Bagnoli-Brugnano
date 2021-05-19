package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameHistoryUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameActionRepresentation;

import java.util.List;

public class ClientGameHistoryUpdate extends ClientGameUpdate {
    public final List<ClientGameActionRepresentation> newGameActions;

    public ClientGameHistoryUpdate(List<ClientGameActionRepresentation> newGameActions) {
        this.newGameActions = newGameActions;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new GameHistoryUpdateHandler();
    }
}

