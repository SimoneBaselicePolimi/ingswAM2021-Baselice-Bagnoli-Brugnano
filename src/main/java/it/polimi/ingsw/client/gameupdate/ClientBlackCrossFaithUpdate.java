package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdatehandler.BlackCrossFaithUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;

public class ClientBlackCrossFaithUpdate extends ClientGameUpdate {

	public int blackCrossFaithPosition;

	public ClientBlackCrossFaithUpdate(
		@JsonProperty("blackCrossFaithPosition") int blackCrossFaithPosition
	) {
		this.blackCrossFaithPosition = blackCrossFaithPosition;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new BlackCrossFaithUpdateHandler();
	}
}
