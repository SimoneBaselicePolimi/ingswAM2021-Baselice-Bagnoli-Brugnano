package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.serialization.SerializeIdOnly;

public class LeaderCardUpdate extends GameUpdate{

	@SerializeIdOnly
	public final LeaderCard leaderCard;

	public final LeaderCardState leaderCardState;

	public final boolean canBeActivated;

	public LeaderCardUpdate(LeaderCard leaderCard, LeaderCardState leaderCardState, boolean canBeActivated) {
		this.leaderCard = leaderCard;
		this.leaderCardState = leaderCardState;
		this.canBeActivated = canBeActivated;
	}
}

