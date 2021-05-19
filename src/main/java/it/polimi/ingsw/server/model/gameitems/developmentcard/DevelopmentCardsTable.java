package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCoveredCardDeck;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardsTableRepresentation;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;

public interface DevelopmentCardsTable extends Representable<ServerDevelopmentCardsTableRepresentation> {
    /**
     * Method to get all the cards that are available (cards that are on top of each deck)
     * @return list of development cards that are available
     */
    List<DevelopmentCard> getAvailableCards();

    /**
     * Method to remove the Card from the top of the Deck and get this Card.
     * @param level level of card to remove
     * @param colour colour of card to remove
     * @return DevelopmentCard the Development Card to remove
     * @throws EmptyStackException if the deck is empty
     */
    DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour);

    /**
     * Method to get all the cards that are available (cards that are on top of each deck)
     * @return map of development cards that are available, along with their colour and level
     */
    Map<DevelopmentCardLevel, Map<DevelopmentCardColour,DevelopmentCard>> getAvailableCardsAsMap();

    /**
     * Method to get the Deck that contains cards of a specific colour and level
     * @param cardLevel level of cards to return
     * @param cardColour colour of cards to return
     * @return the Deck that contains list of development cards that are randomly organized
     * @throws IllegalArgumentException if the deck does not exist
     */
    ShuffledCoveredCardDeck<ServerDevelopmentCardRepresentation, DevelopmentCard> getDeckByLevelAndColour(DevelopmentCardLevel cardLevel, DevelopmentCardColour cardColour)
            throws IllegalArgumentException;
}
