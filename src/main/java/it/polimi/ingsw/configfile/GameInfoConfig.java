package it.polimi.ingsw.configfile;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * This class sets all the parameters regarding the Setup and general information about the Game from values stored
 * in game-info-config file.
 */
public class GameInfoConfig {

    /**
     * Maximum number of Players allowed in a Game
     */
    public final int maxNumberOfPlayers;

    /**
     * Enables the Single Player mode
     */
    public final boolean singlePlayerEnabled;

    /**
     * Additional Setup Rules
     */
    public final GameSetup gameSetup;

    /**
     * Number of Slots for Development Cards assigned to each Player's Personal Board
     */
    public final int numberOfPlayerOwnedDevelopmentCardDecks;

    /**
     * Basic production power given to each Player by his Personal Board
     */
    public final List<ProductionConfig> basicProductionPower;

    /**
     * Shelves used as a Resources Storage in which Players can keep Resources bought at the Market
     */
    public final List<ResourceStorageConfig> resourceShelves;

    /**
     * Indicates if different Resources have to be located in different Storages (as in the standard game rules)
     */
    public final boolean differentResourcesInDifferentStorages;

    /**
     * Number of Resources that at the end of the Game rewards the Player with one Victory Point
     */
    public final int numberOfResourcesRewardingOneVictoryPoint;

    /**
     * GameInfoConfig constructor.
     * @param maxNumberOfPlayers maximum number of Players allowed in a Game
     * @param singlePlayerEnabled enables the Single Player mode
     * @param gameSetup additional Setup Rules
     * @param numberOfPlayerOwnedDevelopmentCardDecks number of Slots for Development Cards assigned to each
     *                                                Player's Personal Board
     * @param basicProductionPower basic production power given to each Player by his Personal Board
     * @param resourceShelves shelves used as a Resources Storage in which Players can keep Resources bought at the Market
     * @param differentResourcesInDifferentStorages true if different Resources have to be located in different Storages
     *                                             (as in the standard game rules), otherwise false
     * @param numberOfResourcesRewardingOneVictoryPoint number of Resources that at the end of the Game
     *                                                  rewards the Player with one Victory Point
     */
    public GameInfoConfig(
        @JsonProperty("maxNumberOfPlayers") int maxNumberOfPlayers,
        @JsonProperty("singlePlayerEnabled") boolean singlePlayerEnabled,
        @JsonProperty("gameSetup") GameSetup gameSetup,
        @JsonProperty("numberOfPlayerOwnedDevelopmentCardDecks") int numberOfPlayerOwnedDevelopmentCardDecks,
        @JsonProperty("basicProductionPower") List<ProductionConfig> basicProductionPower,
        @JsonProperty("resourceShelves") List<ResourceStorageConfig> resourceShelves,
        @JsonProperty("differentResourcesInDifferentStorages") boolean differentResourcesInDifferentStorages,
        @JsonProperty("numberOfResourcesRewardingOneVictoryPoint") int numberOfResourcesRewardingOneVictoryPoint
    ) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.singlePlayerEnabled = singlePlayerEnabled;
        this.gameSetup = gameSetup;
        this.numberOfPlayerOwnedDevelopmentCardDecks = numberOfPlayerOwnedDevelopmentCardDecks;
        this.basicProductionPower = basicProductionPower;
        this.resourceShelves = resourceShelves;
        this.differentResourcesInDifferentStorages = differentResourcesInDifferentStorages;
        this.numberOfResourcesRewardingOneVictoryPoint = numberOfResourcesRewardingOneVictoryPoint;
    }

    /**
     * Additional Setup Rules.
     */
    public static class GameSetup {

        /**
         * Number of Leader Cards each Player randomly receives at the start of the Game
         */
        public final int numberOfLeadersCardsGivenToThePlayer;

        /**
         * Number of Leader Cards each Player must choose to keep in hand. All the other cards will be discarded.
         */
        public final int numberOfLeadersCardsThePlayerKeeps;

        /**
         * Distribution of initial Star Resources (the Player has to choose which type of Resource he wants)
         * and a specific number of Faith Points based on Play Order
         */
        public final Map<Integer, InitialPlayerResourcesAndFaithPoints> initialPlayerResourcesBasedOnPlayOrder;

        /**
         * GameSetup constructor.
         * @param numberOfLeadersCardsGivenToThePlayer number of Leader Cards each Player randomly receives
         *                                             at the start of the Game
         * @param numberOfLeadersCardsThePlayerKeeps number of Leader Cards each Player must choose to keep in hand.
         *                                           All the other cards will be discarded
         * @param initialPlayerResourcesBasedOnPlayOrder distribution of initial Star Resources (the Player
         *                                               has to choose which type of Resource he wants) and a specific
         *                                               number of Faith Points based on Play Order
         */
        public GameSetup(
            @JsonProperty("numberOfLeadersCardsGivenToThePlayer") int numberOfLeadersCardsGivenToThePlayer,
            @JsonProperty("numberOfLeadersCardsThePlayerKeeps") int numberOfLeadersCardsThePlayerKeeps,
            @JsonProperty("initialPlayerResourcesBasedOnPlayOrder") Map<Integer, InitialPlayerResourcesAndFaithPoints> initialPlayerResourcesBasedOnPlayOrder
        ) {
            this.numberOfLeadersCardsGivenToThePlayer = numberOfLeadersCardsGivenToThePlayer;
            this.numberOfLeadersCardsThePlayerKeeps = numberOfLeadersCardsThePlayerKeeps;
            this.initialPlayerResourcesBasedOnPlayOrder = initialPlayerResourcesBasedOnPlayOrder;
        }

        /**
         * Distribution of initial Resources and Faith Points given to each Player at the start of the Game.
         * It's based on Play Order.
         */
        public static class InitialPlayerResourcesAndFaithPoints {

            /**
             * Initial Star Resources (the Player has to choose which type of Resource he wants)
             */
            public final int starResources;

            /**
             * Initial number of Faith Points
             */
            public final int faithPoints;

            /**
             * InitialPlayerResourcesAndFaithPoints constructor.
             * @param starResources initial Star Resources (the Player has to choose which type of Resource he wants)
             * @param faithPoints initial number of Faith Points
             */
            public InitialPlayerResourcesAndFaithPoints(
                @JsonProperty("starResources") int starResources,
                @JsonProperty("faithPoints") int faithPoints
            ) {
                this.starResources = starResources;
                this.faithPoints = faithPoints;
            }
        }
    }
}
