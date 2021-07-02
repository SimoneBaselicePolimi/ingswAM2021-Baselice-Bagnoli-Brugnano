package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Set;

public class ServerLeaderCardsThePlayerOwnsUpdate extends ServerGameUpdate {

	@SerializeIdOnly
	public final Player player;

	@SerializeAsSetOfIds
	public final Set<LeaderCard> leaderCardsThePlayerOwns;

	public ServerLeaderCardsThePlayerOwnsUpdate(
		@JsonProperty("player") Player player,
		@JsonProperty("leaderCardsThePlayerOwns") Set<LeaderCard> leaderCardsThePlayerOwns
	) {
		this.player = player;
		this.leaderCardsThePlayerOwns = leaderCardsThePlayerOwns;
	}

}
