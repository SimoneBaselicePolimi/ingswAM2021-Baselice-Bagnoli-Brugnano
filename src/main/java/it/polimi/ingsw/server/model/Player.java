package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class Player extends RegisteredIdentifiableItem implements Representable<ServerPlayerRepresentation>{

	public Player(String name, GameItemsManager gameItemsManager) {
		super(name, gameItemsManager);
	}

	public String getName() {
		return getItemId();
	}

	@Override
	public ServerPlayerRepresentation getServerRepresentation() {
		return new ServerPlayerRepresentation(getName());
	}

	@Override
	public ServerPlayerRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}
}
