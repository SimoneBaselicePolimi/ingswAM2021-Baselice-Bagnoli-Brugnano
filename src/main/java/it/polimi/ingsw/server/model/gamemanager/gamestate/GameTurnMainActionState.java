package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.LeaderActionClientRequest;
import it.polimi.ingsw.network.clientrequest.MarketActionClientRequest;
import it.polimi.ingsw.network.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.network.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.network.servermessage.*;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Map;

public class GameTurnMainActionState extends GameState {

	public GameTurnMainActionState(GameManager gameManager) {

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

	public Map<Player, LeaderActionServerMessage> handleRequestLeaderAction(LeaderActionClientRequest request) {
		return null;
	}

	public Map<Player, MarketActionServerMessage> handleRequestMarketAction(MarketActionClientRequest request) {
		return null;
	}

	public Map<Player, DevelopmentActionServerMessage> handleRequestDevelopmentAction(DevelopmentActionClientRequest request) {
		return null;
	}

	public Map<Player, ProductionActionServerMessage> handleRequestProductionAction(ProductionActionClientRequest request) {
		return null;
	}

}
