package it.polimi.ingsw.configfile;

import java.util.List;

public class FaithPathConfig {
    public final int length;

    public final List<VaticanReportSectionConfig> vaticanReportSections;
    public final List<VictoryPointsByPositionConfig> victoryPointsByPositions;

    public FaithPathConfig(int length, List<VaticanReportSectionConfig> vaticanReportSections,
                           List<VictoryPointsByPositionConfig> victoryPointsByPositions) {
        this.length = length;
        this.vaticanReportSections = vaticanReportSections;
        this.victoryPointsByPositions = victoryPointsByPositions;
    }

    public static class VaticanReportSectionConfig {
        public final int initialPosition;
        public final int popeSpacePosition;
        public final int victoryPoints;

        public VaticanReportSectionConfig(int initialPosition, int popeSpacePosition, int victoryPoints) {
            this.initialPosition = initialPosition;
            this.popeSpacePosition = popeSpacePosition;
            this.victoryPoints = victoryPoints;
        }
    }

    public static class VictoryPointsByPositionConfig {
        public final int startPosition;
        public final int endPosition;
        public final int victoryPoints;

        public VictoryPointsByPositionConfig(int startPosition, int endPosition, int victoryPoints) {
            this.startPosition = startPosition;
            this.endPosition = endPosition;
            this.victoryPoints = victoryPoints;
        }
    }
}
