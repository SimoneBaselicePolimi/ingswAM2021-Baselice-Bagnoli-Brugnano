package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.LeaderCardCanBeActivatedUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientLeaderCardCanBeActivatedUpdate extends ClientGameUpdate {

	@SerializeIdOnly
	public final ClientLeaderCardRepresentation leaderCard;

	public final boolean canBeActivated;

	public ClientLeaderCardCanBeActivatedUpdate(
		@JsonProperty("leaderCard") ClientLeaderCardRepresentation leaderCard,
		@JsonProperty("canBeActivated") boolean canBeActivated
	) {
		this.leaderCard = leaderCard;
		this.canBeActivated = canBeActivated;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new LeaderCardCanBeActivatedUpdateHandler();
	}
}

