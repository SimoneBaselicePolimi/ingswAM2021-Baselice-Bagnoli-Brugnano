package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.FaithUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.server.model.Player;

public class ClientFaithUpdate extends ClientGameUpdate {

	public final Player player;
	public final int faithPositions;

	public ClientFaithUpdate(Player player, int faithPositions) {
		this.player = player;
		this.faithPositions = faithPositions;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new FaithUpdateHandler();
	}
}
