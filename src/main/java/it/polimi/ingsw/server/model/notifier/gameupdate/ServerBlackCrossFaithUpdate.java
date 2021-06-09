package it.polimi.ingsw.server.model.notifier.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerBlackCrossFaithUpdate extends ServerGameUpdate {

	public int blackCrossFaithPosition;

	public ServerBlackCrossFaithUpdate(
		@JsonProperty("blackCrossFaithPosition") int blackCrossFaithPosition
	) {
		this.blackCrossFaithPosition = blackCrossFaithPosition;
	}
}
