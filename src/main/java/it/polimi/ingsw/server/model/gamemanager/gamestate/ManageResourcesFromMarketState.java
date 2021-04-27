package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Map;

public class ManageResourcesFromMarketState extends GameState {

    public ManageResourcesFromMarketState(GameManager gameManager) {
        super(gameManager);
    }

    public Map<Player, ServerMessage> getInitialServerMessage() {
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

	public Map<Player,ServerMessage> handleRequestManageResourcesFromMarket(ManageResourcesFromMarketClientRequest request) {
		return null;
	}

}
