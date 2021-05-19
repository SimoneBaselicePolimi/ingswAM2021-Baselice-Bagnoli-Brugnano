package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.LeaderCardStateUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientLeaderCardStateUpdate extends ClientGameUpdate {

	@SerializeIdOnly
	public final ClientLeaderCardRepresentation leaderCard;

	public final LeaderCardState leaderCardState;

	public ClientLeaderCardStateUpdate(ClientLeaderCardRepresentation leaderCard, LeaderCardState leaderCardState) {
		this.leaderCard = leaderCard;
		this.leaderCardState = leaderCardState;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new LeaderCardStateUpdateHandler();
	}
}

