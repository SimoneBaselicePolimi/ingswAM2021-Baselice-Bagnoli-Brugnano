package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameHistoryUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.server.model.gamehistory.GameAction;

import java.util.List;

public class ClientGameHistoryUpdate extends ClientGameUpdate {
    public final List<GameAction> newGameActions;

    public ClientGameHistoryUpdate(List<GameAction> newGameActions) {
        this.newGameActions = newGameActions;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new GameHistoryUpdateHandler();
    }
}

