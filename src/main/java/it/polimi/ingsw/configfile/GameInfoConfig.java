package it.polimi.ingsw.configfile;

import java.util.Map;

public class GameInfoConfig {

    public final int maxNumberOfPlayers;

    public final boolean singlePlayerEnabled;

    public final GameSetup gameSetup;

    public GameInfoConfig(int maxNumberOfPlayers, boolean singlePlayerEnabled, GameSetup gameSetup) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.singlePlayerEnabled = singlePlayerEnabled;
        this.gameSetup = gameSetup;
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
