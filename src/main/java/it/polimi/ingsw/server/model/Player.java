package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;

public class Player extends RegisteredIdentifiableItem {

	public Player(String name, GameItemsManager gameItemsManager) {
		super(name, gameItemsManager);
	}

	public String getName() {
		return getItemId();
	}

}
