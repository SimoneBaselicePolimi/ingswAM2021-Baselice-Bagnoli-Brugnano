package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientLeaderCardStateUpdate extends ClientGameUpdate {

	@SerializeIdOnly
	public final LeaderCard leaderCard;

	public final LeaderCardState leaderCardState;

	public ClientLeaderCardStateUpdate(LeaderCard leaderCard, LeaderCardState leaderCardState) {
		this.leaderCard = leaderCard;
		this.leaderCardState = leaderCardState;
	}
}

