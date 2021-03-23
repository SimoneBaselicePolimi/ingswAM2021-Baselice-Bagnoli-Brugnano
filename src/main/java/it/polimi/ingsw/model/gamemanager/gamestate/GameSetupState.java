package it.polimi.ingsw.model.gamemanager.gamestate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gamecontext.GameContext;
import it.polimi.ingsw.network.clientrequest.InitialChoiceCR;
import it.polimi.ingsw.network.servermessage.GameSetupSM;
import it.polimi.ingsw.network.servermessage.PostGameSetupSM;
import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.Map;

public class GameSetupState extends GameState<GameSetupSM, PostGameSetupSM> {

	public GameSetupState(GameContext gameContext) {

	}

	@Override
	public Map<Player, GameSetupSM> getInitialServerMessage() {
		return null;
	}

	public boolean isStateDone() {
		return false;
	}

	public Map<Player, ServerMessage> handleInitialChoiceCR(InitialChoiceCR request) {
		return null;
	}

	public Map<Player, PostGameSetupSM> getFinalServerMessage() {
		return null;
	}

	public GameTurnMainActionState getNextState() {
		return null;
	}

}
