package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.notifier.gameupdate.GameUpdate;

import java.util.Optional;

public interface Notifier<U extends GameUpdate> {

	public abstract Optional<U> getUpdate();

}
