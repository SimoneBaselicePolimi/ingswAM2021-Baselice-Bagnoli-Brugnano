package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamehistory.GameAction;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.Set;

public class GameHistoryNotifier extends GameHistory implements Notifier {

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        return null;
    }

    @Override
    public void addAction (GameAction gameAction){
    }
}
