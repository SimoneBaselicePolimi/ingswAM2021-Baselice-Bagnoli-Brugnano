package it.polimi.ingsw.server.model.gameitems.cardstack;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

/**
 * This class represents a generic Card Deck, owned by a Player, consisting of Developments Cards.
 * It provides the methods to push a Card on the top of this Deck if the insertion is tested as valid:
 * the first Card to be added to an empty Deck has to be a First Level Card, then only a Second Level Card
 * can be pushed on top of it; finally a Third Level Card could be placed on top of the latter.
 * Thus, a Card Deck can only have up to three Cards.
 * This class also inherits attributes and methods from the parent class CardDeck to peek, push or pop
 * one of its elements and to show all the elements stored in it.
 */
public class PlayerOwnedDevelopmentCardDeck extends CardDeck<DevelopmentCard> {

	/**
	 * Class constructor.
	 * @param deckID ID which identifies this specific Card Deck
	 * @param gameItemsManager a reference to gameItemsManager is needed to register the new PlayerOwnedDevelopmentCardDeck object
	 *                          (see {@link RegisteredIdentifiableItem})
	 */
	protected PlayerOwnedDevelopmentCardDeck(
		String deckID,
		GameItemsManager gameItemsManager
	) {
		super(deckID, gameItemsManager);
	}

	/**
	 * Method to push a Development Card on the top of the Deck if the Level of the Card is suitable for this Deck and
	 * if there are no more than three Cards stored in it.
	 * @param card Card which has to be pushed on the top of this Deck
	 * @throws ForbiddenPushOnTopException if the insertion of the Card passed as parameter do not follow
	 * one of the rules imposed by the Development Card Deck
	 */
	public void pushOnTop(DevelopmentCard card) throws ForbiddenPushOnTopException {
		if(isPushOnTopValid(card))
			cardDeck.push(card);
		else throw new ForbiddenPushOnTopException();
	}

	/**
	 * Method to test if the insertion of the Card passed as parameter in this Deck is valid or not, following
	 * the rules of the Development Card Deck based on the Levels of Cards.
	 * @param card Card which has to be tested before pushing it on the top of the Card Deck
	 * @return true if the insertion is valid as the Level of the Card is suitable for this Deck
	 * (from the top to the bottom, a Third Level Card on a Second Level Card on a First Level Card), false otherwise
	 */
	public boolean isPushOnTopValid(DevelopmentCard card) {
		if(cardDeck.isEmpty())
			return card.getLevel() == DevelopmentCardLevel.FIRST_LEVEL;
		if(card.getLevel() == DevelopmentCardLevel.SECOND_LEVEL && cardDeck.peek().getLevel() == DevelopmentCardLevel.FIRST_LEVEL)
			return true;
		return card.getLevel() == DevelopmentCardLevel.THIRD_LEVEL && cardDeck.peek().getLevel() == DevelopmentCardLevel.SECOND_LEVEL;
	}

}
