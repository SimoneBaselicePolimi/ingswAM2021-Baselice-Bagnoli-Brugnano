package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.gamehistory.GameAction;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameHistoryUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameHistoryObservableProxy extends ObservableProxy<GameHistory> implements GameHistory{

    protected int firstNewActionIndex = 0;

    public GameHistoryObservableProxy(GameHistory imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public List<GameAction> getGameHistory() {
        return imp.getGameHistory();
    }

    @Override
    public void addAction(GameAction gameAction) {
        imp.addAction(gameAction);
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        Set<ServerGameUpdate> updates;
        if (firstNewActionIndex != imp.getGameHistory().size()) {
            updates = Set.of(new ServerGameHistoryUpdate(
                imp.getGameHistory().subList(firstNewActionIndex, imp.getGameHistory().size()))
            );
            firstNewActionIndex = imp.getGameHistory().size();
        } else {
            updates = new HashSet<>();
        }
        return updates;
    }

}
