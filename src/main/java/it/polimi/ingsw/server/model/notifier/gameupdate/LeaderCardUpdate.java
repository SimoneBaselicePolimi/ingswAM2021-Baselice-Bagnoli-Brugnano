package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;

public class LeaderCardUpdate extends GameUpdate{

	public final LeaderCard leaderCard;

	public final LeaderCardState leaderCardState;

	public final boolean canBeActivated;

	public LeaderCardUpdate(LeaderCard leaderCard, LeaderCardState leaderCardState, boolean canBeActivated) {
		this.leaderCard = leaderCard;
		this.leaderCardState = leaderCardState;
		this.canBeActivated = canBeActivated;
	}
}
