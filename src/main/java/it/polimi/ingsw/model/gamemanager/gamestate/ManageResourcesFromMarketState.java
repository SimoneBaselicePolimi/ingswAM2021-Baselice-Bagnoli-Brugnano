package it.polimi.ingsw.model.gamemanager.gamestate;

import it.polimi.ingsw.network.clientrequest.ClientRequestManageResourcesFromMarket;

public class ManageResourcesFromMarketState extends GameState {

	public Map<Player,ServerMessage> getInitialServerMessage() {
		return null;
	}

	public Map<Player,ServerMessage> getFinalServerMessage() {
		return null;
	}

	public boolean isStateDone() {
		return false;
	}

	public GameState getNextState() {
		return null;
	}

	public Map<Player,ServerMessage> handleRequestManageResourcesFromMarket(ClientRequestManageResourcesFromMarket request) {
		return null;
	}

}
