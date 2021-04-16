package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;

import java.util.Set;

public class PlayerContextUpdate extends GameUpdate {

	public final Player player;

	public final Set<LeaderCard> leaderCardsThePlayerOwns;

	public PlayerContextUpdate(Player player, Set<LeaderCard> leaderCardsThePlayerOwns) {
		this.player = player;
		this.leaderCardsThePlayerOwns = leaderCardsThePlayerOwns;
	}

}
