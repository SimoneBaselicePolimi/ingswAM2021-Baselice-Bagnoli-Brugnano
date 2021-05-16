package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientLeaderCardCanBeActivatedUpdate extends ClientGameUpdate {

	@SerializeIdOnly
	public final LeaderCard leaderCard;

	public final boolean canBeActivated;

	public ClientLeaderCardCanBeActivatedUpdate(LeaderCard leaderCard, boolean canBeActivated) {
		this.leaderCard = leaderCard;
		this.canBeActivated = canBeActivated;
	}
}

