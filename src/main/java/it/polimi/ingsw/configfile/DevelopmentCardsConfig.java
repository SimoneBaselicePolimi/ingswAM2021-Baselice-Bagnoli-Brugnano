package it.polimi.ingsw.configfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.List;
import java.util.Map;

/**
 * This class sets all the parameters regarding the Development Cards in the Game
 * from values stored in development-cards-config.yml file.
 */
public class DevelopmentCardsConfig {

    /**
     * List of Development Cards in the Game
     */
    public final List<DevelopmentCardConfig> developmentCards;

    /**
     * DevelopmentCardsConfig constructor.
     * @param developmentCards list of Development Cards in the Game
     */
    public DevelopmentCardsConfig(
        @JsonProperty("developmentCards") List<DevelopmentCardConfig> developmentCards) {
        this.developmentCards = developmentCards;
    }

    /**
     * This class contains the configuration schema of a Development Card.
     */
    public static class DevelopmentCardConfig {

        /**
         * Unique ID which identifies this specific Development Card
         */
        public final String developmentCardID;

        /**
         * Fixed level of this Development Card
         */
        public final DevelopmentCardLevel level;

        /**
         * Fixed colour of this Development Card
         */
        public final DevelopmentCardColour colour;

        /**
         * List of Production powers (the Player can pay specific costs to obtain specific rewards)
         */
        public final List<ProductionConfig> productions;

        /**
         * Number of Victory Points given at the end of the Game to the Player who owns this Development Card
         */
        public final int victoryPoints;

        /**
         * Purchase cost, necessary to allow the Player to buy this Development Card
         * (specific Resources in a fixed quantity)
         */
        public final Map<ResourceType, Integer> purchaseCost;

        /**
         * DevelopmentCardConfig constructor.
         * @param developmentCardID unique ID which identifies this specific Development Card
         * @param level fixed level of this Development Card
         * @param colour fixed colour of this Development Card
         * @param productions list of Production powers (the Player can pay specific costs to obtain specific rewards)
         * @param victoryPoints number of Victory Points given at the end of the Game
         *                      to the Player who owns this Development Card
         * @param purchaseCost purchase cost necessary to allow the Player to buy this Development Card
         */
        public DevelopmentCardConfig(
            @JsonProperty("developmentCardID") String developmentCardID,
            @JsonProperty("level") DevelopmentCardLevel level,
            @JsonProperty("colour") DevelopmentCardColour colour,
            @JsonProperty("productions") List<ProductionConfig> productions,
            @JsonProperty("victoryPoints") int victoryPoints,
            @JsonProperty("purchaseCost") Map<ResourceType, Integer> purchaseCost
        ) {
            this.developmentCardID = developmentCardID;
            this.level = level;
            this.colour = colour;
            this.productions = productions;
            this.victoryPoints = victoryPoints;
            this.purchaseCost = purchaseCost;
        }
    }
}
