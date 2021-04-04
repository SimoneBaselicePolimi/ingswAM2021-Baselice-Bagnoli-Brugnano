package it.polimi.ingsw.server.model.gameitems.cardstack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a generic Deck made of randomly shuffled Cards.
 * It inherits attributes and methods from the parent class CardDeck to peek, push or pop one of its elements
 * and to show all the elements stored in it.
 * @param <C> generic parameter used to indicate the type of Card which form the Deck (Leader Card, Development Card)
 */
public class ShuffledCardDeck<C> extends CardDeck<C> {
	/**
	 * A random number generator used to place Cards in the Deck
	 */
	Random randGenerator;

	/**
	 * Shuffled Card Deck constructor.
	 * @param objects list of generic Cards that will be randomly organized in this Deck
	 */
	public ShuffledCardDeck(List<C> objects) {
		randGenerator = new Random();
		initializeShuffledCardDeck(objects);
	}

	/**
	 * Shuffled Card Deck constructor specifying a type of random number generator.
	 * @param randomGenerator random number generator
	 * @param objects list of generic Cards that will be randomly organized in this Deck
	 */
	public ShuffledCardDeck(Random randomGenerator, List<C> objects) {
		this.randGenerator = randomGenerator;
		initializeShuffledCardDeck(objects);
	}

	/**
	 * Initializes the Shuffled Card Deck pushing randomly each Card of the list passed as parameter in this Deck.
	 * @param objects list of generic Cards that will be randomly organized in this Deck
	 */
	private void initializeShuffledCardDeck(List<C> objects) {
		List<C> cardList = new ArrayList<>(objects);
		while (cardList.size() > 0) {
			int randNum = randGenerator.nextInt(cardList.size());
			cardDeck.push(cardList.remove(randNum));
		}
	}

}
