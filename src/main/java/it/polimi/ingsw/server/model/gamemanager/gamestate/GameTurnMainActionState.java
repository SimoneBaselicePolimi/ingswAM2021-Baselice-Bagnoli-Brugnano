package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.LeaderActionClientRequest;
import it.polimi.ingsw.network.clientrequest.MarketActionClientRequest;
import it.polimi.ingsw.network.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.network.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.network.servermessage.*;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamehistory.MainTurnFinalAction;
import it.polimi.ingsw.server.model.gamehistory.MainTurnInitialAction;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameTurnMainActionState extends GameState {

	//true after LeaderAction
	private boolean mainActionDone = false;
	private final Player activePlayer;

	public GameTurnMainActionState(GameManager gameManager) {
		super(gameManager);
		activePlayer = gameManager.getGameContext().getActivePlayer();
    }

	public Map<Player, ServerMessage> getInitialServerMessage() {
		gameManager.getGameHistory().addAction(
			new MainTurnInitialAction(activePlayer)
		);
		//empty serverMessage
		 return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
					player ->  new ServerMessage()
				));
	}

	public Map<Player,ServerMessage> getFinalServerMessage() {
		gameManager.getGameHistory().addAction(
			new MainTurnFinalAction(activePlayer)
		);
		//empty serverMessage
		return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
					player ->  new ServerMessage()
				));
	}

	public boolean isStateDone() {
		return mainActionDone;
	}

	public GameState getNextState() {
		return new GameTurnPostActionState(gameManager);
	}

	@Override
	public Map<Player, ServerMessage> handleRequestLeaderAction(LeaderActionClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {
		// check if the player has already done the main action
		if(mainActionDone)
			return createInvalidRequestServerMessage(
				request.player,
				"The player has already done his main action"
			);

		//check if the leader card the player wants to discard is from the group of card he holds in his hand
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToDiscard){
			if (!gameManager.getGameContext().getPlayerContext(activePlayer).getLeaderCards().contains(leaderCard))
				return createInvalidRequestServerMessage(
					request.player,
					"The leader card cannot be discarded: the player does not own this card"
				);
			if (!leaderCard.getState().equals(LeaderCardState.HIDDEN))
			return createInvalidRequestServerMessage(
				request.player,
				"The leader card cannot be discarded: " +
					"the player no longer has the card in his hand (the state is not HIDDEN)"
			);
		}

		//check if the leader card the player wants to activate is from the group of card he holds in his hand
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToActivate){
			if (!gameManager.getGameContext().getPlayerContext(activePlayer).getLeaderCards().contains(leaderCard))
				return createInvalidRequestServerMessage(
					request.player,
					"The leader card cannot be activate: the player does not own this card"
				);
			if (!leaderCard.getState().equals(LeaderCardState.HIDDEN))
				return createInvalidRequestServerMessage(
					request.player,
					"The leader card cannot be activate: " +
						"the player no longer has the card in his hand (the state is not HIDDEN)"
				);
		}

		//check if the player meets the requirements for activating the leader card
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToActivate){
			if(!leaderCard.areRequirementsSatisfied(gameManager.getGameContext().getPlayerContext(activePlayer)))
				return createInvalidRequestServerMessage(
					request.player,
					"The leader card cannot be activate: " +
						"the player does not meet the requirements to activate the card"
				);
		}
		// discard leader cards
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToDiscard)
			leaderCard.discardLeaderCard();

		// activate leader cards
		for (LeaderCard leaderCard : request.leaderCardsThePlayerWantsToActivate)
			leaderCard.activateLeaderCard(gameManager.getGameContext().getPlayerContext(activePlayer));

		Set<GameUpdate> gameUpdates = gameManager.getAllGameUpdates();
		Map<Player, ServerMessage> serverMessages = new HashMap<>();
		for (Player player : gameManager.getPlayers())
			serverMessages.put(player, new GameUpdateServerMessage(gameUpdates));
		mainActionDone = true;
		return serverMessages;
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
