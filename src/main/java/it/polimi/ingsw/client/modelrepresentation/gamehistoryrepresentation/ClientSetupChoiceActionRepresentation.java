package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ClientSetupChoiceActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;
    private final Map<ResourceType,Integer> initialResources;

    public ClientSetupChoiceActionRepresentation(
        @JsonProperty("player") ClientPlayerRepresentation player,
        @JsonProperty("initialResources") Map<ResourceType, Integer> initialResources
    ) {
        this.player = player;
        this.initialResources = initialResources;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }

    public Map<ResourceType, Integer> getInitialResources() {
        return initialResources;
    }
}
