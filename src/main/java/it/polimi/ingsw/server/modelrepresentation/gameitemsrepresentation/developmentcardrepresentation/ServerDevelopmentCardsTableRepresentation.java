package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ServerCoveredCardsDeckRepresentation;

import java.util.Map;

public class ServerDevelopmentCardsTableRepresentation extends ServerRepresentation {

    /**
     * Map that contains the cards in each deck, along with their colour and level
     */
    public final Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ServerCoveredCardsDeckRepresentation<ServerDevelopmentCardRepresentation>>> cards;

    /**
     * DevelopmentCardsTableRepresentation constructor
     * @param cards that are placed in decks
     */
    public ServerDevelopmentCardsTableRepresentation(
        @JsonProperty("cards") Map<DevelopmentCardLevel,
            Map<DevelopmentCardColour,
                ServerCoveredCardsDeckRepresentation<ServerDevelopmentCardRepresentation>>> cards
    ) {
        this.cards = cards;
    }
}
