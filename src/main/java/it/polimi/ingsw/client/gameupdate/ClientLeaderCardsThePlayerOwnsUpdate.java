package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.LeaderCardsThePlayerOwnsUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Set;

public class ClientLeaderCardsThePlayerOwnsUpdate extends ClientGameUpdate {

	@SerializeIdOnly
	public final Player player;

	@SerializeAsSetOfIds
	public final Set<ClientLeaderCardRepresentation> leaderCardsThePlayerOwns;

	public ClientLeaderCardsThePlayerOwnsUpdate(
		@JsonProperty("player") Player player,
		@JsonProperty("leaderCardsThePlayerOwns") Set<ClientLeaderCardRepresentation> leaderCardsThePlayerOwns) {
		this.player = player;
		this.leaderCardsThePlayerOwns = leaderCardsThePlayerOwns;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new LeaderCardsThePlayerOwnsUpdateHandler();
	}
}
