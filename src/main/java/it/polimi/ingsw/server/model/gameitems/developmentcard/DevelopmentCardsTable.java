package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class represents the table on which the decks of development cards are located
 */
public class DevelopmentCardsTable {
	/**
	 * Map that contains the cards in each deck, along with their color and level
	 */
	private Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>>> cards = new HashMap<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>>>();

	/**
	 * DevelopmentCardsTable constructor
	 * @param cards that are placed in decks
	 */
	public DevelopmentCardsTable(List<DevelopmentCard> cards) {
		this.cards = cards.stream().collect(Collectors.groupingBy(
				DevelopmentCard::getLevel,
				Collectors.groupingBy(
						DevelopmentCard::getColour,
						Collectors.collectingAndThen(Collectors.toList(), ShuffledCardDeck::new)
				)
		));
	}

	/**
	 * Method to get all the cards that are available (cards that are on top of each deck)
	 * @return list of development cards that are available
	 */
	public List<DevelopmentCard> getAvailableCards() {
		List<DevelopmentCard> availableCards = new ArrayList<DevelopmentCard>();
		for (Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>> value : cards.values()) {
			for (ShuffledCardDeck<DevelopmentCard> deck : value.values())
				availableCards.add(deck.peek());
		}
		return availableCards;
	}

	/**
	 * Method to remove the Card on the top of the Deck and get this Card.
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