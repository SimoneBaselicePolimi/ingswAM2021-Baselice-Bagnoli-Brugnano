package it.polimi.ingsw.client;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionsSelectionInfo {

    protected List<ClientProductionRepresentation> alreadySelectedProductions;
    protected Map<ResourceType, Integer> resourcesLeftToThePlayer;
    protected Map<ResourceType, Integer> totalStarResourcesProductionCost;
    protected Map<ResourceType, Integer> totalStarResourcesProductionReward;

    public ProductionsSelectionInfo(Map<ResourceType, Integer> playerResources) {
        alreadySelectedProductions = new ArrayList<>();
        this.resourcesLeftToThePlayer = playerResources;
        totalStarResourcesProductionCost = new HashMap<>();
        totalStarResourcesProductionReward = new HashMap<>();
    }

    public List<ClientProductionRepresentation> getAlreadySelectedProductions() {
        return alreadySelectedProductions;
    }

    public void addNewSelectedProduction(ClientProductionRepresentation production) {
        alreadySelectedProductions.add(production);
    }

    public void setAlreadySelectedProductions(List<ClientProductionRepresentation> alreadySelectedProductions) {
        this.alreadySelectedProductions = alreadySelectedProductions;
    }

    public Map<ResourceType, Integer> getResourcesLeftToThePlayer() {
        return resourcesLeftToThePlayer;
    }

    public void setResourcesLeftToThePlayer(Map<ResourceType, Integer> resourcesLeftToThePlayer) {
        this.resourcesLeftToThePlayer = resourcesLeftToThePlayer;
    }

    public Map<ResourceType, Integer> getTotalStarResourcesProductionCost() {
        return totalStarResourcesProductionCost;
    }

    public void setTotalStarResourcesProductionCost(Map<ResourceType, Integer> totalStarResourcesProductionCost) {
        this.totalStarResourcesProductionCost = totalStarResourcesProductionCost;
    }

    public Map<ResourceType, Integer> getTotalStarResourcesProductionReward() {
        return totalStarResourcesProductionReward;
    }

    public void setTotalStarResourcesProductionReward(Map<ResourceType, Integer> totalStarResourcesProductionReward) {
        this.totalStarResourcesProductionReward = totalStarResourcesProductionReward;
    }

}
