package it.polimi.ingsw.server.model.notifier.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ServerLeaderCardCanBeActivatedUpdate extends ServerGameUpdate {

	@SerializeIdOnly
	public final LeaderCard leaderCard;

	public final boolean canBeActivated;

	public ServerLeaderCardCanBeActivatedUpdate(
		@JsonProperty("leaderCard") LeaderCard leaderCard,
		@JsonProperty("canBeActivated") boolean canBeActivated
	) {
		this.leaderCard = leaderCard;
		this.canBeActivated = canBeActivated;
	}
}

