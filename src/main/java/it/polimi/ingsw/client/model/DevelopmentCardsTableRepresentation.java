package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.Map;

public class DevelopmentCardsTableRepresentation extends Representation{

    /**
     * Map that contains the cards in each deck, along with their colour and level
     */
    //TODO proposta: avere come attributo solo le cards on top, dato che gli interi mazzetti sono nascosti
    // (semplifica anche l'Handler)
    private Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCardRepresentation>>> cards;

    /**
     * DevelopmentCardsTableRepresentation constructor
     * @param cards that are placed in decks
     */
    public DevelopmentCardsTableRepresentation(
        Map<DevelopmentCardLevel,
            Map<DevelopmentCardColour,
                ShuffledCardDeck<DevelopmentCardRepresentation>>> cards
    ) {
        this.cards = cards;
    }

    public Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCardRepresentation>>> getCards() {
        return cards;
    }

    public void setCards(Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCardRepresentation>>> cards) {
        this.cards = cards;
    }
}
