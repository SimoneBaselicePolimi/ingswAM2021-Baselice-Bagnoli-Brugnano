package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerBlackCrossFaithUpdate extends ServerGameUpdate {

	public int blackCrossFaithPosition;

	public ServerBlackCrossFaithUpdate(
		@JsonProperty("blackCrossFaithPosition") int blackCrossFaithPosition
	) {
		this.blackCrossFaithPosition = blackCrossFaithPosition;
	}
}
