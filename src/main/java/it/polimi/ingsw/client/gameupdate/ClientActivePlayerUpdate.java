package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.ActivePlayerUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.server.model.Player;

public class ClientActivePlayerUpdate extends ClientGameUpdate {
    public final Player newActivePlayer;

    public ClientActivePlayerUpdate(
        @JsonProperty("newActivePlayer") Player newActivePlayer
    ) {
        this.newActivePlayer = newActivePlayer;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new ActivePlayerUpdateHandler();
    }
}
