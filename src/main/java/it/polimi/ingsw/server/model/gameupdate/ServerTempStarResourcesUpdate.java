package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ServerTempStarResourcesUpdate extends ServerGameUpdate{

    @SerializeIdOnly
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
