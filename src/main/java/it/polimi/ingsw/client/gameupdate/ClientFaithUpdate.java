package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.FaithUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientFaithUpdate extends ClientGameUpdate {

	public final ClientPlayerRepresentation player;
	public final int faithPositions;

	public ClientFaithUpdate(ClientPlayerRepresentation player, int faithPositions) {
		this.player = player;
		this.faithPositions = faithPositions;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new FaithUpdateHandler();
	}
}
