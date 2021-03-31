package it.polimi.ingsw.server.model.gameitems.cardstack;

import java.util.EmptyStackException;
import java.util.Stack;

public abstract class CardDeck<C> {
	Stack<C> cardDeck = new Stack<>();

	public boolean isEmpty() {
		return cardDeck.empty();
	}

	public C peek() throws EmptyStackException {
		return cardDeck.peek();
	}

	public C pop() throws EmptyStackException {
		if(cardDeck.isEmpty())
			throw new EmptyStackException();
		return cardDeck.pop();
	}

	public void pushOnTop(C card) throws ForbiddenPushOnTopException {
		cardDeck.push(card);
	}

}
