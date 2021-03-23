package it.polimi.ingsw.model.gamemanager.gamestate;

import it.polimi.ingsw.model.gamecontext.GameContext;
import it.polimi.ingsw.model.Map_Player,GameSetupSM_;
import it.polimi.ingsw.network.clientrequest.InitialChoiceCR;
import it.polimi.ingsw.model.Map_Player,PostGameSetupSM_;

public class GameSetupState extends GameState {

	public GameSetupState(GameContext gameContext) {

	}

	public Map_Player,GameSetupSM_ getInitialServerMessage() {
		return null;
	}

	public boolean isStateDone() {
		return false;
	}

	public Map<Player,ServerMessage> handleInitialChoiceCR(InitialChoiceCR request) {
		return null;
	}

	public Map_Player,PostGameSetupSM_ getFinalServerMessage() {
		return null;
	}

	public GameTurnMainActionState getNextState() {
		return null;
	}

}
