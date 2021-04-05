package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DevelopmentCardsTable {

	private Map<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>>> cards = new HashMap<DevelopmentCardLevel, Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>>>();

	public DevelopmentCardsTable(List<DevelopmentCard> cards) {
		this.cards = cards.stream().collect(Collectors.groupingBy(
				DevelopmentCard::getLevel,
				Collectors.groupingBy(
						DevelopmentCard::getColour,
						Collectors.collectingAndThen(Collectors.toList(), ShuffledCardDeck::new)
				)
		));
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

	public DevelopmentCard popCard (DevelopmentCardLevel level, DevelopmentCardColour colour) {
		DevelopmentCard cardToRemove = new DevelopmentCard(null, null, null, 0);
		for (DevelopmentCardLevel cardlevel : cards.keySet()) {
			for (Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>> value : cards.values()) {
				for (DevelopmentCardColour cardColour : value.keySet()) {
					for (ShuffledCardDeck <DevelopmentCard> deck : value.values()) {
						if (cardlevel == level && cardColour == colour)
							cardToRemove = deck.pop();
					}
				}
			}
		}
		return cardToRemove;
	}

	public Map<DevelopmentCardLevel,Map<DevelopmentCardColour,DevelopmentCard>> getAvailableCardsAsMap() {
		List<DevelopmentCard> availableCards = this.getAvailableCards();
		Map<DevelopmentCardLevel, Map<DevelopmentCardColour, DevelopmentCard>> cardsToReturn = new HashMap<DevelopmentCardLevel, Map<DevelopmentCardColour, DevelopmentCard>>();
		for (DevelopmentCard card : availableCards) {
			cardsToReturn.put(card.getLevel(), Map.of(card.getColour(), card));
		}
		return cardsToReturn;
	}

	public List<ShuffledCardDeck<DevelopmentCard>> getDeckByLevelAndColour (DevelopmentCardLevel cardLevel, DevelopmentCardColour cardColour){
		List<ShuffledCardDeck<DevelopmentCard>> deckByLevelAndColour = new ArrayList<ShuffledCardDeck<DevelopmentCard>>();
		for (DevelopmentCardLevel level : cards.keySet()) {
			for (Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>> value : cards.values()) {
				for (DevelopmentCardColour colour : value.keySet()) {
					for (ShuffledCardDeck<DevelopmentCard> deck : value.values()) {
						if (level.equals(cardLevel) && colour.equals(cardColour))
							deckByLevelAndColour.add(deck);
					}
				}
			}
		}
		return deckByLevelAndColour;
	}
}