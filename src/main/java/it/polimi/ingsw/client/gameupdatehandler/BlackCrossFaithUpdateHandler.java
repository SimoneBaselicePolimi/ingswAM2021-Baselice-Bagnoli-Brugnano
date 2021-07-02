package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientBlackCrossFaithUpdate;

public class BlackCrossFaithUpdateHandler extends GameUpdateHandler<ClientBlackCrossFaithUpdate> {

    @Override
    public void handleGameUpdate(ClientBlackCrossFaithUpdate update, ClientManager clientManager) {
        if(clientManager.getGameContextRepresentation().isSinglePlayerGame()) {
            clientManager.getGameContextRepresentation().getSinglePlayerFaithPath().get()
                .setBlackCrossFaithPosition(update.blackCrossFaithPosition);
        }
    }

}
