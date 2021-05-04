package it.polimi.ingsw.configfile;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

/**
 * This class sets all the parameters regarding the Production powers given to the Player by different types
 * of Game Items (Leader Cards, Development Cards). The Player can pay specific costs to obtain the specified rewards.
 */
public class ProductionConfig {

    /**
     * Cost the Player must pay to activate this Production power
     */
    public final CostConfig costs;

    /**
     * Reward the Player obtains by activating this Production power
     */
    public final RewardConfig rewards;

    /**
     * ProductionConfig constructor.
     * @param costs cost the Player must pay to activate this Production power
     * @param rewards reward the Player obtains by activating this Production power
     */
    public ProductionConfig(CostConfig costs, RewardConfig rewards) {
        this.costs = costs;
        this.rewards = rewards;
    }

    /**
     * This class contains the configuration schema of the Cost of a specific Production power.
     */
    public static class CostConfig {

        /**
         * Cost made of specific type and number of Resources
         */
        public final Map<ResourceType, Integer> resources;

        /**
         * Cost made of a generic type of Resource (Player can choose), in a fixed quantity
         */
        public final int starResources;

        /**
         * CostConfig constructor.
         * @param resources cost made of specific type and number of Resources
         * @param starResources cost made of a generic type of Resource (Player can choose), in a fixed quantity
         */
        public CostConfig(Map<ResourceType, Integer> resources, int starResources) {
            this.resources = resources;
            this.starResources = starResources;
        }
    }

    /**
     * This class contains the configuration schema of the Reward of a Production power.
     */
    public static class RewardConfig {

        /**
         * Reward made of specific type and number of Resources
         */
        public final Map<ResourceType, Integer> resources;

        /**
         * Reward made of a generic type of Resource (Player can choose), in a fixed quantity
         */
        public final int starResources;

        /**
         * Reward made of a fixed number of Faith Points
         */
        public final int faithPoints;

        /**
         * RewardConfig constructor.
         * @param resources reward made of specific type and number of Resources
         * @param starResources reward made of a generic type of Resource (Player can choose), in a fixed quantity
         * @param faithPoints reward made of a fixed number of Faith Points
         */
        public RewardConfig(Map<ResourceType, Integer> resources, int starResources, int faithPoints) {
            this.resources = resources;
            this.starResources = starResources;
            this.faithPoints = faithPoints;
        }
    }
}
