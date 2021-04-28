package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.Player;

public class FaithUpdate extends GameUpdate {

	public final Player player;
	public final int faithPositions;

	public FaithUpdate(Player player, int faithPositions) {
		this.player = player;
		this.faithPositions = faithPositions;
	}
}
