package it.polimi.ingsw.client.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.gameupdate.ClientGameUpdate;

import java.util.HashSet;
import java.util.Set;

public class GameUpdateServerMessage extends ServerMessage {

	public final Set<ClientGameUpdate> gameUpdates;

	public GameUpdateServerMessage(
		@JsonProperty("gameUpdates") Set<ClientGameUpdate> gameUpdates
	) {
		this.gameUpdates = new HashSet<>(gameUpdates);
	}

}
