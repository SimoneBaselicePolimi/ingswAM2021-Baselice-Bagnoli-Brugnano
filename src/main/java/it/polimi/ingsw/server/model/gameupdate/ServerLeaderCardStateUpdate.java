package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ServerLeaderCardStateUpdate extends ServerGameUpdate {

	@SerializeIdOnly
	public final Player player;

	@SerializeIdOnly
	public final LeaderCard leaderCard;

	public final LeaderCardState leaderCardState;

	public ServerLeaderCardStateUpdate(
		@JsonProperty("player") Player player,
		@JsonProperty("leaderCard") LeaderCard leaderCard,
		@JsonProperty("leaderCardState") LeaderCardState leaderCardState
	) {
		this.player = player;
		this.leaderCard = leaderCard;
		this.leaderCardState = leaderCardState;
	}
}

