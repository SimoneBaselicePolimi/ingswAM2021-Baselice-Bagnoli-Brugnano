package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

import java.util.Map;

public class ServerSetupChoiceActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;
    public final Map<ResourceType,Integer> initialResources;

    public ServerSetupChoiceActionRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player,
        @JsonProperty("initialResources") Map<ResourceType, Integer> initialResources
    ) {
        this.player = player;
        this.initialResources = initialResources;
    }
}
