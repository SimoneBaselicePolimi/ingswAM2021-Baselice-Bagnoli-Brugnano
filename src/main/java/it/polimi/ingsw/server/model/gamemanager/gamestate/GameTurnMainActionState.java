package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.LeaderActionClientRequest;
import it.polimi.ingsw.network.clientrequest.MarketActionClientRequest;
import it.polimi.ingsw.network.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.network.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.network.servermessage.*;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Map;

public class GameTurnMainActionState extends GameState {

	//Quando faccio azione leader metto a true
	private boolean mainActionDone = false;

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
		return mainActionDone;
	}

	public GameState getNextState() {
		return new GameTurnPostActionState(gameManager);
	}

	@Override
	public Map<Player, ServerMessage> handleRequestLeaderAction(LeaderActionClientRequest request) {
		// check if the player has already done the main action
		if(mainActionDone)
			return createInvalidRequestServerMessage(
				request.player,
				"The player has already done his main action"
			);
		return null;
	}

	@Override
	public Map<Player, MarketActionServerMessage> handleRequestMarketAction(MarketActionClientRequest request) {
		return null;
	}

	@Override
	public Map<Player, ServerMessage> handleRequestDevelopmentAction(DevelopmentActionClientRequest request) {
		return null;
	}

	@Override
	public Map<Player, ProductionActionServerMessage> handleRequestProductionAction(ProductionActionClientRequest request) {
		return null;
	}

}
