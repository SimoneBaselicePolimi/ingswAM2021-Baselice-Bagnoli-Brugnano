package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.server.model.gamehistory.GameAction;

import java.util.List;

public class ClientClientGameHistoryUpdate extends ClientGameUpdate {
    public final List<GameAction> newGameActions;

    public ClientClientGameHistoryUpdate(List<GameAction> newGameActions) {
        this.newGameActions = newGameActions;
    }
}

