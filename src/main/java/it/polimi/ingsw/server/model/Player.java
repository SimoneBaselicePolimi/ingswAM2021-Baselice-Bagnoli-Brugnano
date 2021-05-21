package it.polimi.ingsw.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class Player implements Representable<ServerPlayerRepresentation>, IdentifiableItem {

	public final String playerName;

	@JsonCreator
	public Player(
		@JsonProperty("playerName") String name
	) {
		playerName = name;
	}

	public Player(
		@JsonProperty("playerName") String name,
		GameItemsManager gameItemsManager
	) {
		playerName = name;
		gameItemsManager.addItem(this);
	}

	@Override
	public String toString() {
		return String.format("[Player: %s]", playerName);
	}

	@JsonIgnore
	public String getName() {
		return getItemId();
	}

	@Override
	@JsonIgnore
	public ServerPlayerRepresentation getServerRepresentation() {
		return new ServerPlayerRepresentation(getName());
	}

	@Override
	@JsonIgnore
	public ServerPlayerRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}

	@Override
	@JsonIgnore
	public String getItemId() {
		return playerName;
	}

}
