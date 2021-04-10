package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.network.clientrequest.InitialChoiceClientRequest;
import it.polimi.ingsw.network.servermessage.GameSetupServerMessage;
import it.polimi.ingsw.network.servermessage.PostGameSetupServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;

import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class GameSetupState extends GameState<GameSetupServerMessage, PostGameSetupServerMessage> {

	/**
	 * A random number generator used to randomly give leader cards to each player at the start of the game.
	 */
	Random randGenerator;

	/**
	 * map containing the leader cards given to each player at the start of the game.
	 */
	Map <Player, Set<LeaderCard>> leaderCardsGivenToThePlayers = new HashMap<>();

	/**
	 * GameSetupState constructor
	 * The leader cards will be given randomly to each player.
	 * @param gameContext reference to the game context (it contains all the information relative to the current game state)
	 * @param leaderCards all leader cards initialized
	 * @param numOfLeaderCard number of leader cards to give to each player
	 */
	public GameSetupState(GameContext gameContext, Set<LeaderCard> leaderCards, int numOfLeaderCard) {
		randGenerator = new Random();
		initializeGameSetupState(gameContext, leaderCards, numOfLeaderCard);
	}

	/**
	 * GameSetupState constructor specifying a type of random number generator.
	 * @param randGenerator random number generator
	 * @param gameContext reference to the game context (it contains all the information relative to the current game state)
	 * @param leaderCards all leader cards initialized
	 * @param numOfLeaderCard number of leader cards to give to each player
	 */
	public GameSetupState (Random randGenerator, GameContext gameContext, Set<LeaderCard> leaderCards, int numOfLeaderCard){
		this.randGenerator = randGenerator;
		initializeGameSetupState(gameContext, leaderCards, numOfLeaderCard);
	}

	/**
	 * Initializes GameSetupState.
	 * Leader cards are randomly assigned to each player.
	 * @param gameContext reference to the game context (it contains all the information relative to the current game state)
	 * @param leaderCards all leader cards initialized
	 * @param numOfLeaderCard number of leader cards to give to each player
	 */
	private void initializeGameSetupState (GameContext gameContext, Set<LeaderCard> leaderCards, int numOfLeaderCard){
		List<LeaderCard> listOfLeaderCards = new ArrayList<>(leaderCards);
		for (Player player : gameContext.getPlayersTurnOrder()){
			Set<LeaderCard> leaderCardsGivenToThePlayer = new HashSet<>();
			for (int i = 0; i <numOfLeaderCard; i++) {
				int randNum = randGenerator.nextInt(listOfLeaderCards.size());
				leaderCardsGivenToThePlayer.add(listOfLeaderCards.remove(randNum));
			}
			leaderCardsGivenToThePlayers.put(player, leaderCardsGivenToThePlayer);
		}
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
