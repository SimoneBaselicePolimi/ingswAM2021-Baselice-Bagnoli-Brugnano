package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.network.servermessage.*;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.InitialChoicesClientRequest;
import it.polimi.ingsw.server.model.gamehistory.SetupChoiceAction;
import it.polimi.ingsw.server.model.gamehistory.SetupStartedAction;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.LeaderCardsThePlayerOwnsUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class represents the initial phase of the game.
 * Everything that is needed to start the game is initialised in the setup state.
 * Each player is assigned a variable number of leader cards, resources and faith points.
 * Each player then chooses which leader cards to hold and discard
 * and which bonus resources to obtain based on the number assigned to him.
 */
public class GameSetupState extends GameState<InitialChoicesServerMessage, PostGameSetupServerMessage> {

	/**
	 * A random number generator used to randomly give leader cards to each player at the start of the game.
	 */
	Random randGenerator;

	final int numberOfLeadersCardsGivenToThePlayer;

	final int numberOfLeadersCardsThePlayerKeeps;

	final Set<LeaderCard> allLeaderCards;

	/**
	 * map containing the leader cards given to each player at the start of the game.
	 */
	Map <Player, Set<LeaderCard>> leaderCardsGivenToThePlayers = new HashMap<>();

	Map<Player, Integer> numOfStarResourcesGivenToThePlayers = new HashMap<>();

	Map<Player, Integer> numOfFaithPointsGivenToThePlayers = new HashMap<>();

	Map<Player, Boolean> hasPlayerAlreadyAnswered = new HashMap<>();

	/**
	 * GameSetupState constructor
	 * The leader cards will be given randomly to each player.
	 * @param gameManager GameManager, see {@link GameManager}
	 */
	public GameSetupState(GameManager gameManager) {
		super(gameManager);
		GameInfoConfig gameInfo = gameManager.getGameRules().gameInfoConfig;
		numberOfLeadersCardsGivenToThePlayer = gameInfo.gameSetup.numberOfLeadersCardsGivenToThePlayer;
		numberOfLeadersCardsThePlayerKeeps = gameInfo.gameSetup.numberOfLeadersCardsThePlayerKeeps;
		allLeaderCards = gameManager.getGameItemsManager().getAllItemsOfType(LeaderCard.class);
		randGenerator = new Random();
		initializeGameSetupState();
	}

	/**
	 * GameSetupState constructor specifying a type of random number generator.
	 * From the configuration file are taken the number of cards to give to each player,
	 * the number of cards that each player must hold in his hand and all the leader cards
	 * that are then randomly assigned to each player with the initializeGameSetupState() function
	 * @param randGenerator random number generator
	 */
	public GameSetupState (Random randGenerator, GameManager gameManager){
        super(gameManager);
		GameInfoConfig gameInfo = gameManager.getGameRules().gameInfoConfig;
		numberOfLeadersCardsGivenToThePlayer = gameInfo.gameSetup.numberOfLeadersCardsGivenToThePlayer;
		numberOfLeadersCardsThePlayerKeeps = gameInfo.gameSetup.numberOfLeadersCardsThePlayerKeeps;
		allLeaderCards = gameManager.getGameItemsManager().getAllItemsOfType(LeaderCard.class);
        this.randGenerator = randGenerator;
		initializeGameSetupState();
	}

	/**
	 * Initializes GameSetupState.
	 * Leader cards are randomly assigned to each player.
	 * The number of resources to choose from and the number of faith points are also assigned to each player.
	 */
	private void initializeGameSetupState (){

		List<LeaderCard> listOfLeaderCards = new ArrayList<>(allLeaderCards);

		for (Player player : gameManager.getPlayers()){

			Set<LeaderCard> leaderCardsGivenToThePlayer = new HashSet<>();
			for (int i = 0; i <numberOfLeadersCardsGivenToThePlayer; i++) {
				int randNum = randGenerator.nextInt(listOfLeaderCards.size());
				leaderCardsGivenToThePlayer.add(listOfLeaderCards.remove(randNum));
			}
			leaderCardsGivenToThePlayers.put(player, leaderCardsGivenToThePlayer);

			int playerTurnOrder = gameManager.getGameContext().getPlayersTurnOrder().indexOf(player) + 1;

			numOfStarResourcesGivenToThePlayers.put(
				player,
				gameManager.getGameRules().gameInfoConfig.gameSetup.
					initialPlayerResourcesBasedOnPlayOrder.get(playerTurnOrder).starResources
			);

			numOfFaithPointsGivenToThePlayers.put(
				player,
				gameManager.getGameRules().gameInfoConfig.gameSetup.
					initialPlayerResourcesBasedOnPlayOrder.get(playerTurnOrder).faithPoints
			);

		}

		hasPlayerAlreadyAnswered = gameManager.getPlayers().stream().collect(Collectors.toMap(
			Function.identity(),
			player -> false
		));

	}

