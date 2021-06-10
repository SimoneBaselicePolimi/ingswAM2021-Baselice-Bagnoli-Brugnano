package it.polimi.ingsw.server.model.notifier.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

public class ServerTempStarResourcesUpdate extends ServerGameUpdate{

    public final Player player;
    public final int tempStarResources;

    public ServerTempStarResourcesUpdate(
        @JsonProperty("player") Player player,
        @JsonProperty("tempStarResources") int tempStarResources
    ) {
        this.player = player;
        this.tempStarResources = tempStarResources;
    }
}
