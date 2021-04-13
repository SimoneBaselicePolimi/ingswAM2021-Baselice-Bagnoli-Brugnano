package it.polimi.ingsw.configfile;

import java.util.Map;

public class GameInfoConfig {

    public final int maxNumberOfPlayers;

    public final GameSetup gameSetup;

    public GameInfoConfig(int maxNumberOfPlayers, GameSetup gameSetup) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.gameSetup = gameSetup;
    }

    public class GameSetup {

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

        public class InitialPlayerResourcesAndFaithPoints {
           public final int starResources;
           public final int faithPoints;

            public InitialPlayerResourcesAndFaithPoints(int starResources, int faithPoints) {
                this.starResources = starResources;
                this.faithPoints = faithPoints;
            }
        }

    }

}
