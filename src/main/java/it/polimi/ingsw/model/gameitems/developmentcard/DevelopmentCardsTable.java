package it.polimi.ingsw.model.gameitems.developmentcard;

public class DevelopmentCardsTable {

	private Map<DevelopmentCardLevel,Map<DevelopmentCardColour,ShuffledCardStack<DevelopmentCard>>> cards;

	public DevelopmentCardsTable(List<DevelopmentCard> cards) {

	}

	public List<DevelopmentCard> getAvailableCards() {
		return null;
	}

	public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour) {
		return null;
	}

	public Map<DevelopmentCardLevel,Map<DevelopmentCardColour,DevelopmentCard>> getAvailableCardsAsMap() {
		return null;
	}

}
