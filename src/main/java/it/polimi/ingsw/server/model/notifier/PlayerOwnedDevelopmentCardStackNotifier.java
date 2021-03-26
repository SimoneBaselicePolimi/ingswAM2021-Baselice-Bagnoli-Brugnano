package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardStack;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.notifier.gameupdate.PlayerOwnedDevelopmentCardStackUpdate;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

public class PlayerOwnedDevelopmentCardStackNotifier extends PlayerOwnedDevelopmentCardStack implements Notifier {

	public PlayerOwnedDevelopmentCardStackNotifier(List objects) {
		super(objects);
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
