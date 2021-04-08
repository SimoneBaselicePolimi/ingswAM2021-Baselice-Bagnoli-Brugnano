package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.notifier.gameupdate.PlayerOwnedDevelopmentCardStackUpdate;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

public class PlayerOwnedDevelopmentCardDeckNotifier extends PlayerOwnedDevelopmentCardDeck implements Notifier {

	public PlayerOwnedDevelopmentCardDeckNotifier() {
		super();
	}

	public Optional<PlayerOwnedDevelopmentCardStackUpdate> getUpdate() {
		return null;
	}

	public DevelopmentCard pop() throws EmptyStackException {
		return null;
	}

	public void pushOnTop(DevelopmentCard card) {

	}

}
