package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientGameUpdate;

public abstract class GameUpdateHandler<U extends ClientGameUpdate> {

    public abstract void handleGameUpdate(U update, ClientManager clientManager);

}
