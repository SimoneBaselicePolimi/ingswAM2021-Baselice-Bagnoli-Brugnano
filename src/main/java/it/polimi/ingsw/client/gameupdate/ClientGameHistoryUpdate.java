package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameHistoryUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.model.GameActionRepresentation;

import java.util.List;

public class ClientGameHistoryUpdate extends ClientGameUpdate {
    public final List<GameActionRepresentation> newGameActions;

    public ClientGameHistoryUpdate(List<GameActionRepresentation> newGameActions) {
        this.newGameActions = newGameActions;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new GameHistoryUpdateHandler();
    }
}