	/**
	 * Method that sends to each player the number of leader cards and bonus resources he has at the start of the game.
	 * @return a map specifying the initial message to be sent to each player
	 */
	@Override
	public Map<Player, InitialChoicesServerMessage> getInitialServerMessage() {
		gameManager.getGameHistory().addAction(new SetupStartedAction());
		return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
				player ->  new InitialChoicesServerMessage(
					gameManager.getAllGameUpdates(),
					leaderCardsGivenToThePlayers.get(player),
					numOfStarResourcesGivenToThePlayers.get(player)
				)
			));
	}

	/**
	 * Method that verifies that the current status is closed by checking that all players have sent a request message.
	 * @return true if all players have sent the request
	 */
	@Override
	public boolean isStateDone() {
		return hasPlayerAlreadyAnswered.values().stream().allMatch(f -> f);
	}

	/**
	 * The method checks that the player's requests are valid. Specifically, it verifies that:
	 * - The player has not already sent a request.
	 * - The number of resources chosen by the player is the same as the one assigned to him.
	 * - The player only wants to add resources in valid storages (only on the shelves).
	 * - The resources chosen by the player do not violate the rules of the storage they are to be added to.
	 * - The number of leader cards chosen by the player is what is expected
	 * (players can only hold a certain number of cards).
	 * - The leader cards chosen by the player are part of the group of leader cards assigned to him.
	 * After verifying these requirements, the method assigns the chosen leader cards to each player,
	 * stores the resources resources in the shelves chosen by the player
	 * and moves the player in the Faith Track for a specific number of steps forward.
	 * Finally, the method returns these changes to each player, taking care to filter out private informations:
	 * each player can only see his own leader cards, which he must keep secret from the other players.
	 * @param request specifying each player's choices, see {@link InitialChoicesClientRequest}
	 * @return a map specifying the message to be sent to each player
	 * @throws ResourceStorageRuleViolationException if at least one of the above requirements is not met:
	 * if a player's choice is not valid, the method throws an exception.
	 */
	public Map<Player, ServerMessage> handleInitialChoiceCR(InitialChoicesClientRequest request) throws ResourceStorageRuleViolationException {

		// check if the player has already sent the request
		if(hasPlayerAlreadyAnswered.get(request.player))
			return createInvalidRequestServerMessage(
				request.player,
				"A request for this player has already been sent"
			);

		// check if the player choose a leader card that was not from the group of leader cards assigned to him.
		if (!leaderCardsGivenToThePlayers.get(request.player).containsAll(request.leaderCardsChosenByThePlayer))
			return createInvalidRequestServerMessage(
				request.player,
				"Invalid request: the player must chose from the group of leader cards assigned to him"
			);

		// give to the player the leader cards he wants to keep
		gameManager.getGameContext().getPlayerContext(request.player)
			.setLeaderCards(request.leaderCardsChosenByThePlayer);

		// store resources in the shelves chosen by the player
		for(ResourceStorage storage : request.chosenResourcesToAddByStorage.keySet())
			storage.addResources(request.chosenResourcesToAddByStorage.get(storage));

		// the player moves in the Faith Track for a specific number of steps forward
		// (initial faith points assigned to him)
		gameManager.getGameContext().getFaithPath()
			.move(request.player, numOfFaithPointsGivenToThePlayers.get(request.player));

		gameManager.getGameHistory().addAction(new SetupChoiceAction(
			request.player,
			ResourceUtils.sum(request.chosenResourcesToAddByStorage.values())
		));

		Set<GameUpdate> gameUpdates = gameManager.getAllGameUpdates();
		Map<Player, ServerMessage> serverMessages = new HashMap<>();
		for (Player player : gameManager.getPlayers()){
			// Filter out private info on leader cards HIDDEN
			Set <GameUpdate> gameUpdatesForPlayer = gameUpdates.stream()
				.filter(gameUpdate -> !(
					gameUpdate instanceof LeaderCardsThePlayerOwnsUpdate &&
					!((LeaderCardsThePlayerOwnsUpdate)gameUpdate).player.equals(player)
				)).collect(Collectors.toSet());
			serverMessages.put(player, new GameUpdateServerMessage(gameUpdatesForPlayer));
		}
		return serverMessages;
	}

	/**
	 * Method that sends to each player the final message of the setup state
	 * @return a map specifying the final message to be sent to each player
	 */
	//empty PostGameSetupServerMessage
	public Map<Player, PostGameSetupServerMessage> getFinalServerMessage() {
		return gameManager.getPlayers().stream()
				.collect(
					Collectors.toMap(Function.identity(),
					player ->  new PostGameSetupServerMessage()
				));
	}

	/**
	 * Method that changes the state of the game:
	 * the setup state ends and the game switches to the main state where the player can choose what action to perform.
	 * @return GameTurnMainActionState main state of the game, see {@link GameTurnMainActionState}
	 */
	public GameTurnMainActionState getNextState() {
		return new GameTurnMainActionState(gameManager);
	}

}
