package it.polimi.ingsw.configfile;

import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.List;

public class MarketConfig {

    public final MatrixConfig marketMatrix;

    public final List<MarbleConfigAndNumber> marbles;

    public MarketConfig(MatrixConfig marketMatrix, List<MarbleConfigAndNumber> marbles) {
        this.marketMatrix = marketMatrix;
        this.marbles = marbles;
    }

    public static class MatrixConfig {
        public final int numberOfRows;
        public final int numberOfColumns;

        public MatrixConfig(int numberOfRows, int numberOfColumns) {
            this.numberOfRows = numberOfRows;
            this.numberOfColumns = numberOfColumns;
        }
    }

    public static class  MarbleConfigAndNumber {

        public final MarbleConfig marbleConfig;

        public final int numberOfMarbles;

        public MarbleConfigAndNumber(MarbleConfig marbleConfig, int numberOfMarbles) {
            this.marbleConfig = marbleConfig;
            this.numberOfMarbles = numberOfMarbles;
        }

        public static class MarbleConfig {

            public final String marbleID;

            public final ResourceType resourceType;

            public final int numberOfFaithPoints;

            public final boolean isSpecial;

            public MarbleConfig(String marbleID, ResourceType resourceType, int numberOfFaithPoints, boolean isSpecial) {
                this.marbleID = marbleID;
                this.resourceType = resourceType;
                this.numberOfFaithPoints = numberOfFaithPoints;
                this.isSpecial = isSpecial;
            }
        }

    }

}
