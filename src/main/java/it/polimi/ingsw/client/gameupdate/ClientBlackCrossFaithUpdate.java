package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.BlackCrossFaithUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;

public class ClientBlackCrossFaithUpdate extends ClientGameUpdate {

	public int blackCrossFaithPosition;

	@Override
	public GameUpdateHandler getHandler() {
		return new BlackCrossFaithUpdateHandler();
	}
}
