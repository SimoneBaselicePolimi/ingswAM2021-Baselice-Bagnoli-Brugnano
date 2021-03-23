package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.gameitems.cardstack.PlayerOwnedDevelopmentCardStack;
import it.polimi.ingsw.model.gameitems.developmentcard.DevelopmentCard;

public class PlayerOwnedDevelopmentCardStackNotifier extends PlayerOwnedDevelopmentCardStack implements Notifier {

	public Option<PlayerOwnedDevelopmentCardStackUpdate> getUpdate() {
		return null;
	}

	public DevelopmentCard pop() {
		return null;
	}

	public void pushOnTop(DevelopmentCard card) {

	}


	/**
	 * @see Notifier#getUpdate()
	 */
	public Optional<GameUpdate> getUpdate() {
		return null;
	}

}
