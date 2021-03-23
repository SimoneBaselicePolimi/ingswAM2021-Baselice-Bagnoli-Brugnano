package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.Player;

import java.util.List;

public class PlayerContextUpdate extends GameUpdate {

	public List<LeaderCardUpdate> leaderCardsUpdate;

	public Player player;

}
