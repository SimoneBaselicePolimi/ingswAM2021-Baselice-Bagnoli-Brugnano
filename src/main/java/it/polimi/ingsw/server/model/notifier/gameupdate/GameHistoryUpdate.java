package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gamehistory.GameAction;

import java.util.List;

public class GameHistoryUpdate extends GameUpdate {
    public final List<GameAction> newGameActions;

    public GameHistoryUpdate(List<GameAction> newGameActions) {
        this.newGameActions = newGameActions;
    }
}

