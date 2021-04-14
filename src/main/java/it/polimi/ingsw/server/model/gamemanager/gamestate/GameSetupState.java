package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.InitialChoicesClientRequest;
import it.polimi.ingsw.network.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.network.servermessage.PostGameSetupServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;


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

	@Override
	public Map<Player, InitialChoicesServerMessage> getInitialServerMessage() {
		return gameManager.getPlayers().stream()
			.collect(
				Collectors.toMap(Function.identity(),
				player ->  new InitialChoicesServerMessage(
					leaderCardsGivenToThePlayers.get(player),
					numOfStarResourcesGivenToThePlayers.get(player)
				)
			));

//	    Set<GameUpdate> gameUpdates = gameManager.getAllGameUpdates();
//		return gameManager.getPlayers().stream()
//			.collect(
//				Collectors.toMap(Function.identity(),
//				player ->  new GameSetupServerMessage(
//					gameUpdates,
//					leaderCardsGivenToThePlayers.get(player)
//				)
//			));
	}

	public boolean isStateDone() {
		return false;
	}

	public Map<Player, ServerMessage> handleInitialChoiceCR(InitialChoicesClientRequest request) throws ResourceStorageRuleViolationException {

		// check if the player has already sent the request
		if(hasPlayerAlreadyAnswered.get(request.player))
			return createInvalidRequestServerMessage(
				request.player,
				"A request for this player has already been sent"
			);

		// check if the player is trying to add a number of resources different from the number of star resources
		// assigned to him
		int numOfTotalResourcesInRequest = ResourceUtils.sumResources(request.chosenResourcesToAdd.values()).values()
			.stream().mapToInt(e -> e).sum();
		if (numOfTotalResourcesInRequest != numOfStarResourcesGivenToThePlayers.get(request.player))
			return createInvalidRequestServerMessage(
				request.player,
				"The number of resources sent is invalid. The number of resources assigned to this " +
					"player is: %s",
				numOfStarResourcesGivenToThePlayers.get(request.player)
			);

		Set<ResourceStorage> validResourceStorages =
			gameManager.getGameContext().getPlayerContext(request.player).getShelves();
		for(ResourceStorage storage : request.chosenResourcesToAdd.keySet()) {

			// check if it is possible to add initial resources to the storages specified by the player (only shelves
			// are valid)
			if (!validResourceStorages.contains(storage))
				return createInvalidRequestServerMessage(
					request.player,
					"Invalid request: the player cannot add initial resources to the storage with ID: %s. " +
						"Valid storages for initial resources are: %s",
					storage.getStorageID(),
					validResourceStorages.stream()
						.map(ResourceStorage::getStorageID)
						.collect(Collectors.toList())
				);

			// check if adding the specified resources to this storage would violate a storage rule.
			if(!storage.canAddResources(request.chosenResourcesToAdd.get(storage)))
				return createInvalidRequestServerMessage(
					request.player,
					"Invalid request: it is not possible to add the specified resources to the storage with " +
						"ID: %s. Resource storage rules violation.",
					storage.getStorageID()
				);

		}

		// check if the number of card chosen by the player is what is expected.
		if (request.leaderCardsChosenByThePlayer.size() != numberOfLeadersCardsThePlayerKeeps)
			return createInvalidRequestServerMessage(
				request.player,
				"Invalid request: the number of leader cards chosen by the player is different from the " +
					"number specified in the rules for this game. Number of cards to chose: %s",
				numberOfLeadersCardsThePlayerKeeps
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


		//TODO
		// store resources in the shelves chosen by the player
		List <ResourceStorage> shelves = new ArrayList<>(validResourceStorages);
		for(ResourceStorage storage : request.chosenResourcesToAdd.keySet()) {
			for (int i = 0; i < shelves.size(); i++) {
				if (storage.equals(shelves.get(i)))
					storage.addResources(request.chosenResourcesToAdd.get(storage));
			}
		}

		// the player moves in the Faith Track for a specific number of steps forward
		// (initial faith points assigned to him)
		gameManager.getGameContext().getFaithPath()
			.move(request.player, numOfFaithPointsGivenToThePlayers.get(request.player));
	}


	private static Map<Player, ServerMessage> createInvalidRequestServerMessage(
		Player requestSender,
		String errorMessage,
		Object... messageArgs
	) {
		InvalidRequestServerMessage serverMessage = new InvalidRequestServerMessage(
			String.format(errorMessage, messageArgs)
		);
		return Map.of(requestSender, serverMessage);
	}

	public Map<Player, PostGameSetupServerMessage> getFinalServerMessage() {
		return null;
	}

	public GameTurnMainActionState getNextState() {
		return null;
	}

}
