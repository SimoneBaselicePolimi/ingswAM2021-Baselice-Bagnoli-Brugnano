package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class LeaderCardCanBeActivatedUpdate extends GameUpdate{

	@SerializeIdOnly
	public final LeaderCard leaderCard;

	public final boolean canBeActivated;

	public LeaderCardCanBeActivatedUpdate(LeaderCard leaderCard, boolean canBeActivated) {
		this.leaderCard = leaderCard;
		this.canBeActivated = canBeActivated;
	}
}

