package it.polimi.ingsw.server.model.gameitems.cardstack;

import java.util.EmptyStackException;

public abstract class CardStack<C> {

	public boolean isEmpty() {
		return false;
	}

	public C peek() throws EmptyStackException {
		return null;
	}

	public C pop() throws EmptyStackException {
		return null;
	}

	public void pushOnTop(C card) {

	}

}
