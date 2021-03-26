package it.polimi.ingsw.server.model.gameitems.cardstack;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;

public class ShuffledCardStack<C> extends CardStack<C> {

	public ShuffledCardStack(List<C> objects) {

	}

	public ShuffledCardStack(Random randomGenerator, List<C> objects) {

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
