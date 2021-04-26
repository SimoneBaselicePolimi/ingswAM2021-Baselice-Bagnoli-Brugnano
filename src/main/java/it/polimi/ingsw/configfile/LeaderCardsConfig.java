package it.polimi.ingsw.configfile;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.SerializeIdOnly;

import java.util.List;

public class LeaderCardsConfig {

    @SerializeIdOnly
    public final LeaderCard leaderCardID;

    public final List<RequirementConfig> requirements;

    public final List<DevelopmentCardCostDiscountConfig> developmentCardCostDiscounts;
    public final List<WhiteMarbleSubstitutionConfig> specialMarbleSubstitutions;
    public final List<ResourceStorageConfig> resourceStorages;
    public final List<ProductionConfig> productions;

    public final int victoryPoints;

    public LeaderCardsConfig(LeaderCard leaderCardID, List<RequirementConfig> requirements,
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

    public static class colorRequirementConfig extends RequirementConfig {
        public final DevelopmentCardColour cardColour;

        public final int numberOfCards;

        public colorRequirementConfig(DevelopmentCardColour cardColour, int numberOfCards) {
            this.cardColour = cardColour;
            this.numberOfCards = numberOfCards;
        }
    }

    public static class colorAndLevelRequirementConfig extends RequirementConfig {
        public final DevelopmentCardColour cardColour;
        public final DevelopmentCardLevel cardLevel;
        public final int numberOfCards;

        public colorAndLevelRequirementConfig(DevelopmentCardColour cardColour, DevelopmentCardLevel cardLevel, int numberOfCards) {
            this.cardColour = cardColour;
            this.cardLevel = cardLevel;
            this.numberOfCards = numberOfCards;
        }
    }

    public static class resourceRequirementConfig extends RequirementConfig {
        public final ResourceType resourceType;

        public final int numberOfResources;

        public resourceRequirementConfig(ResourceType resourceType, int numberOfResources) {
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

    public static class ResourceStorageConfig {

        public static class StorageConfig {
            public final List<RuleConfig> rules;

            public StorageConfig(List<RuleConfig> rules) {
                this.rules = rules;
            }

            public abstract static class RuleConfig {

            }

            public static class maxResourceNumberRuleConfig extends RuleConfig {
                public final int maxNumber;

                public maxResourceNumberRuleConfig(int maxNumber) {
                    this.maxNumber = maxNumber;
                }
            }

            public static class specificResourceTypeRuleConfig extends RuleConfig {
                public final ResourceType resourceType;

                public specificResourceTypeRuleConfig(ResourceType resourceType) {
                    this.resourceType = resourceType;
                }
            }
        }
    }

}