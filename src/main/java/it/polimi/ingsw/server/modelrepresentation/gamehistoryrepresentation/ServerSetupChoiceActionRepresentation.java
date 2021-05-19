package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

import java.util.Map;

public class ServerSetupChoiceActionRepresentation extends ServerGameActionRepresentation {
    public final ServerPlayerRepresentation player;
    public final Map<ResourceType,Integer> initialResources;

    public ServerSetupChoiceActionRepresentation(
        ServerPlayerRepresentation player,
        Map<ResourceType, Integer> initialResources
    ) {
        this.player = player;
        this.initialResources = initialResources;
    }
}
