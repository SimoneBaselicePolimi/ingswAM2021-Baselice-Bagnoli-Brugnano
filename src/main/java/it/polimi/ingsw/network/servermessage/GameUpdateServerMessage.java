package it.polimi.ingsw.network.servermessage;

import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.HashSet;
import java.util.Set;

public class GameUpdateServerMessage extends ServerMessage {

	public final Set<ServerGameUpdate> gameUpdates;

	public GameUpdateServerMessage(Set<ServerGameUpdate> gameUpdates) {
		this.gameUpdates = new HashSet<>(gameUpdates);
	}

}
