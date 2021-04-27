package it.polimi.ingsw.configfile;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.List;

public class LeaderCardsConfig {
    public final List<LeaderCardConfig> leaderCards;

    public LeaderCardsConfig(List<LeaderCardConfig> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public static class LeaderCardConfig {
        public final String leaderCardID;

        public final List<RequirementConfig> requirements;

        public final List<DevelopmentCardCostDiscountConfig> developmentCardCostDiscounts;
        public final List<WhiteMarbleSubstitutionConfig> specialMarbleSubstitutions;
        public final List<ResourceStorageConfig> resourceStorages;
        public final List<ProductionConfig> productions;

        public final int victoryPoints;

        public LeaderCardConfig(String leaderCardID, List<RequirementConfig> requirements,
                                List<DevelopmentCardCostDiscountConfig> developmentCardCostDiscounts,
                                List<WhiteMarbleSubstitutionConfig> specialMarbleSubstitutions,
                                List<ResourceStorageConfig> resourceStorages,
                                List<ProductionConfig> productions,
                                int victoryPoints) {
            this.leaderCardID = leaderCardID;
            this.requirements = requirements;
            this.developmentCardCostDiscounts = developmentCardCostDiscounts;
            this.specialMarbleSubstitutions = specialMarbleSubstitutions;
            this.resourceStorages = resourceStorages;
            this.productions = productions;
            this.victoryPoints = victoryPoints;
        }

        public static abstract class RequirementConfig {

        }

        public static class ColourRequirementConfig extends RequirementConfig {
            public final DevelopmentCardColour cardColour;

            public final int numberOfCards;

            public ColourRequirementConfig(DevelopmentCardColour cardColour, int numberOfCards) {
                this.cardColour = cardColour;
                this.numberOfCards = numberOfCards;
            }
        }

        public static class ColourAndLevelRequirementConfig extends RequirementConfig {
            public final DevelopmentCardColour cardColour;
            public final DevelopmentCardLevel cardLevel;
            public final int numberOfCards;

            public ColourAndLevelRequirementConfig(DevelopmentCardColour cardColour, DevelopmentCardLevel cardLevel, int numberOfCards) {
                this.cardColour = cardColour;
                this.cardLevel = cardLevel;
                this.numberOfCards = numberOfCards;
            }
        }

        public static class ResourceRequirementConfig extends RequirementConfig {
            public final ResourceType resourceType;

            public final int numberOfResources;

            public ResourceRequirementConfig(ResourceType resourceType, int numberOfResources) {
                this.resourceType = resourceType;
                this.numberOfResources = numberOfResources;
            }
        }

        public static class DevelopmentCardCostDiscountConfig {
            public final ResourceType resourceType;
            public final int amountToDiscount;

            public DevelopmentCardCostDiscountConfig(ResourceType resourceType, int amountToDiscount) {
                this.resourceType = resourceType;
                this.amountToDiscount = amountToDiscount;
            }
        }

        public static class WhiteMarbleSubstitutionConfig {
            public final ResourceType resourceType;

            public WhiteMarbleSubstitutionConfig(ResourceType resourceType) {
                this.resourceType = resourceType;
            }
        }
    }
}