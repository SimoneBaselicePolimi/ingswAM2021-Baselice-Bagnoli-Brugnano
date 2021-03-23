package it.polimi.ingsw.model.gamemanager.gamestate;

import it.polimi.ingsw.model.gamecontext.GameContext;
import it.polimi.ingsw.model.gamehistory.GameHistory;
import it.polimi.ingsw.network.clientrequest.InitialChoiceCR;
import it.polimi.ingsw.model.Map_Player,ServerMessageLeaderAction_;
import it.polimi.ingsw.network.clientrequest.ClientRequestLeaderAction;
import it.polimi.ingsw.model.Map_Player,ServerMessageMarketAction_;
import it.polimi.ingsw.network.clientrequest.ClientRequestMarketAction;
import it.polimi.ingsw.network.clientrequest.ClientRequestManageResourcesFromMarket;
import it.polimi.ingsw.network.clientrequest.ClientRequestCustom;
import it.polimi.ingsw.model.Map_Player,ServerMessageDevelopmentAction_;
import it.polimi.ingsw.network.clientrequest.ClientRequestDevelopmentAction;
import it.polimi.ingsw.model.Map_Player,ServerMessageProductionAction_;
import it.polimi.ingsw.network.clientrequest.ClientRequestProductionAction;
import it.polimi.ingsw.network.clientrequest.ClientRequestEndTurn;

public abstract class GameState {

	private GameContext gameContext;

	private GameHistory gameHistory;

	public abstract Map<Player,ServerMessage> getInitialServerMessage();

	public abstract Map<Player,ServerMessage> getFinalServerMessage();

	public abstract boolean isStateDone();

	public abstract GameState getNextState();

	public Map<Player,ServerMessage> handleInitialChoiceCR(InitialChoiceCR request) {
		return null;
	}

	public Map_Player,ServerMessageLeaderAction_ handleRequestLeaderAction(ClientRequestLeaderAction request) {
		return null;
	}

	public Map_Player,ServerMessageMarketAction_ handleRequestMarketAction(ClientRequestMarketAction request) {
		return null;
	}

	public Map<Player,ServerMessage> handleRequestManageResourcesFromMarket(ClientRequestManageResourcesFromMarket request) {
		return null;
	}

	public Map<Player,ServerMessage> handleRequestCustom(ClientRequestCustom request) {
		return null;
	}

	public Map_Player,ServerMessageDevelopmentAction_ handleRequestDevelopmentAction(ClientRequestDevelopmentAction request) {
		return null;
	}

	public Map_Player,ServerMessageProductionAction_ handleRequestProductionAction(ClientRequestProductionAction request) {
		return null;
	}

	public Map<Player,ServerMessageEndTurn> handleRequestEndTurn(ClientRequestEndTurn request) {
		return null;
	}

}
