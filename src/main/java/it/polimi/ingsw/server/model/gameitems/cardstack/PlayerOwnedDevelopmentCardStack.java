package it.polimi.ingsw.server.model.gameitems.cardstack;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;

import java.util.EmptyStackException;
import java.util.List;

public class PlayerOwnedDevelopmentCardStack extends CardStack<DevelopmentCard> {

	public PlayerOwnedDevelopmentCardStack(List<DevelopmentCard> cards) {

	}

	public DevelopmentCard peek() throws EmptyStackException {
		return null;
	}

	public DevelopmentCard pop() throws EmptyStackException {
		return null;
	}

	public void pushOnTop(DevelopmentCard card) {

	}

	public boolean isPushOnTopValid(DevelopmentCard card) {
		return false;
	}

}
