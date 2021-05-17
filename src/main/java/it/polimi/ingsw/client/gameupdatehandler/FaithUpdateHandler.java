package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientFaithUpdate;
import it.polimi.ingsw.client.model.FaithPathRepresentation;
import it.polimi.ingsw.client.model.GameContextRepresentation;

public class FaithUpdateHandler extends GameUpdateHandler<ClientFaithUpdate>{

    @Override
    public void handleGameUpdate(ClientFaithUpdate update, GameContextRepresentation gameContextRepresentation) {
        FaithPathRepresentation faithPathRepresentation = gameContextRepresentation.getFaithPath();
        faithPathRepresentation.getFaithPositions().put(update.player, update.faithPositions);
    }
}
