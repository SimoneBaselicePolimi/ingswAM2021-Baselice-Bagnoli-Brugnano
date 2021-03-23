package it.polimi.ingsw.server.model.gameitems.cardstack;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;

import java.util.List;

public class PlayerOwnedDevelopmentCardStack extends CardStack<DevelopmentCard> {

	public PlayerOwnedDevelopmentCardStack(List<DevelopmentCard> objects) {

	}

	public DevelopmentCard peek() {
		return null;
	}

	public DevelopmentCard pop() {
		return null;
	}

	public void pushOnTop(DevelopmentCard card) {

	}

	public boolean isPushOnTopValid(DevelopmentCard card) {
		return false;
	}

}
