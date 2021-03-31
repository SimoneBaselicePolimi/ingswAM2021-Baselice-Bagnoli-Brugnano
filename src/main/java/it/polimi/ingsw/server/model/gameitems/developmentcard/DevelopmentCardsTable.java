package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevelopmentCardsTable {

	private Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>>> cards = new HashMap<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>>>();

	public DevelopmentCardsTable(List<DevelopmentCard> developmentCards) {
		Map <DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>> mapColors = new HashMap<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>>();
		for (DevelopmentCard card : developmentCards){
			for (DevelopmentCardLevel level : card.getLevel().values()){
				for (DevelopmentCardColour colour : card.getColour().values()){
					List <DevelopmentCard> deck = new ArrayList<DevelopmentCard>();
					deck.add(card);
					ShuffledCardDeck<DevelopmentCard> shuffledCardDeck = new ShuffledCardDeck(deck);
					mapColors.put(colour,shuffledCardDeck);
					}
				cards.put(level, mapColors);
				}
			}
	}

	public List<DevelopmentCard> getAvailableCards() {
	List<DevelopmentCard> availableCards = new ArrayList<DevelopmentCard>();
		for (DevelopmentCardLevel level : cards.keySet()){
			for (Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>> value : cards.values()) {
				for (DevelopmentCardColour colour : value.keySet()) {
					for (ShuffledCardDeck<DevelopmentCard> deck : value.values())
						availableCards.add(deck.peek());
				}
			}
		}
		return availableCards;
	}

	public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour) {
		for (DevelopmentCardLevel cardlevel : cards.keySet()) {
			for (Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>> value : cards.values()) {
				for (DevelopmentCardColour cardColour : value.keySet()) {
					for (ShuffledCardDeck <DevelopmentCard> deck : value.values()) {
						if (cardlevel == level && cardColour == colour)
							return(deck.pop());
					}
				}
			}
		}
	}

	public Map<DevelopmentCardLevel,Map<DevelopmentCardColour,DevelopmentCard>> getAvailableCardsAsMap() {
		return null;
	}

}
