package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientLeaderCardsThePlayerOwnsUpdate;

public class LeaderCardsThePlayerOwnsUpdateHandler extends GameUpdateHandler<ClientLeaderCardsThePlayerOwnsUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardsThePlayerOwnsUpdate update, ClientManager clientManager) {
        clientManager.getGameContextRepresentation()
            .getPlayerContext(update.player).setLeaderCardsPlayerOwns(update.leaderCardsThePlayerOwns);
    }
}
