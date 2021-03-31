package it.polimi.ingsw.server.model.gameitems.cardstack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShuffledCardDeck<C> extends CardDeck<C> {

	public ShuffledCardDeck(List<C> objects) {
		Random randGenerator = new Random();
		List<C> cardList = new ArrayList<>(objects);
		while(cardList.size() > 0) {
			int randNum = randGenerator.nextInt(cardList.size());
			cardDeck.push(cardList.remove(randNum));
		}
	}

	public ShuffledCardDeck(Random randomGenerator, List<C> objects) {
		List<C> cardList = new ArrayList<>(objects);
		while(cardList.size() > 0) {
			int randNum = randomGenerator.nextInt(cardList.size());
			cardDeck.push(cardList.remove(randNum));
		}
	}

}
