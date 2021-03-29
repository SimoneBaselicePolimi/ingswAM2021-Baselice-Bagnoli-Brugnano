package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.notifier.gameupdate.PlayerContextUpdate;

import java.util.Optional;

public class PlayerContextNotifier extends PlayerContext implements Notifier<PlayerContextUpdate> {


	/**
	 * Creates the player context associated to a specific player. At any moment after the beginning of the game there
	 * should be one and only one instance of this class for each player.
	 *
	 * @param player the player associated with this player context
	 */
	protected PlayerContextNotifier(Player player) {
		super(player);
	}

	public Optional<PlayerContextUpdate> getUpdate() {
		return null;
	}

	public void activeLeaderCard(DevelopmentCard card) {

	}

	public void discardLeaderCard(DevelopmentCard card) {

	}

}
