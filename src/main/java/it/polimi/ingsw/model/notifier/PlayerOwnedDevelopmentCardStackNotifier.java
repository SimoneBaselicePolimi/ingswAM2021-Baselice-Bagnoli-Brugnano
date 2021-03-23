package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.gameitems.cardstack.PlayerOwnedDevelopmentCardStack;
import it.polimi.ingsw.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.model.notifier.gameupdate.PlayerOwnedDevelopmentCardStackUpdate;

import java.util.List;
import java.util.Optional;

public class PlayerOwnedDevelopmentCardStackNotifier extends PlayerOwnedDevelopmentCardStack implements Notifier {

	public PlayerOwnedDevelopmentCardStackNotifier(List objects) {
		super(objects);
	}

	public Optional<PlayerOwnedDevelopmentCardStackUpdate> getUpdate() {
		return null;
	}

	public DevelopmentCard pop() {
		return null;
	}

	public void pushOnTop(DevelopmentCard card) {

	}

}
