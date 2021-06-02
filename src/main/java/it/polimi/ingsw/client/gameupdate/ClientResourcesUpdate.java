package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.ResourcesUpdateHandler;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ClientResourcesUpdate extends ClientGameUpdate{

    public Map<ResourceType, Integer> resources;

    @Override
    public GameUpdateHandler getHandler() {
        return new ResourcesUpdateHandler();
    }
}
