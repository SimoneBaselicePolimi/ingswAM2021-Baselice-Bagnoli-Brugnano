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
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRule;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class GameContextBuilder {

	protected final ProjectLogger logger = ProjectLogger.getLogger();

	protected final Set<Player> players;

	protected final GameRules gameRules;

	protected final GameItemsManager gameItemsManager;

	protected int numProductionID = 0;

	protected int numResourceStorageID = 0;

	public GameContextBuilder(
		Set<Player> players,
		GameRules gameRules,
		GameItemsManager gameItemsManager
	) {
		this.players = players;
		this.gameRules = gameRules;
		this.gameItemsManager = gameItemsManager;
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

	public GameContext buildGameContext() throws GameContextCreationError {

		logger.log(LogLevel.INFO, "GameContext creation started");

		Market market = buildMarket();
		DevelopmentCardsTable developmentCardsTable = buildDevelopmentCardsTable();
		FaithPath faithPath = buildFaithPath();
		List<Player> playersInOrder = generateRandomPlayersOrder();
		Map<Player, PlayerContext> playerContexts = new HashMap<>();
		for(Player player : players)
			playerContexts.put(player, buildPlayerContext(player));

		return initializeGameContext(market, developmentCardsTable, faithPath, playersInOrder, playerContexts);
	}

	private PlayerContext buildPlayerContext(Player player) {
		GameInfoConfig gameInfoConfig = gameRules.gameInfoConfig;

		Set<ResourceStorage> shelves = new HashSet<>();
		for(ResourceStorageConfig resourceStorageConf : gameInfoConfig.resourceShelves) {
			ResourceStorage resourceStorage = buildResourceStorage(resourceStorageConf);
			shelves.add(resourceStorage);
		}

		List<PlayerOwnedDevelopmentCardDeck> decks = new ArrayList<>();
		for(int i=0; i<gameInfoConfig.numberOfPlayerOwnedDevelopmentCardDecks; i++) {
			decks.add(
				new PlayerOwnedDevelopmentCardDeck(
						generatePlayerOwnedDevCardDeckID(i+1),
						gameItemsManager
				)
			);
		}

		ResourceStorage infiniteChest = new ResourceStorage(
			"InfChest_ID", gameItemsManager, new ArrayList<>()
		);

		ResourceStorage temporaryStorage = new ResourceStorage(
			"TempStorage_ID", gameItemsManager, new ArrayList<>()
		);

		Set<Production> baseProductions = new HashSet<>();
		for(ProductionConfig basicProductionConfig : gameInfoConfig.basicProductionPower) {
			Production production = buildProduction(basicProductionConfig);
			baseProductions.add(production);
		}

		return initializePlayerContext(
			player,
			shelves,
			decks,
			infiniteChest,
			temporaryStorage,
			baseProductions
			);
	}

	private String generatePlayerOwnedDevCardDeckID(int num) {
		return "PlayerDevCardDeck_ID_" + num;
	}

	private ResourceStorage buildResourceStorage(ResourceStorageConfig resourceStorageConf) {
		String resourceStorageID = generateResourceStorageID();

		List<ResourceStorageRule> rules = resourceStorageConf.storage.rules.stream()
			.map(ResourceStorageConfig.StorageConfig.RuleConfig::createRule)
			.collect(Collectors.toList());

		return initializeResourceStorage(resourceStorageID, rules);
	}

	private String generateResourceStorageID() {
		numResourceStorageID++;
		return "ResourceStorage_ID_" + numResourceStorageID;
	}

	private ResourceStorage initializeResourceStorage(
		String resourceStorageID,
		List<ResourceStorageRule> rules
	) {
		return new ResourceStorage(resourceStorageID, gameItemsManager, rules);
	}

	private PlayerContext initializePlayerContext(
		Player player,
		Set<ResourceStorage> shelves,
		List<PlayerOwnedDevelopmentCardDeck> decks,
		ResourceStorage infiniteChest,
		ResourceStorage temporaryStorage,
		Set<Production> baseProductions
	) {
		return new PlayerContext(player, shelves, decks, infiniteChest, temporaryStorage, baseProductions);
	}

	protected MarbleColour initializeMarbleColour(
		String marbleID,
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
		String productionID = generateNewProductionID();
		return initializeProduction(
			productionID,
			productionConfig.costs.resources,
			productionConfig.rewards.resources,
			productionConfig.costs.starResources,
			productionConfig.rewards.starResources,
			productionConfig.rewards.faithPoints
		);
	}

	private String generateNewProductionID() {
		numProductionID++;
		return "Prod_ID_" + numProductionID;
	}

	protected DevelopmentCard initializeDevelopmentCard(
		String cardID,
		DevelopmentCardLevel level,
		DevelopmentCardColour colour,
		Set<Production> productions,
		int victoryPoints,
		Map<ResourceType, Integer> purchaseCost
	) {
		return new DevelopmentCard(cardID, gameItemsManager, level, colour, productions, victoryPoints, purchaseCost);
	}

	protected DevelopmentCard buildDevelopmentCard(DevelopmentCardsConfig.DevelopmentCardConfig developmentCardConfig, Set<Production> productionSet) {
		return initializeDevelopmentCard(
			developmentCardConfig.developmentCardID,
			developmentCardConfig.level,
			developmentCardConfig.colour,
			productionSet,
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
			Set<Production> productions = new HashSet<>();
			for (ProductionConfig productionConfig : developmentCardConfig.productions) {
				Production production = buildProduction(productionConfig);
				productions.add(production);
			}
			DevelopmentCard developmentCard = buildDevelopmentCard(developmentCardConfig, productions);
			developmentCardList.add(developmentCard);
		}

		return initializeDevelopmentCardsTable(
				developmentCardList,
				(colour, level) -> "DevCardDeckOnTable_ID_" + colour + "_" + level
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

	protected List<Player> generateRandomPlayersOrder() {
		List<Player> playersList = new ArrayList<>(players);
		List<Player> playersInOrder = new ArrayList<>();
		Random randGenerator = new Random();
		int randNum;
		while (playersList.size() > 0) {
			randNum = randGenerator.nextInt(playersList.size());
			playersInOrder.add(playersList.remove(randNum));
		}
		return playersInOrder;
	}

}
