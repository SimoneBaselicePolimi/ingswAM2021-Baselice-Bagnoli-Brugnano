package it.polimi.ingsw.server.model.notifier.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO add as @JsonTypeInfo in ServerGameUpdate and create new corresponding class client side
public class ServerTempStarResourcesUpdate extends ServerGameUpdate{

    public final int tempStarResources;

    public ServerTempStarResourcesUpdate(
        @JsonProperty("tempStarResources") int tempStarResources
    ) {
        this.tempStarResources = tempStarResources;
    }
}
