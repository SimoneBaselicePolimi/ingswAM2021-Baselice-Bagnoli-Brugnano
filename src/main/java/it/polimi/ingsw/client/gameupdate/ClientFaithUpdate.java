package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.FaithUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.model.PlayerRepresentation;

public class ClientFaithUpdate extends ClientGameUpdate {

	public final PlayerRepresentation player;
	public final int faithPositions;

	public ClientFaithUpdate(PlayerRepresentation player, int faithPositions) {
		this.player = player;
		this.faithPositions = faithPositions;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new FaithUpdateHandler();
	}
}
