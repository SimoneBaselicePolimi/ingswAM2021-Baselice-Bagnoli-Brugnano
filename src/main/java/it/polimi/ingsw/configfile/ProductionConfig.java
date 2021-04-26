package it.polimi.ingsw.configfile;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

class ProductionConfig {

    public final CostConfig costs;

    public final RewardConfig rewards;

    public ProductionConfig(CostConfig costs, RewardConfig rewards) {
        this.costs = costs;
        this.rewards = rewards;
    }

    public static class CostConfig {
        public final Map<ResourceType, Integer> resources;
        public final int starResources;

        public CostConfig(Map<ResourceType, Integer> resources, int starResources) {
            this.resources = resources;
            this.starResources = starResources;
        }
    }

    public static class RewardConfig {
        public final Map<ResourceType, Integer> resources;
        public final int starResources;
        public final int faithPoints;

        public RewardConfig(Map<ResourceType, Integer> resources, int starResources, int faithPoints) {
            this.resources = resources;
            this.starResources = starResources;
            this.faithPoints = faithPoints;
        }
    }
}
