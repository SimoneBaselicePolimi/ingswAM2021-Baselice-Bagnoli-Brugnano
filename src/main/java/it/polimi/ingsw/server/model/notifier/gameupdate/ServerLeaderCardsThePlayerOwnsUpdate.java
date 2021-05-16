package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Set;

public class ServerLeaderCardsThePlayerOwnsUpdate extends ServerGameUpdate {

	@SerializeIdOnly
	public final Player player;

	public final Set<LeaderCard> leaderCardsThePlayerOwns;

	public ServerLeaderCardsThePlayerOwnsUpdate(Player player, Set<LeaderCard> leaderCardsThePlayerOwns) {
		this.player = player;
		this.leaderCardsThePlayerOwns = leaderCardsThePlayerOwns;
	}

}
