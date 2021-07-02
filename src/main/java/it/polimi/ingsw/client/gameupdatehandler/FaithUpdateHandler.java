package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientFaithUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientFaithPathRepresentation;

public class FaithUpdateHandler extends GameUpdateHandler<ClientFaithUpdate>{

    @Override
    public void handleGameUpdate(ClientFaithUpdate update, ClientManager clientManager) {
        ClientFaithPathRepresentation faithPathRepresentation = clientManager.getGameContextRepresentation().getFaithPath();
        faithPathRepresentation.getFaithPositions().put(update.player, update.faithPositions);
    }
}
