package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientDevelopmentCardsTableRepresentation extends ClientRepresentation {

    /**
     * Map that contains the cards in each deck, along with their colour and level
     */
    private Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>>> cards;
    private Set<ClientDevelopmentCardRepresentation> purchasableCards = new HashSet<>();

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

    public ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> getDeck(
        DevelopmentCardLevel deckLevel, DevelopmentCardColour deckColour
    ) {
        return cards.get(deckLevel).get(deckColour);
    }

    public void setCards(Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>>> cards) {
        this.cards = cards;
        notifyViews();
    }

    public void setPurchasableCards(Set<ClientDevelopmentCardRepresentation> purchasableCards) {
        this.purchasableCards = new HashSet<>(purchasableCards);
        notifyViews();
    }

    public Set<ClientDevelopmentCardRepresentation> getPurchasableCards() {
        return new HashSet<>(purchasableCards);
    }

    public boolean isCardPurchasable(ClientDevelopmentCardRepresentation card) {
        return purchasableCards.contains(card);
    }
}
