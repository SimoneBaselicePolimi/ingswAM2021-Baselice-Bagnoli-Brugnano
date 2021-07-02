package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientGameHistoryUpdate;

public class GameHistoryUpdateHandler extends GameUpdateHandler<ClientGameHistoryUpdate>{

    @Override
    public void handleGameUpdate(ClientGameHistoryUpdate update, ClientManager clientManager) {
        update.newGameActions.forEach(action -> clientManager.getGameHistoryRepresentation().addGameAction(action));

    }
}
