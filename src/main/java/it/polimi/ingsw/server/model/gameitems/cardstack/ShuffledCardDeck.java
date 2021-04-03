package it.polimi.ingsw.server.model.gameitems.cardstack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShuffledCardDeck<C> extends CardDeck<C> {

	Random randGenerator;

	public ShuffledCardDeck(List<C> objects) {
		randGenerator = new Random();
		initializeShuffledCardDeck(objects);
	}

	public ShuffledCardDeck(Random randomGenerator, List<C> objects) {
		this.randGenerator = randomGenerator;
		initializeShuffledCardDeck(objects);
	}

	private void initializeShuffledCardDeck(List<C> objects) {
		List<C> cardList = new ArrayList<>(objects);
		while (cardList.size() > 0) {
			int randNum = randGenerator.nextInt(cardList.size());
			cardDeck.push(cardList.remove(randNum));
		}
	}

}
