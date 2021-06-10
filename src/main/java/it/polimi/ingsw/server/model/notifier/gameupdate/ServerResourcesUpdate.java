package it.polimi.ingsw.server.model.notifier.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

@Deprecated
public class ServerResourcesUpdate {

    public Map<ResourceType, Integer> resources;

    public ServerResourcesUpdate(
        @JsonProperty("resources") Map<ResourceType, Integer> resources
    ) {
        this.resources = resources;
    }

}
