package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientGameUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;

public abstract class GameUpdateHandler<U extends ClientGameUpdate> {

    public abstract void handleGameUpdate(U update, ClientGameContextRepresentation gameContextRepresentation);

}
