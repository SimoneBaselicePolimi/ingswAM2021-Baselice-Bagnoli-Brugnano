package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.notifier.gameupdate.PlayerContextUpdate;

import java.util.Optional;

public class PlayerContextNotifier extends PlayerContext implements Notifier<PlayerContextUpdate> {

	public Optional<PlayerContextUpdate> getUpdate() {
		return null;
	}

	public void activeLeaderCard(DevelopmentCard card) {

	}

	public void discardLeaderCard(DevelopmentCard card) {

	}

}
