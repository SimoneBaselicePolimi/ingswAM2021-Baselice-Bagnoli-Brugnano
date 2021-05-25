package it.polimi.ingsw.client.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.HashSet;
import java.util.Set;

public class GameUpdateServerMessage extends ServerMessage {

	public final Set<ServerGameUpdate> gameUpdates;

	public GameUpdateServerMessage(
		@JsonProperty("gameUpdates") Set<ServerGameUpdate> gameUpdates
	) {
		this.gameUpdates = new HashSet<>(gameUpdates);
	}

}
