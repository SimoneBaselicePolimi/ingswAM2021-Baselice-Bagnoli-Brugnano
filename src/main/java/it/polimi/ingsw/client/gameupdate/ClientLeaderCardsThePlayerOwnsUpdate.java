package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.LeaderCardsThePlayerOwnsUpdateHandler;
import it.polimi.ingsw.client.model.LeaderCardRepresentation;
import it.polimi.ingsw.client.model.PlayerRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Set;

public class ClientLeaderCardsThePlayerOwnsUpdate extends ClientGameUpdate {

	@SerializeIdOnly
	public final PlayerRepresentation player;

	public final Set<LeaderCardRepresentation> leaderCardsThePlayerOwns;

	public ClientLeaderCardsThePlayerOwnsUpdate(PlayerRepresentation player, Set<LeaderCardRepresentation> leaderCardsThePlayerOwns) {
		this.player = player;
		this.leaderCardsThePlayerOwns = leaderCardsThePlayerOwns;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new LeaderCardsThePlayerOwnsUpdateHandler();
	}
}
