package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientFaithUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientFaithPathRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;

public class FaithUpdateHandler extends GameUpdateHandler<ClientFaithUpdate>{

    @Override
    public void handleGameUpdate(ClientFaithUpdate update, ClientGameContextRepresentation gameContextRepresentation) {
        ClientFaithPathRepresentation faithPathRepresentation = gameContextRepresentation.getFaithPath();
        faithPathRepresentation.getFaithPositions().put(update.player, update.faithPositions);
    }
}
