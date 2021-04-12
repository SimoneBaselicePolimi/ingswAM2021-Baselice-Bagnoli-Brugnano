package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.Optional;

public interface Notifier<U extends GameUpdate> {

	//Side effects
	public abstract Optional<U> getUpdate();

}
