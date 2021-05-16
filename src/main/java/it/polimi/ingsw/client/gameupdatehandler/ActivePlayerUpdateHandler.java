package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientActivePlayerUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;

public class ActivePlayerUpdateHandler extends GameUpdateHandler<ClientActivePlayerUpdate> {

    @Override
    public void handleGameUpdate(ClientActivePlayerUpdate update, GameContextRepresentation gameContextRepresentation) {
        gameContextRepresentation.setActivePlayer(update.newActivePlayer);
    }
}
