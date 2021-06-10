package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.ResourcesUpdateHandler;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

@Deprecated
public class ClientResourcesUpdate extends ClientGameUpdate{

    public Map<ResourceType, Integer> resources;

    public ClientResourcesUpdate(
        @JsonProperty("resources") Map<ResourceType, Integer> resources
    ) {
        this.resources = resources;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new ResourcesUpdateHandler();
    }
}
