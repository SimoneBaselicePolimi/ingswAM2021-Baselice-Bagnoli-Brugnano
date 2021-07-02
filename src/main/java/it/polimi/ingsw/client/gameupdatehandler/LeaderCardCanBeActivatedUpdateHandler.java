package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientLeaderCardCanBeActivatedUpdate;

public class LeaderCardCanBeActivatedUpdateHandler extends GameUpdateHandler<ClientLeaderCardCanBeActivatedUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardCanBeActivatedUpdate update, ClientManager clientManager) {
        update.leaderCard.setCanBeActivated(update.canBeActivated);
    }

}
