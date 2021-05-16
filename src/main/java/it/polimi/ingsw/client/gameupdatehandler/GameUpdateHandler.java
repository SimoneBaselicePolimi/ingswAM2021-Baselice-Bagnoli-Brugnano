package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientGameUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;

public abstract class GameUpdateHandler<U extends ClientGameUpdate> {

    public abstract void handleGameUpdate(U update, GameContextRepresentation gameContextRepresentation);

}
