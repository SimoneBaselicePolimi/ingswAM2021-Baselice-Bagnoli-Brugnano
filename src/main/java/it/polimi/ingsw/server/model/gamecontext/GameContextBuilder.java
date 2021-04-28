package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.configfile.MarketConfig;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.market.WrongNumberOfMarblesException;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;

import java.util.*;

public class GameContextBuilder {

	protected final ProjectLogger logger = ProjectLogger.getLogger();

	protected final Set<Player> players;

	protected final GameRules gameRules;

	protected final GameItemsManager gameItemsManager;

	public GameContextBuilder(
		Set<Player> players,
		GameRules gameRules,
		GameItemsManager gameItemsManager
	) {
		this.players = players;
		this.gameRules = gameRules;
		this.gameItemsManager = gameItemsManager;
	}

	public GameContext build() throws GameContextCreationError {

		logger.log(LogLevel.INFO, "GameContext creation started");

		// TODO
		Market market = buildMarket();
		DevelopmentCardsTable developmentCardsTable = buildDevelopmentCardsTable();
		FaithPath faithPath = buildFaithPath();

		return initializeGameContext(market, developmentCardsTable, faithPath, new ArrayList<>(), new HashMap<>());
	}

	protected FaithPath buildFaithPath() {
		return null;
	}

	protected DevelopmentCardsTable buildDevelopmentCardsTable() {
		return null;
	}

	protected MarbleColour initializeMarbleColour(
		String marbleID,
		GameItemsManager gameItemsManager,
		Optional<ResourceType> resourceType,
		int faithPoints,
		boolean isSpecialMarble
	) {
		return new MarbleColour(marbleID, gameItemsManager, resourceType, faithPoints, isSpecialMarble);
	}

	protected Market initializeMarket(int nRows, int nColumns, Map<MarbleColour,Integer> marbles)
		throws WrongNumberOfMarblesException {
		return new Market(nRows, nColumns, marbles);
	}

	protected Market buildMarket() throws GameContextCreationError {
		MarketConfig marketConfig = gameRules.marketConfig;

		Map<MarbleColour,Integer> marbles = new HashMap<>();
		for(MarketConfig.MarbleConfigAndNumber marbleConf : marketConfig.marbles) {
			MarbleColour marbleColour = initializeMarbleColour(
				marbleConf.marbleConfig.marbleID,
				gameItemsManager,
				marbleConf.marbleConfig.resourceType != null ?
					Optional.of(marbleConf.marbleConfig.resourceType) :
					Optional.empty(),
				marbleConf.marbleConfig.numberOfFaithPoints,
				marbleConf.marbleConfig.isSpecial
			);
			marbles.put(marbleColour, marbleConf.numberOfMarbles);
		}

		try {
			return initializeMarket(
				marketConfig.marketMatrix.numberOfRows,
				marketConfig.marketMatrix.numberOfColumns,
				marbles
			);
		} catch (WrongNumberOfMarblesException e) {
			GameContextCreationError newException = new GameContextCreationError(e);
			logger.log(newException);
			throw newException;
		}
	}

	protected GameContext initializeGameContext(
		Market market,
		DevelopmentCardsTable developmentCardsTable,
		FaithPath faithPath,
		List<Player> playersOrder,
		Map<Player, PlayerContext> playerContexts
	) {
		return new GameContext(market, developmentCardsTable, faithPath, playersOrder, playerContexts);
	}

}
