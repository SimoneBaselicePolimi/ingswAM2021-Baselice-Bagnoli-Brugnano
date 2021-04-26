package it.polimi.ingsw.configfile;

import java.util.List;
import java.util.Map;

public class GameInfoConfig {

    public final int maxNumberOfPlayers;

    public final boolean singlePlayerEnabled;

    public final GameSetup gameSetup;

    public final int numberOfPlayerOwnedDevelopmentCardDecks;

    public final List<ProductionConfig> basicProductionPower;

    public final List<ResourceStorageConfig> resourceShelves;

    public final boolean differentResourcesInDifferentStorages;

    public final boolean numberOfResourcesRewardingOneVictoryPoint;

    public GameInfoConfig(int maxNumberOfPlayers, boolean singlePlayerEnabled, GameSetup gameSetup,
                          int numberOfPlayerOwnedDevelopmentCardDecks, List<ProductionConfig> basicProductionPower,
                          List<ResourceStorageConfig> resourceShelves, boolean differentResourcesInDifferentStorages,
                          boolean numberOfResourcesRewardingOneVictoryPoint) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.singlePlayerEnabled = singlePlayerEnabled;
        this.gameSetup = gameSetup;
        this.numberOfPlayerOwnedDevelopmentCardDecks = numberOfPlayerOwnedDevelopmentCardDecks;
        this.basicProductionPower = basicProductionPower;
        this.resourceShelves = resourceShelves;
        this.differentResourcesInDifferentStorages = differentResourcesInDifferentStorages;
        this.numberOfResourcesRewardingOneVictoryPoint = numberOfResourcesRewardingOneVictoryPoint;
    }

    public static class GameSetup {

        public final int numberOfLeadersCardsGivenToThePlayer;
        public final int numberOfLeadersCardsThePlayerKeeps;

        public final Map<Integer, InitialPlayerResourcesAndFaithPoints> initialPlayerResourcesBasedOnPlayOrder;

        public GameSetup(
            int numberOfLeadersCardsGivenToThePlayer,
            int numberOfLeadersCardsThePlayerKeeps,
            Map<Integer, InitialPlayerResourcesAndFaithPoints> initialPlayerResourcesBasedOnPlayOrder
        ) {
            this.numberOfLeadersCardsGivenToThePlayer = numberOfLeadersCardsGivenToThePlayer;
            this.numberOfLeadersCardsThePlayerKeeps = numberOfLeadersCardsThePlayerKeeps;
            this.initialPlayerResourcesBasedOnPlayOrder = initialPlayerResourcesBasedOnPlayOrder;
        }

        public static class InitialPlayerResourcesAndFaithPoints {
            public final int starResources;
            public final int faithPoints;

            public InitialPlayerResourcesAndFaithPoints(int starResources, int faithPoints) {
                this.starResources = starResources;
                this.faithPoints = faithPoints;
            }
        }
    }
}
