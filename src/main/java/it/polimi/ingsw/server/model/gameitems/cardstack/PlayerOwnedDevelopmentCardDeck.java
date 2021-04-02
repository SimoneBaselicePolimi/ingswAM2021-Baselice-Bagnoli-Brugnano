package it.polimi.ingsw.server.model.gameitems.cardstack;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.List;

public class PlayerOwnedDevelopmentCardDeck extends CardDeck<DevelopmentCard> {

	public PlayerOwnedDevelopmentCardDeck(List<DevelopmentCard> cards) {
		for(DevelopmentCard card : cards)
			cardDeck.push(card);
	}

	public void pushOnTop(DevelopmentCard card) throws ForbiddenPushOnTopException{
		if(isPushOnTopValid(card))
			cardDeck.push(card);
		else throw new ForbiddenPushOnTopException();
	}

	public boolean isPushOnTopValid(DevelopmentCard card) {
		if(card.getLevel() == DevelopmentCardLevel.FIRST_LEVEL && cardDeck.isEmpty())
			return true;
		if(card.getLevel() == DevelopmentCardLevel.SECOND_LEVEL && cardDeck.peek().getLevel() == DevelopmentCardLevel.FIRST_LEVEL)
			return true;
		return card.getLevel() == DevelopmentCardLevel.THIRD_LEVEL && cardDeck.peek().getLevel() == DevelopmentCardLevel.SECOND_LEVEL;
	}

}
