package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientFaithUpdate;
import it.polimi.ingsw.client.model.FaithPathRepresentation;
import it.polimi.ingsw.client.model.GameContextRepresentation;

public class FaithUpdateHandler extends GameUpdateHandler<ClientFaithUpdate>{

    @Override
    public void handleGameUpdate(ClientFaithUpdate update, GameContextRepresentation gameContextRepresentation) {
        FaithPathRepresentation faithPathRepresentation = gameContextRepresentation.getFaithPath();
        //TODO: l'errore dovrebbe sparire dopo il merge della branch di Nati
        faithPathRepresentation.getFaithPositions().put(update.player, update.faithPositions);
    }
}
