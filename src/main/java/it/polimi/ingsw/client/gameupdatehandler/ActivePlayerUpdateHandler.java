package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.gameupdate.ClientActivePlayerUpdate;

public class ActivePlayerUpdateHandler extends GameUpdateHandler<ClientActivePlayerUpdate> {

    @Override
    public void handleGameUpdate(ClientActivePlayerUpdate update, ClientManager clientManager) {
        clientManager.getGameContextRepresentation().setActivePlayer(update.newActivePlayer);
        if(update.newActivePlayer.equals(clientManager.getMyPlayer())) {
            clientManager.setGameState(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION);
        } else {
            clientManager.setGameState(GameState.ANOTHER_PLAYER_TURN);
        }
    }
}
