package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.serialization.SerializeIdOnly;

public class LeaderCardStateUpdate extends GameUpdate{

	@SerializeIdOnly
	public final LeaderCard leaderCard;

	public final LeaderCardState leaderCardState;

	public LeaderCardStateUpdate(LeaderCard leaderCard, LeaderCardState leaderCardState) {
		this.leaderCard = leaderCard;
		this.leaderCardState = leaderCardState;
	}
}

