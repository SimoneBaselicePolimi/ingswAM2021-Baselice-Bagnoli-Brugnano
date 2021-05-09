package it.polimi.ingsw.configfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.leadercard.DevelopmentCardColourAndLevelRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.DevelopmentCardColourRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.ResourceNumberRequirement;

import java.util.List;

/**
 * This class sets all the parameters regarding the Leader Cards in the Game
 * from values stored in leader-cards-config.yml file.
 */
public class LeaderCardsConfig {

    /**
     * List of Leader Cards in the Game
     */
    public final List<LeaderCardConfig> leaderCards;

    /**
     * LeaderCardsConfig constructor.
     * @param leaderCards list of Leader Cards in the Game
     */
    public LeaderCardsConfig(
        @JsonProperty("leaderCards") List<LeaderCardConfig> leaderCards
    ) {
        this.leaderCards = leaderCards;
    }

    /**
     * This class contains the configuration schema of a Leader Card.
     */
    public static class LeaderCardConfig {

        /**
         * Unique ID which identifies this specific Leader Card
         */
        public final String leaderCardID;

        /**
         * List of requirements the Player who owns this Leader Card must satisfy
         * to activate this Card and obtain its special abilities.
         */
        public final List<RequirementConfig> requirements;

        /**
         * List of Development Card Cost Discount special abilities: the Player who owns this Leader Card,
         * when buying a Development Card, can pay its cost with a specified discount of the indicated Resource
         */
        public final List<DevelopmentCardCostDiscountConfig> developmentCardCostDiscounts;

        /**
         * List of Special Marble Substitution special abilities: when taking Resources from the Market,
         * the Player can choose which Resource to take from those given by this special ability for each special Marble
         */
        public final List<WhiteMarbleSubstitutionConfig> specialMarbleSubstitutions;

        /**
         * List of Special Leader Storages: extra depots used only to store specific Resources within a certain amount
         */
        public final List<ResourceStorageConfig> resourceStorages;

        /**
         * List of Production powers (the Player can pay specific costs to obtain specific rewards)
         */
        public final List<ProductionConfig> productions;

        /**
         * Number of Victory Points given at the end of the Game to the Player who owns this Leader Card
         */
        public final int victoryPoints;

        /**
         * LeaderCardConfig constructor.
         *
         * @param leaderCardID                 unique ID which identifies this specific Leader Card
         * @param requirements                 list of requirements the Player who owns this Leader Card must satisfy
         *                                     to activate this Card and obtain its special abilities.
         * @param developmentCardCostDiscounts list of Development Card Cost Discount special abilities
         * @param specialMarbleSubstitutions   list of Special Marble Substitution special abilities
         * @param resourceStorages             list of Special Leader Storages
         * @param productions                  list of Production powers
         * @param victoryPoints                number of Victory Points given at the end of the Game
         *                                     to the Player who owns this Leader Card
         */
        public LeaderCardConfig(
            @JsonProperty("leaderCardID") String leaderCardID,
            @JsonProperty("requirements") List<RequirementConfig> requirements,
            @JsonProperty("developmentCardCostDiscounts") List<DevelopmentCardCostDiscountConfig> developmentCardCostDiscounts,
            @JsonProperty("specialMarbleSubstitutions") List<WhiteMarbleSubstitutionConfig> specialMarbleSubstitutions,
            @JsonProperty("resourceStorages") List<ResourceStorageConfig> resourceStorages,
            @JsonProperty("productions") List<ProductionConfig> productions,
            @JsonProperty("victoryPoints") int victoryPoints
        ) {
            this.leaderCardID = leaderCardID;
            this.requirements = requirements;
            this.developmentCardCostDiscounts = developmentCardCostDiscounts;
            this.specialMarbleSubstitutions = specialMarbleSubstitutions;
            this.resourceStorages = resourceStorages;
            this.productions = productions;
            this.victoryPoints = victoryPoints;
        }

        /**
         * This class contains the configuration schema of the Requirement of a specific Leader Card, which the Player
         * who owns this Leader Card must satisfy to activate this Card and obtain its special abilities.
         */
        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "requirementType")
        @JsonSubTypes({
            @JsonSubTypes.Type(value = ColourRequirementConfig.class, name = "COLOUR"),
            @JsonSubTypes.Type(value = ColourAndLevelRequirementConfig.class, name = "COLOUR_AND_LEVEL"),
            @JsonSubTypes.Type(value = ResourceRequirementConfig.class, name = "RESOURCE")
        })
        public static abstract class RequirementConfig {

            /**
             * Abstract method to create a specific Requirement
             *
             * @return a LeaderCardRequirement
             */
            public abstract LeaderCardRequirement createRequirement();
        }

        /**
         * Colour Requirement.
         * Request for a specific number of Development Cards (even the covered ones) of a certain colour.
         */
        @JsonTypeName("COLOUR")
        public static class ColourRequirementConfig extends RequirementConfig {

            /**
             * Colour of Development Cards required
             */
            public final DevelopmentCardColour cardColour;

            /**
             * Number of Development Cards required
             */
            public final int numberOfCards;

