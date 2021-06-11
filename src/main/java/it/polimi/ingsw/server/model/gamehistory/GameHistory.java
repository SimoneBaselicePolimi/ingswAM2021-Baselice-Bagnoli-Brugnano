package it.polimi.ingsw.server.model.gamehistory;

import java.util.List;

public interface GameHistory {
    List<GameAction> getGameHistory();

    void addAction(GameAction gameAction);
}
