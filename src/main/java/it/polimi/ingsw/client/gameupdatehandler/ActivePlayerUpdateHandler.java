package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientActivePlayerUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;

public class ActivePlayerUpdateHandler extends GameUpdateHandler<ClientActivePlayerUpdate> {

    @Override
    public void handleGameUpdate(ClientActivePlayerUpdate update, ClientGameContextRepresentation gameContextRepresentation) {
        gameContextRepresentation.setActivePlayer(update.newActivePlayer);
    }
}
