package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamehistory.GameAction;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameHistoryUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameHistoryRepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameHistoryObservableProxy extends ObservableProxy<GameHistory> implements GameHistory{

    protected boolean hasSomethingChanged = false;
    GameAction gameAction;

    public GameHistoryObservableProxy(GameHistory imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public ServerGameHistoryRepresentation getServerRepresentation() {
        return imp.getServerRepresentation();
    }

    @Override
    public ServerGameHistoryRepresentation getServerRepresentationForPlayer(Player player) {
        return imp.getServerRepresentationForPlayer(player);
    }

    @Override
    public List<GameAction> getGameHistory() {
        return imp.getGameHistory();
    }

    @Override
    public void addAction(GameAction gameAction) {
        this.gameAction = gameAction;
        hasSomethingChanged = true;
        imp.addAction(gameAction);
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
        if (hasSomethingChanged) {
            hasSomethingChanged = false;
            return Set.of(new ServerGameHistoryUpdate(List.of(gameAction)));
        }
        else
            return new HashSet<>();
    }
}
