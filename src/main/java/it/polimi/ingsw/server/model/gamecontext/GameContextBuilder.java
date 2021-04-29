package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.configfile.*;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.faith.VaticanReportSection;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.market.WrongNumberOfMarblesException;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;

import java.util.*;
import java.util.function.BiFunction;

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

	public GameContext buildGameContext() throws GameContextCreationError {

		logger.log(LogLevel.INFO, "GameContext creation started");

		Market market = buildMarket();
		DevelopmentCardsTable developmentCardsTable = buildDevelopmentCardsTable();
		FaithPath faithPath = buildFaithPath();

		// TODO: last two parameters
		return initializeGameContext(market, developmentCardsTable, faithPath, new ArrayList<>(), new HashMap<>());
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

	protected Production initializeProduction(
		String productionID,
		Map<ResourceType, Integer> resourceCost,
		Map<ResourceType, Integer> resourceReward,
		int starResourceCost,
		int starResourceReward,
		int faithReward
	) {
		return new Production(productionID, gameItemsManager, resourceCost, resourceReward, starResourceCost, starResourceReward, faithReward);
	}

	protected Production buildProduction(ProductionConfig productionConfig) {
		// TODO add ID to Productions
		return initializeProduction(
			"ID",
			productionConfig.costs.resources,
			productionConfig.rewards.resources,
			productionConfig.costs.starResources,
			productionConfig.rewards.starResources,
			productionConfig.rewards.faithPoints
				);
	}

	protected DevelopmentCard initializeDevelopmentCard(
		String cardID,
		DevelopmentCardLevel level,
		DevelopmentCardColour colour,
		List<Production> productions,
		int victoryPoints,
		Map<ResourceType, Integer> purchaseCost
	) {
		return new DevelopmentCard(cardID, gameItemsManager, level, colour, productions, victoryPoints, purchaseCost);
	}

	protected DevelopmentCard buildDevelopmentCard(DevelopmentCardsConfig.DevelopmentCardConfig developmentCardConfig, List<Production> productionList) {
		return initializeDevelopmentCard(
			developmentCardConfig.developmentCardID,
			developmentCardConfig.level,
			developmentCardConfig.colour,
			productionList,
			developmentCardConfig.victoryPoints,
			developmentCardConfig.purchaseCost
		);
	}

	protected DevelopmentCardsTable initializeDevelopmentCardsTable(
		List<DevelopmentCard> cards,
		BiFunction<DevelopmentCardColour, DevelopmentCardLevel, String> getIdForDeckWithColourAndLevel
	) {
		return new DevelopmentCardsTable(cards, gameItemsManager, getIdForDeckWithColourAndLevel);
	}

	protected DevelopmentCardsTable buildDevelopmentCardsTable() {
		DevelopmentCardsConfig developmentCardsConfig = gameRules.developmentCardsConfig;

		List<DevelopmentCard> developmentCardList = new ArrayList<>();
		for(DevelopmentCardsConfig.DevelopmentCardConfig developmentCardConfig : developmentCardsConfig.developmentCards) {
			List<Production> productionList = new ArrayList<>();
			for (ProductionConfig productionConfig : developmentCardConfig.productions) {
				Production production = buildProduction(productionConfig);
				productionList.add(production);
			}
			DevelopmentCard developmentCard = buildDevelopmentCard(developmentCardConfig, productionList);
			developmentCardList.add(developmentCard);
		}

		// TODO add ID to DevelopmentCardDecks (getIdForDeckWithColourAndLevel)
		return initializeDevelopmentCardsTable(
			developmentCardList,
			null
		);
	}

	protected VaticanReportSection initializeVaticanReportSection(
		int sectionInitialPos,
		int popeSpacePos,
		int sectionVictoryPoints
	) {
		return new VaticanReportSection(sectionInitialPos, popeSpacePos, sectionVictoryPoints);
	}

	protected FaithPath initializeFaithPath(
		int faithPathLength,
		List<VaticanReportSection> vaticanSections,
		int[] victoryPointsByPosition
	) {
		return new FaithPath(faithPathLength, vaticanSections, victoryPointsByPosition, players);
	}

	protected FaithPath buildFaithPath() throws GameContextCreationError {
		FaithPathConfig faithPathConfig = gameRules.faithPathConfig;

		List<VaticanReportSection> vaticanReportSectionList = new ArrayList<>();
		for(FaithPathConfig.VaticanReportSectionConfig vaticanSectionConf : faithPathConfig.vaticanReportSections) {
			VaticanReportSection vaticanReportSection = initializeVaticanReportSection(
				vaticanSectionConf.initialPosition,
				vaticanSectionConf.popeSpacePosition,
				vaticanSectionConf.victoryPoints
			);
			vaticanReportSectionList.add(vaticanReportSection);
		}

		int[] victoryPointsByPosition = new int[faithPathConfig.faithPathLength];
		int cell;
		for(FaithPathConfig.VictoryPointsByPositionConfig victoryPointsConf : faithPathConfig.victoryPointsByPositions)
			for (cell = victoryPointsConf.startPosition; cell > victoryPointsConf.endPosition; cell++)
				victoryPointsByPosition[cell] = victoryPointsConf.victoryPoints;

		try {
			return initializeFaithPath(
				faithPathConfig.faithPathLength,
				vaticanReportSectionList,
				victoryPointsByPosition
			);
		} catch (IllegalArgumentException e) {
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
