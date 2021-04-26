package it.polimi.ingsw.configfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

class ProductionConfig {

    // TODO: wrapper classi


    @JsonProperty("resources")
    public final Map<ResourceType, Integer> costResources;

    @JsonProperty("starResources")
    public final int costStarResources;

    @JsonProperty("resources")
    public final Map<ResourceType, Integer> rewardResources;

    @JsonProperty("starResources")
    public final int rewardStarResources;

    public final int faithPoints;

    public ProductionConfig(Map<ResourceType, Integer> costResources, int costStarResources,
                            Map<ResourceType, Integer> rewardResources, int rewardStarResources, int faithPoints) {
        this.costResources = costResources;
        this.costStarResources = costStarResources;
        this.rewardResources = rewardResources;
        this.rewardStarResources = rewardStarResources;
        this.faithPoints = faithPoints;
    }
}
