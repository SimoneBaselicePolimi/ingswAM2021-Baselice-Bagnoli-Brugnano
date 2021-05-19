package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.Player;

public class ServerFaithUpdate extends ServerGameUpdate {

	public final Player player;
	public final int faithPositions;

	public ServerFaithUpdate(Player player, int faithPositions) {
		this.player = player;
		this.faithPositions = faithPositions;
	}
}
