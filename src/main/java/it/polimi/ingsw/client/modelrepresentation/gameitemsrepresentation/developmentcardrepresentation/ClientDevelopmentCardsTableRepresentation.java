package it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientDevelopmentCardsTableRepresentation extends ClientRepresentation {

    /**
     * Map that contains the cards in each deck, along with their colour and level
     */
    private Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>>> cards;
    private final Map<Player, Set<ClientDevelopmentCardRepresentation>> purchasableCards = new HashMap<>();

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

    public void setPurchasableCardsForPlayer(Player player, Set<ClientDevelopmentCardRepresentation> purchasableCards) {
        this.purchasableCards.put(player, new HashSet<>(purchasableCards));
        notifyViews();
    }

    public Map<Player, Set<ClientDevelopmentCardRepresentation>> getAllPurchasableCards() {
        return new HashMap<>(purchasableCards);
    }

    public Set<ClientDevelopmentCardRepresentation> getAllPurchasableCardsForMyPlayer() {
        return new HashSet<>(purchasableCards.getOrDefault(ClientManager.getInstance().getMyPlayer(), new HashSet<>()));

    }

    public boolean isCardPurchasableByMyPlayer(ClientDevelopmentCardRepresentation card) {
        return purchasableCards.getOrDefault(ClientManager.getInstance().getMyPlayer(), new HashSet<>()).contains(card);
    }
}
