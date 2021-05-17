package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.ActivePlayerUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientActivePlayerUpdate extends ClientGameUpdate {
    public final ClientPlayerRepresentation newActivePlayer;

    public ClientActivePlayerUpdate(ClientPlayerRepresentation newActivePlayer) {
        this.newActivePlayer = newActivePlayer;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new ActivePlayerUpdateHandler();
    }
}
