package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Set;

public class ClientLeaderCardsThePlayerOwnsUpdate extends ClientGameUpdate {

	@SerializeIdOnly
	public final Player player;

	public final Set<LeaderCard> leaderCardsThePlayerOwns;

	public ClientLeaderCardsThePlayerOwnsUpdate(Player player, Set<LeaderCard> leaderCardsThePlayerOwns) {
		this.player = player;
		this.leaderCardsThePlayerOwns = leaderCardsThePlayerOwns;
	}

}
