package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class ClientSetupChoiceActionRepresentation extends ClientGameActionRepresentation {
    private final Player player;
    private final Map<ResourceType,Integer> initialResources;

    public ClientSetupChoiceActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("initialResources") Map<ResourceType, Integer> initialResources
    ) {
        this.player = player;
        this.initialResources = initialResources;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<ResourceType, Integer> getInitialResources() {
        return initialResources;
    }
}
