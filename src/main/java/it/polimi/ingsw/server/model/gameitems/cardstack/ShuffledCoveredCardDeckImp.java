package it.polimi.ingsw.server.model.gameitems.cardstack;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ServerCoveredCardsDeckRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a generic Deck made of randomly shuffled Cards.
 * It inherits attributes and methods from the parent class CardDeck to peek, push or pop one of its elements
 * and to show all the elements stored in it.
 * @param <C> generic parameter used to indicate the type of Card which form the Deck (Leader Card, Development Card)
 */
public class ShuffledCoveredCardDeckImp<RC extends ServerRepresentation & IdentifiableItem, C extends Representable<RC>> extends CardDeckImp<C>
	implements ShuffledCoveredCardDeck<RC, C> {

	/**
	 * A random number generator used to place Cards in the Deck
	 */
	Random randGenerator;

	/**
	 * Shuffled Card Deck constructor.
	 * @param deckID unique ID that identifies this shuffled card Deck
	 * @param gameItemsManager a reference to gameItemsManager is needed to register all the decks created
	 *                         (see {@link it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem})
	 * @param objects list of generic Cards that will be randomly organized in this Deck
	 */
	public ShuffledCoveredCardDeckImp(
		String deckID,
		GameItemsManager gameItemsManager,
		List<C> objects
	) {
		super(deckID, gameItemsManager);
		randGenerator = new Random();
		initializeShuffledCardDeck(objects);
	}

	/**
	 * Shuffled Card Deck constructor specifying a type of random number generator.
	 * @param deckID unique ID that identifies this shuffled card Deck
	 * @param gameItemsManager a reference to gameItemsManager is needed to register all the decks created
	 *                         (see {@link it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem})
	 * @param randomGenerator random number generator
	 * @param objects list of generic Cards that will be randomly organized in this Deck
	 */
	public ShuffledCoveredCardDeckImp(
		String deckID,
		GameItemsManager gameItemsManager,
		Random randomGenerator,
		List<C> objects
	) {
		super(deckID, gameItemsManager);
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

	@Override
	public ServerCoveredCardsDeckRepresentation<RC> getServerRepresentation() {
		return new ServerCoveredCardsDeckRepresentation<RC>(
			getItemId(),
			this.cardDeck.peek().getServerRepresentation(),
			this.peekAll().size()
		);
	}

	@Override
	public ServerCoveredCardsDeckRepresentation<RC> getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}

}
