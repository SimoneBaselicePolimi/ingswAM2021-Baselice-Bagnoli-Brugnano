package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.TempStarResourcesUpdateHandler;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientTempStarResourcesUpdate extends ClientGameUpdate{

    @SerializeIdOnly
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
