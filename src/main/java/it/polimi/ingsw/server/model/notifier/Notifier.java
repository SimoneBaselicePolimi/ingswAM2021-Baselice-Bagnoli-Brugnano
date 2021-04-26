package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.Set;

public interface Notifier {

	//Side effects
	public abstract Set<GameUpdate> getUpdates();

}
