package it.polimi.ingsw.server.model.gameitems.cardstack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * This class represents a generic Deck made of Cards and provides the methods to peek, push or pop one of
 * its elements and to show all the elements stored in it.
 * @param <C> generic parameter used to indicate the type of Card which form the Deck (Leader Card, Development Card)
 */
public abstract class CardDeck<C> {
	/**
	 * Stack of generics which represents the Card Deck
	 */
	protected Stack<C> cardDeck = new Stack<>();

	/**
	 * Method to test if the Card Deck is empty.
	 * @return true if this Card Deck is empty (no elements stored in it), false if there's at least one element in it
	 */
	public boolean isEmpty() {
		return cardDeck.empty();
	}

	/**
	 * Method to peek the first Card on the top of the Deck without removing it from the stack.
	 * @return Card on top of this Deck
	 * @throws EmptyStackException if this Deck is empty
	 */
	public C peek() throws EmptyStackException {
		return cardDeck.peek();
	}

	/**
	 * Method to get all the Cards which are part of the Deck.
	 * @return list of all the Cards stored in this Deck
	 * @throws EmptyStackException if this Deck is empty
	 */
	public List<C> peekAll() throws EmptyStackException {
		List<C> cardsList = new ArrayList<>();
		Stack<C> cardDeckCopy = (Stack<C>)cardDeck.clone();
		while(!cardDeckCopy.isEmpty())
			cardsList.add(cardDeckCopy.remove(0));
		return cardsList;
	}

	/**
	 * Method to remove the Card on the top of the Deck and get this Card.
	 * @return Card which has just been removed from this Deck
	 * @throws EmptyStackException if this Deck is empty
	 */
	public C pop() throws EmptyStackException {
		return cardDeck.pop();
	}

	/**
	 * Method to push a Card on the top of the Deck.
	 * @param card Card which has to be pushed on the top of this Deck
	 * @throws ForbiddenPushOnTopException if the rules imposed by this Deck do not allow this Card to be pushed on the
	 * top of this Deck
	 */
	public void pushOnTop(C card) throws ForbiddenPushOnTopException {
		cardDeck.push(card);
	}

}
