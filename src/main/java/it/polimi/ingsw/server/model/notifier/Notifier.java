package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.Set;

public interface Notifier {

	//Side effects
	public abstract Set<ServerGameUpdate> getUpdates();

}
