package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class Player implements Representable<ServerPlayerRepresentation>, IdentifiableItem {

	public final String playerName;

	public Player(String name) {
		playerName = name;
	}

	public Player(String name, GameItemsManager gameItemsManager) {
		playerName = name;
		gameItemsManager.addItem(this);
	}

	@Override
	public String toString() {
		return String.format("[Player: %s]", playerName);
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

	@Override
	public String getItemId() {
		return playerName;
	}

}
