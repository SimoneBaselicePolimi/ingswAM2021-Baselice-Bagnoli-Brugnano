package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;

import java.util.List;
import java.util.Map;

public class DevelopmentCardsTable {

	private Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>>> cards;

	public DevelopmentCardsTable(List<DevelopmentCard> cards) {

	}

	public List<DevelopmentCard> getAvailableCards() {
		return null;
	}

//toglie primo elemento e lo restituisce
	public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour) {
		return null;
	}

	public Map<DevelopmentCardLevel,Map<DevelopmentCardColour,DevelopmentCard>> getAvailableCardsAsMap() {
		return null;
	}

}
