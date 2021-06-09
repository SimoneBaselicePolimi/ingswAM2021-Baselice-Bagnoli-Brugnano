package it.polimi.ingsw.server.model.notifier.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerTempStarResourcesUpdate extends ServerGameUpdate{

    public final int tempStarResources;

    public ServerTempStarResourcesUpdate(
        @JsonProperty("tempStarResources") int tempStarResources
    ) {
        this.tempStarResources = tempStarResources;
    }
}
