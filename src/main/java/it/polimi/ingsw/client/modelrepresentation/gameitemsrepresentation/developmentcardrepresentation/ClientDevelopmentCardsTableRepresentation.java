package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.Map;

public class ClientDevelopmentCardsTableRepresentation extends ClientRepresentation {

    /**
     * Map that contains the cards in each deck, along with their colour and level
     */
    private Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>>> cards;

    /**
     * DevelopmentCardsTableRepresentation constructor
     * @param cards that are placed in decks
     */
    public ClientDevelopmentCardsTableRepresentation(
        @JsonProperty("cards") Map<DevelopmentCardLevel,
            Map<DevelopmentCardColour,
                ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>>> cards
    ) {
        this.cards = cards;
    }

    public Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>>> getCards() {
        return cards;
    }

    public void setCards(Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>>> cards) {
        this.cards = cards;
    }
}
