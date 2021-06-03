package it.polimi.ingsw.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

import java.util.Objects;

public class Player implements IdentifiableItem {

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
		return getItemID();
	}

	@Override
	@JsonIgnore
	public String getItemID() {
		return playerName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return playerName.equals(player.playerName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerName);
	}
}
