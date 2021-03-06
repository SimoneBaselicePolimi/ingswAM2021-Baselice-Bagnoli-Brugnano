package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.server.gameactionshistory.GameAction;

import java.util.List;

public interface GameHistory {
    List<GameAction> getGameHistory();

    void addAction(GameAction gameAction);
}
