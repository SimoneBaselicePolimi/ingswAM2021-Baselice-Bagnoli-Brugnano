package it.polimi.ingsw.configfile;

import java.util.List;

/**
 * This class sets all the parameters regarding the Faith Path from values stored in faith-path-config.yml file.
 */
public class FaithPathConfig {

    /**
     * Length of the Faith Path
     */
    public final int faithPathLength;

    /**
     * List of Vatican Report Sections included in the Faith Path
     */
    public final List<VaticanReportSectionConfig> vaticanReportSections;

    /**
     * List of sections which give at the end of the Game a specific number of Victory Points to the Player
     * based on his position on the Faith Track
     */
    public final List<VictoryPointsByPositionConfig> victoryPointsByPositions;

    /**
     * FaithPathConfig constructor.
     * @param faithPathLength length of the Faith Path
     * @param vaticanReportSections list of Vatican Report Sections included in the Faith Path
     * @param victoryPointsByPositions list of sections which give at the end of the Game a specific number
     *                                 of Victory Points to the Player based on his position on the Faith Track
     */
    public FaithPathConfig(int faithPathLength, List<VaticanReportSectionConfig> vaticanReportSections,
                           List<VictoryPointsByPositionConfig> victoryPointsByPositions) {
        this.faithPathLength = faithPathLength;
        this.vaticanReportSections = vaticanReportSections;
        this.victoryPointsByPositions = victoryPointsByPositions;
    }

    /**
     * This class contains the configuration schema of a Vatican Report Section.
     */
    public static class VaticanReportSectionConfig {

        /**
         * Position in which this section starts
         */
        public final int initialPosition;

        /**
         * Position of the Pope space, in which a Player triggers the Vatican Report
         */
        public final int popeSpacePosition;

        /**
         * Number of Victory Points scored by a Player for the related Pope's Favor card turned face-up (active)
         */
        public final int victoryPoints;

        /**
         * VaticanReportSectionConfig constructor.
         * @param initialPosition position in which this section start
         * @param popeSpacePosition position of the Pope space, in which a Player triggers the Vatican Report
         * @param victoryPoints number of Victory Points scored by a Player
         *                      for the related Pope's Favor card turned face-up (active)
         */
        public VaticanReportSectionConfig(int initialPosition, int popeSpacePosition, int victoryPoints) {
            this.initialPosition = initialPosition;
            this.popeSpacePosition = popeSpacePosition;
            this.victoryPoints = victoryPoints;
        }
    }

    /**
     * This class contains the configuration schema of a section of the Faith Track which gives at the end of the Game
     * a specific number of Victory Points to the Player based on his position.
     */
    public static class VictoryPointsByPositionConfig {

        /**
         * Position in which this section starts
         */
        public final int startPosition;

        /**
         * Position in which this section ends
         */
        public final int endPosition;

        /**
         * Number of Victory Points given to the Player
         */
        public final int victoryPoints;

        /**
         * VictoryPointsByPositionConfig constructor.
         * @param startPosition position in which this section starts
         * @param endPosition position in which this section ends
         * @param victoryPoints number of Victory Points given to the Player
         */
        public VictoryPointsByPositionConfig(int startPosition, int endPosition, int victoryPoints) {
            this.startPosition = startPosition;
            this.endPosition = endPosition;
            this.victoryPoints = victoryPoints;
        }
    }
}
