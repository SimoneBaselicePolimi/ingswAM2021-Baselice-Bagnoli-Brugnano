package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;

import java.util.*;
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
		for (Map<DevelopmentCardColour, ShuffledCardDeck<DevelopmentCard>> value : cards.values()) {
			for (ShuffledCardDeck<DevelopmentCard> deck : value.values())
				availableCards.add(deck.peek());
		}
		return availableCards;
	}


	public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour) throws EmptyStackException{
		return (this.getDeckByLevelAndColour(level, colour).pop());
	}


	public Map<DevelopmentCardLevel,Map<DevelopmentCardColour,DevelopmentCard>> getAvailableCardsAsMap() {
		Map<DevelopmentCardLevel, Map<DevelopmentCardColour, DevelopmentCard>> map =
				getAvailableCards().stream().collect(
						Collectors.groupingBy(
								DevelopmentCard::getLevel,
								Collectors.toMap(
										DevelopmentCard::getColour,
										Function.identity()
								)
						)
				);
		return map;
	}

	public ShuffledCardDeck<DevelopmentCard> getDeckByLevelAndColour (DevelopmentCardLevel cardLevel, DevelopmentCardColour cardColour) throws IllegalArgumentException{
		if (!cards.containsKey(cardLevel) || !cards.get(cardLevel).containsKey(cardColour))
			throw new IllegalArgumentException();
		return(cards.get(cardLevel).get(cardColour));
	}
}