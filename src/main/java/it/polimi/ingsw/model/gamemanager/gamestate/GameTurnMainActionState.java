package it.polimi.ingsw.model.gamemanager.gamestate;

import it.polimi.ingsw.model.gamecontext.GameContext;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Map_Player,ServerMessageLeaderAction_;
import it.polimi.ingsw.network.clientrequest.ClientRequestLeaderAction;
import it.polimi.ingsw.model.Map_Player,ServerMessageMarketAction_;
import it.polimi.ingsw.network.clientrequest.ClientRequestMarketAction;
import it.polimi.ingsw.model.Map_Player,ServerMessageDevelopmentAction_;
import it.polimi.ingsw.network.clientrequest.ClientRequestDevelopmentAction;
import it.polimi.ingsw.model.Map_Player,ServerMessageProductionAction_;
import it.polimi.ingsw.network.clientrequest.ClientRequestProductionAction;

public class GameTurnMainActionState extends GameState {

	public GameTurnMainActionState(GameContext gameContext, Player player) {

	}

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

	public Map_Player,ServerMessageLeaderAction_ handleRequestLeaderAction(ClientRequestLeaderAction request) {
		return null;
	}

	public Map_Player,ServerMessageMarketAction_ handleRequestMarketAction(ClientRequestMarketAction request) {
		return null;
	}

	public Map_Player,ServerMessageDevelopmentAction_ handleRequestDevelopmentAction(ClientRequestDevelopmentAction request) {
		return null;
	}

	public Map_Player,ServerMessageProductionAction_ handleRequestProductionAction(ClientRequestProductionAction request) {
		return null;
	}

}
