package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gamehistory.GameAction;

import java.util.List;

public class ServerGameHistoryUpdate extends ServerGameUpdate {
    public final List<GameAction> newGameActions;

    public ServerGameHistoryUpdate(List<GameAction> newGameActions) {
        this.newGameActions = newGameActions;
    }
}

