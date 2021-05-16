package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.LeaderCardCanBeActivatedUpdateHandler;
import it.polimi.ingsw.client.model.LeaderCardRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientLeaderCardCanBeActivatedUpdate extends ClientGameUpdate {

	@SerializeIdOnly
	public final LeaderCardRepresentation leaderCard;

	public final boolean canBeActivated;

	public ClientLeaderCardCanBeActivatedUpdate(LeaderCardRepresentation leaderCard, boolean canBeActivated) {
		this.leaderCard = leaderCard;
		this.canBeActivated = canBeActivated;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new LeaderCardCanBeActivatedUpdateHandler();
	}
}

