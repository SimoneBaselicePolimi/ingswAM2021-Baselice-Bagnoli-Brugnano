package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientLeaderCardStateUpdate;

public class LeaderCardStateUpdateHandler extends GameUpdateHandler<ClientLeaderCardStateUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardStateUpdate update, ClientManager clientManager) {
        update.leaderCard.setState(update.leaderCardState);
    }
}
