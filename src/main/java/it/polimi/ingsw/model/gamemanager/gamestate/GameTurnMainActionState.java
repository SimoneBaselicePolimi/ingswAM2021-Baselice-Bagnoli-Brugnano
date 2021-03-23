package it.polimi.ingsw.model.gamemanager.gamestate;

import it.polimi.ingsw.model.gamecontext.GameContext;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.clientrequest.ClientRequestLeaderAction;
import it.polimi.ingsw.network.clientrequest.ClientRequestMarketAction;
import it.polimi.ingsw.network.clientrequest.ClientRequestDevelopmentAction;
import it.polimi.ingsw.network.clientrequest.ClientRequestProductionAction;
import it.polimi.ingsw.network.servermessage.*;

import java.util.Map;

public class GameTurnMainActionState extends GameState {

	public GameTurnMainActionState(GameContext gameContext, Player player) {

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

	public Map<Player, ServerMessageLeaderAction> handleRequestLeaderAction(ClientRequestLeaderAction request) {
		return null;
	}

	public Map<Player, ServerMessageMarketAction> handleRequestMarketAction(ClientRequestMarketAction request) {
		return null;
	}

	public Map<Player, ServerMessageDevelopmentAction> handleRequestDevelopmentAction(ClientRequestDevelopmentAction request) {
		return null;
	}

	public Map<Player, ServerMessageProductionAction> handleRequestProductionAction(ClientRequestProductionAction request) {
		return null;
	}

}
