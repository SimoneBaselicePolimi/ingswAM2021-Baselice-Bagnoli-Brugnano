package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.TotalResourcesUpdateHandler;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Map;

public class ClientTotalResourcesUpdate extends ClientGameUpdate{

    @SerializeIdOnly
    public Player player;

    public Map<ResourceType, Integer> totalResources;

    public ClientTotalResourcesUpdate(
        @JsonProperty("player") Player player,
        @JsonProperty("resources") Map<ResourceType, Integer> totalResources
    ) {
        this.player = player;
        this.totalResources = totalResources;
    }

    @Override
    public GameUpdateHandler getHandler() {
        return new TotalResourcesUpdateHandler();
    }
}
