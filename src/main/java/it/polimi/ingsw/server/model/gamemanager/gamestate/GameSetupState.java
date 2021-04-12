package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.network.clientrequest.InitialChoicesClientRequest;
import it.polimi.ingsw.network.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.network.servermessage.PostGameSetupServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

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

	public Map<Player, ServerMessage> handleInitialChoiceCR(InitialChoicesClientRequest request) {

		if(hasPlayerAlreadyAnswered.get(request.player)) {
			InvalidRequestServerMessage errorMessage = new InvalidRequestServerMessage(
				"The player has already sent a request"
			);
			return Map.of(request.player, errorMessage);
		}

		// TODO
	}

	public Map<Player, PostGameSetupServerMessage> getFinalServerMessage() {
		return null;
	}

	public GameTurnMainActionState getNextState() {
		return null;
	}

}
