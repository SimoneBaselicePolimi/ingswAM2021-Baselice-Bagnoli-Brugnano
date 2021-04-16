package it.polimi.ingsw.network.servermessage;

import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameUpdateServerMessage extends ServerMessage {

	public final Set<GameUpdate> gameUpdates;

	public GameUpdateServerMessage(Set<GameUpdate> gameUpdates) {
		this.gameUpdates = new HashSet<>(gameUpdates);
	}

}
