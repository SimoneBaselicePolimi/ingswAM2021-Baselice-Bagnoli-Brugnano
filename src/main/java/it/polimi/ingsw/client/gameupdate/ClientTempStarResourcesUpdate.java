package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.TempStarResourcesUpdateHandler;
import it.polimi.ingsw.server.model.Player;

public class ClientTempStarResourcesUpdate extends ClientGameUpdate{

    public final Player player;
    public final int tempStarResources;

    public ClientTempStarResourcesUpdate(
        @JsonProperty("player") Player player,
        @JsonProperty("tempStarResources") int tempStarResources
    ) {
        this.player = player;
        this.tempStarResources = tempStarResources;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new TempStarResourcesUpdateHandler();
    }

}
