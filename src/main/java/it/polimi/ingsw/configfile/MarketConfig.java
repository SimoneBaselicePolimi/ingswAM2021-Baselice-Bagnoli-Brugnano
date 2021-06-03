package it.polimi.ingsw.configfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.Colour;

import java.util.List;

/**
 * This class sets all the parameters regarding the Market structure from values stored in market-config file.
 */
public class MarketConfig {

    /**
     * Matrix representing the Market
     */
    public final MatrixConfig marketMatrix;

    /**
     * List of Coloured Marbles in different quantities which fill the Market matrix
     */
    public final List<MarbleConfigAndNumber> marbles;

    /**
     * MarketConfig constructor.
     * @param marketMatrix matrix representing the Market
     * @param marbles list of Coloured Marbles in different quantities which fill the Market matrix
     */
    public MarketConfig(
        @JsonProperty("marketMatrix") MatrixConfig marketMatrix,
        @JsonProperty("marbles") List<MarbleConfigAndNumber> marbles
    ) {
        this.marketMatrix = marketMatrix;
        this.marbles = marbles;
    }

    /**
     * This class contains the configuration schema of the matrix representing the Market.
     */
    public static class MatrixConfig {

        /**
         * Number of rows in the matrix
         */
        public final int numberOfRows;

        /**
         * Number of columns in the matrix
         */
        public final int numberOfColumns;

        /**
         * MatrixConfig constructor.
         * @param numberOfRows number of rows in the matrix
         * @param numberOfColumns number of columns in the matrix
         */
        public MatrixConfig(
            @JsonProperty("numberOfRows") int numberOfRows,
            @JsonProperty("numberOfColumns") int numberOfColumns
        ) {
            this.numberOfRows = numberOfRows;
            this.numberOfColumns = numberOfColumns;
        }
    }

    /**
     * This class indicates which type of Coloured Marbles fill the Market matrix and their quantities.
     */
    public static class MarbleConfigAndNumber {

        /**
         * Configuration of a Coloured Marble
         */
        public final MarbleConfig marbleConfig;

        /**
         * Number of Coloured Marbles
         */
        public final int numberOfMarbles;

        /**
         * MarbleConfigAndNumber constructor.
         * @param marbleConfig configuration of a Coloured Marble
         * @param numberOfMarbles number of Coloured Marbles
         */
        public MarbleConfigAndNumber(
            @JsonProperty("marbleConfig") MarbleConfig marbleConfig,
            @JsonProperty("numberOfMarbles") int numberOfMarbles
        ) {
            this.marbleConfig = marbleConfig;
            this.numberOfMarbles = numberOfMarbles;
        }

        /**
         * This class contains the configuration schema of a Coloured Marble.
         */
        public static class MarbleConfig {

            /**
             * Unique ID which identifies this specific Marble
             */
            public final String marbleID;

            /**
             * Marble colour, specified as a list (in case of multi-coloured marbles) of RGB colour codes
             */
            public final List<Colour> marbleColour;

            /**
             * Optional type of Resource obtainable with this Marble
             */
            public final ResourceType resourceType;

            /**
             * Number of Faith Points given to the Player who owns this Marble
             */
            public final int numberOfFaithPoints;

            /**
             * Possibility to be transformed into a generic type of Resource by activating the
             * Special Marble Substitution power of a Leader Card
             */
            public final boolean isSpecial;

            /**
             * MarbleConfig constructor.
             * @param marbleID unique ID which identifies this specific Marble
             * @param marbleColour marble colour specified as a list of RGB colour codes
             * @param resourceType optional type of Resource obtainable with this Marble
             * @param numberOfFaithPoints number of Faith Points given to the Player who owns this Marble
             * @param isSpecial true if the Marble can be transformed into a generic type of Resource by activating the
             */
            public MarbleConfig(
                @JsonProperty("marbleID") String marbleID,
                @JsonProperty("marbleColour") List<Colour> marbleColour,
                @JsonProperty("resourceType") ResourceType resourceType,
                @JsonProperty("numberOfFaithPoints") int numberOfFaithPoints,
                @JsonProperty("isSpecial") boolean isSpecial
            ) {
                this.marbleID = marbleID;
                this.marbleColour = marbleColour;
                this.resourceType = resourceType;
                this.numberOfFaithPoints = numberOfFaithPoints;
                this.isSpecial = isSpecial;
            }
        }

    }

}
