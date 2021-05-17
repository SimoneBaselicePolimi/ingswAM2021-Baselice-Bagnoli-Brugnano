package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ServerCoveredCardsDeckRepresentation;

import java.util.Map;

public class ServerDevelopmentCardsTableRepresentation extends ClientRepresentation {

    /**
     * Map that contains the cards in each deck, along with their colour and level
     */
    private Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ServerCoveredCardsDeckRepresentation<ServerDevelopmentCardRepresentation>>> cards;

    /**
     * DevelopmentCardsTableRepresentation constructor
     * @param cards that are placed in decks
     */
    public ServerDevelopmentCardsTableRepresentation(
        Map<DevelopmentCardLevel,
            Map<DevelopmentCardColour,
                ServerCoveredCardsDeckRepresentation<ServerDevelopmentCardRepresentation>>> cards
    ) {
        this.cards = cards;
    }

    public Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ServerCoveredCardsDeckRepresentation<ServerDevelopmentCardRepresentation>>> getCards() {
        return cards;
    }

    public void setCards(Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ServerCoveredCardsDeckRepresentation<ServerDevelopmentCardRepresentation>>> cards) {
        this.cards = cards;
    }
}
