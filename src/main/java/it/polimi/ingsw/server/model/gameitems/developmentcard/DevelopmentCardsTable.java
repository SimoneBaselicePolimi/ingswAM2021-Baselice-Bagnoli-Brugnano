package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class represents the table on which the decks of development cards are located
 */
public class DevelopmentCardsTable {
	/**
	 * Map that contains the cards in each deck, along with their colour and level
	 */
	private Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>>> cards;

	/**
	 * DevelopmentCardsTable constructor
	 * @param cards that are placed in decks
     * @param gameItemsManager a reference to gameItemsManager is needed to register all the decks created
	 *                         (see {@link it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem})
     * @param getIdForDeckWithColourAndLevel lambda that specifies the criteria for assigning the IDs of the decks
	 */
	public DevelopmentCardsTable(
		List<DevelopmentCard> cards,
		GameItemsManager gameItemsManager,
		BiFunction<DevelopmentCardColour, DevelopmentCardLevel, String> getIdForDeckWithColourAndLevel
	) {
		this.cards = cards.stream().collect(Collectors.groupingBy(
			DevelopmentCard::getLevel,
			Collectors.groupingBy(
				DevelopmentCard::getColour,
				Collectors.collectingAndThen(
					Collectors.toList(),
					cardsForDeck -> {
						DevelopmentCardColour deckColour = cardsForDeck.get(0).getColour();
						DevelopmentCardLevel deckLevel = cardsForDeck.get(0).getLevel();
						String deckID = getIdForDeckWithColourAndLevel.apply(deckColour, deckLevel);
						return new ShuffledCardDeck<>(deckID, gameItemsManager, cardsForDeck);
					}
				)
			)
		));
	}

	/**
	 * Method to get all the cards that are available (cards that are on top of each deck)
	 * @return list of development cards that are available
	 */
	public List<DevelopmentCard> getAvailableCards() {
		List<DevelopmentCard> availableCards = new ArrayList<>();
		for (Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>> value : cards.values()) {
			for (ShuffledCardDeck<DevelopmentCard> deck : value.values())
				availableCards.add(deck.peek());
		}
		return availableCards;
	}

	/**
	 * Method to remove the Card from the top of the Deck and get this Card.
	 * @param level level of card to remove
	 * @param colour colour of card to remove
	 * @return DevelopmentCard the Development Card to remove
	 * @throws EmptyStackException if the deck is empty
	 */
	public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour){
		return (this.getDeckByLevelAndColour(level, colour).pop());
	}

	/**
	 * Method to get all the cards that are available (cards that are on top of each deck)
	 * @return map of development cards that are available, along with their colour and level
	 */
	public Map<DevelopmentCardLevel,Map<DevelopmentCardColour,DevelopmentCard>> getAvailableCardsAsMap() {
		Map<DevelopmentCardLevel, Map<DevelopmentCardColour, DevelopmentCard>> map =
			getAvailableCards().stream().collect(
				Collectors.groupingBy(
					DevelopmentCard::getLevel,
					Collectors.toMap(
						DevelopmentCard::getColour,
						Function.identity()
					)
				)
			);
		return map;
	}

	/**
	 * Method to get the Deck that contains cards of a specific colour and level
	 * @param cardLevel level of cards to return
	 * @param cardColour colour of cards to return
	 * @return the Deck that contains list of development cards that are randomly organized
	 * @throws IllegalArgumentException if the deck does not exist
	 */
	public ShuffledCardDeck<DevelopmentCard> getDeckByLevelAndColour (DevelopmentCardLevel cardLevel, DevelopmentCardColour cardColour)
			throws IllegalArgumentException{
		if (!cards.containsKey(cardLevel) || !cards.get(cardLevel).containsKey(cardColour))
			throw new IllegalArgumentException();
		return(cards.get(cardLevel).get(cardColour));
	}
}