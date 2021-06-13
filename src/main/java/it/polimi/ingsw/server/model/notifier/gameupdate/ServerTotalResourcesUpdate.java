package it.polimi.ingsw.server.model.notifier.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Map;

public class ServerTotalResourcesUpdate extends ServerGameUpdate{

    @SerializeIdOnly
    public Player player;

    public Map<ResourceType, Integer> totalResources;

    public ServerTotalResourcesUpdate(
        @JsonProperty("player") Player player,
        @JsonProperty("resources") Map<ResourceType, Integer> totalResources
    ) {
        this.player = player;
        this.totalResources = totalResources;
    }

}