            /**
             * ColourRequirementConfig constructor.
             *
             * @param cardColour    colour of Development Cards required
             * @param numberOfCards number of Development Cards required
             */
            public ColourRequirementConfig(
                @JsonProperty("cardColour") DevelopmentCardColour cardColour,
                @JsonProperty("numberOfCards") int numberOfCards
            ) {
                this.cardColour = cardColour;
                this.numberOfCards = numberOfCards;
            }

            /**
             * Specific implementation of the abstract method of RequirementConfig class
             *
             * @return a new DevelopmentCardColourRequirement
             */
            public LeaderCardRequirement createRequirement() {
                return new DevelopmentCardColourRequirement(cardColour, numberOfCards);
            }
        }

        /**
         * Colour and Level Requirement.
         * Request for a specific number of Development Cards (even the covered ones) of a certain colour and level.
         */
        @JsonTypeName("COLOUR_AND_LEVEL")
        public static class ColourAndLevelRequirementConfig extends RequirementConfig {

            /**
             * Colour of Development Cards required
             */
            public final DevelopmentCardColour cardColour;

            /**
             * Level of Development Cards required
             */
            public final DevelopmentCardLevel cardLevel;

            /**
             * Number of Development Cards required
             */
            public final int numberOfCards;

            /**
             * ColourAndLevelRequirementConfig constructor.
             *
             * @param cardColour    colour of Development Cards required
             * @param cardLevel     level of Development Cards required
             * @param numberOfCards number of Development Cards required
             */
            public ColourAndLevelRequirementConfig(
                @JsonProperty("cardColour") DevelopmentCardColour cardColour,
                @JsonProperty("cardLevel") DevelopmentCardLevel cardLevel,
                @JsonProperty("numberOfCards") int numberOfCards
            ) {
                this.cardColour = cardColour;
                this.cardLevel = cardLevel;
                this.numberOfCards = numberOfCards;
            }

            /**
             * Specific implementation of the abstract method of RequirementConfig class
             *
             * @return a new DevelopmentCardColourAndLevelRequirement
             */
            public LeaderCardRequirement createRequirement() {
                return new DevelopmentCardColourAndLevelRequirement(cardColour, cardLevel, numberOfCards);
            }
        }

        /**
         * Resource Requirement.
         * Request for a specific number of Resources to be present in the Player's Storages.
         */
        @JsonTypeName("RESOURCE")
        public static class ResourceRequirementConfig extends RequirementConfig {

            /**
             * Type of Resource required
             */
            public final ResourceType resourceType;

            /**
             * Number of Resources required
             */
            public final int numberOfResources;

            /**
             * ResourceRequirementConfig constructor.
             *
             * @param resourceType      type of Resource required
             * @param numberOfResources number of Resources required
             */
            public ResourceRequirementConfig(
                @JsonProperty("resourceType") ResourceType resourceType,
                @JsonProperty("numberOfResources") int numberOfResources
            ) {
                this.resourceType = resourceType;
                this.numberOfResources = numberOfResources;
            }

            /**
             * Specific implementation of the abstract method of RequirementConfig class
             *
             * @return a new ResourceNumberRequirement
             */
            public LeaderCardRequirement createRequirement() {
                return new ResourceNumberRequirement(resourceType, numberOfResources);
            }
        }

        /**
         * This class contains the configuration schema of the Development Card Cost Discount special ability.
         * The Player who owns this Leader Card, when buying a Development Card, can pay its cost
         * with a specified discount of the indicated Resource.
         */
        public static class DevelopmentCardCostDiscountConfig {

            /**
             * Resource type the Player can discount from the cost of a Development Card
             */
            public final ResourceType resourceType;

            /**
             * Number of resources the Player can discount from the cost of a Development Card
             */
            public final int amountToDiscount;

            /**
             * DevelopmentCardCostDiscountConfig constructor.
             *
             * @param resourceType     resource type the Player can discount from the cost of a Development Card
             * @param amountToDiscount number of resources the Player can discount from the cost of a Development Card
             */
            public DevelopmentCardCostDiscountConfig(
                @JsonProperty("resourceType") ResourceType resourceType,
                @JsonProperty("amountToDiscount") int amountToDiscount
            ) {
                this.resourceType = resourceType;
                this.amountToDiscount = amountToDiscount;
            }
        }

        /**
         * This class contains the configuration schema of the Special Marble Substitution special ability.
         * When taking Resources from the Market, the Player can choose which Resource to take
         * from those given by this special ability for each special Marble.
         */
        public static class WhiteMarbleSubstitutionConfig {

            /**
             * Resource type the Player can substitute to a Special Marble taken from the Market
             */
            public final ResourceType resourceType;

            /**
             * WhiteMarbleSubstitutionConfig constructor.
             *
             * @param resourceType resource type the Player can substitute to a Special Marble taken from the Market
             */
            public WhiteMarbleSubstitutionConfig(
                @JsonProperty("resourceType") ResourceType resourceType
            ) {
                this.resourceType = resourceType;
            }
        }
    }
}