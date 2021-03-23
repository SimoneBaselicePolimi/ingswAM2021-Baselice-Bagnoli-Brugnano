package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.model.gameitems.developmentcard.DevelopmentCard;

public class PlayerContextNotifier extends PlayerContext implements Notifier {

	public Optional<PlayerContextUpdate> getUpdate() {
		return null;
	}

	public void activeLeaderCard(DevelopmentCard card) {

	}

	public void discardLeaderCard(DevelopmentCard card) {

	}


	/**
	 * @see Notifier#getUpdate()
	 */
	public Optional<GameUpdate> getUpdate() {
		return null;
	}

}
