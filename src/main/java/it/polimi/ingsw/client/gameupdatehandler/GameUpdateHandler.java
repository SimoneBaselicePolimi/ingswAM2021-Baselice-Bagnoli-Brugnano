package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

public abstract class GameUpdateHandler<U extends ServerGameUpdate> {

    public abstract void handleGameUpdate(U update, GameContextRepresentation gameContextRepresentation);

}
