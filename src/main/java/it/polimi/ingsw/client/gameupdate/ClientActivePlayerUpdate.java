package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.ActivePlayerUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.model.PlayerRepresentation;

public class ClientActivePlayerUpdate extends ClientGameUpdate {
    public final PlayerRepresentation newActivePlayer;

    public ClientActivePlayerUpdate(PlayerRepresentation newActivePlayer) {
        this.newActivePlayer = newActivePlayer;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new ActivePlayerUpdateHandler();
    }
}
