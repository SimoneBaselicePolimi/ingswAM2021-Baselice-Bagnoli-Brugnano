package it.polimi.ingsw.server.model.notifier.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;

public class ServerFaithUpdate extends ServerGameUpdate {

	public final Player player;
	public final int faithPositions;

	public ServerFaithUpdate(
		@JsonProperty("player") Player player,
		@JsonProperty("faithPositions") int faithPositions
	) {
		this.player = player;
		this.faithPositions = faithPositions;
	}
}
