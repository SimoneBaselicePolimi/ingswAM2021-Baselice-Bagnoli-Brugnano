package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.network.clientrequest.InitialChoiceClientRequest;
import it.polimi.ingsw.network.servermessage.GameSetupServerMessage;
import it.polimi.ingsw.network.servermessage.PostGameSetupServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.Map;

public class GameSetupState extends GameState<GameSetupServerMessage, PostGameSetupServerMessage> {

	public GameSetupState(GameContext gameContext) {

	}

	@Override
	public Map<Player, GameSetupServerMessage> getInitialServerMessage() {
		return null;
	}

	public boolean isStateDone() {
		return false;
	}

	public Map<Player, ServerMessage> handleInitialChoiceCR(InitialChoiceClientRequest request) {
		return null;
	}

	public Map<Player, PostGameSetupServerMessage> getFinalServerMessage() {
		return null;
	}

	public GameTurnMainActionState getNextState() {
		return null;
	}

}
