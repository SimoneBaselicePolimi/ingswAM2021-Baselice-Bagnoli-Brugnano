package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.configfile.*;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.*;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.market.MarketImp;
import it.polimi.ingsw.server.model.gamecontext.market.WrongNumberOfMarblesException;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContextImp;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeckImp;
import it.polimi.ingsw.server.model.gameitems.developmentcard.*;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardImp;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.storage.DifferentResourceTypesInDifferentStoragesRule;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageBuilder;
import it.polimi.ingsw.server.model.storage.ResourceStorageRule;
import it.polimi.ingsw.utils.Colour;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class GameContextBuilder {

	protected final ProjectLogger logger = ProjectLogger.getLogger();

	protected final Set<Player> players;

	protected final GameRules gameRules;

	protected final GameItemsManager gameItemsManager;

	protected final GameHistory gameHistory;

	protected int numProductionID = 0;
	protected int numSpecialMarbleSubstitutionID = 0;
	protected int numDevelopmentCardCostDiscountID = 0;
	protected int numResourceStorageID = 0;

	private Random randGenerator;

	public GameContextBuilder(
		Set<Player> players,
		GameRules gameRules,
		GameItemsManager gameItemsManager,
		GameHistory gameHistory
	) {
		this.players = players;
		this.gameRules = gameRules;
		this.gameItemsManager = gameItemsManager;
		this.gameHistory = gameHistory;
		this.randGenerator = new Random();
	}

	public GameContextBuilder(
		Set<Player> players,
		GameRules gameRules,
		GameItemsManager gameItemsManager,
		GameHistory gameHistory,
		Random randGenerator
	) {
		this.players = players;
		this.gameRules = gameRules;
		this.gameItemsManager = gameItemsManager;
		this.gameHistory = gameHistory;
		this.randGenerator = randGenerator;
	}

	public GameContext initializeGameContext(
		Market market,
		DevelopmentCardsTable developmentCardsTable,
		FaithPath faithPath,
		List<Player> playersOrder,
		Map<Player, PlayerContext> playerContexts
	) {
		return new GameContextImp(market, developmentCardsTable, faithPath, playersOrder, playerContexts);
	}

	public SinglePlayerGameContext initializeSinglePlayerGameContext(
		Market market,
		DevelopmentCardsTable developmentCardsTable,
		FaithPathSinglePlayer faithPath,
		List<Player> playersOrder,
		Map<Player, PlayerContext> playerContexts
	) {
		return new SinglePlayerGameContextImp(market, developmentCardsTable, faithPath, playersOrder, playerContexts);
	}

	public GameContext buildGameContext() throws GameContextCreationError {
		logger.log(LogLevel.INFO, "GameContext creation started");

		Market market = buildMarket();
		gameRules.leaderCardsConfig.leaderCards.forEach(this::buildLeaderCard);
		DevelopmentCardsTable developmentCardsTable = buildDevelopmentCardsTable();
		List<Player> playersInOrder = generateRandomPlayersOrder();
		FaithPath faithPath = buildFaithPath();
		Map<Player, PlayerContext> playerContexts = new HashMap<>();
		for(Player player : players)
			playerContexts.put(player, buildPlayerContext(player));

		return initializeGameContext(market, developmentCardsTable, faithPath, playersInOrder, playerContexts);
	}

	public SinglePlayerGameContext buildSinglePlayerGameContext() throws GameContextCreationError {

		logger.log(LogLevel.INFO, "GameContext creation started (single player mode)");

		Market market = buildMarket();
		FaithPathSinglePlayer faithPathSinglePlayer = buildFaithPathSinglePlayer();
		gameRules.leaderCardsConfig.leaderCards.forEach(this::buildLeaderCard);
		DevelopmentCardsTable developmentCardsTable = buildDevelopmentCardsTable();
		List<Player> playersInOrder = generateRandomPlayersOrder();
		Map<Player, PlayerContext> playerContexts = new HashMap<>();
		for(Player player : players)
			playerContexts.put(player, buildPlayerContext(player));

		return initializeSinglePlayerGameContext(market, developmentCardsTable, faithPathSinglePlayer, playersInOrder, playerContexts);
	}

	public PlayerContext initializePlayerContext(
		Player player,
		Set<ResourceStorage> shelves,
		List<PlayerOwnedDevelopmentCardDeck> decks,
		ResourceStorage infiniteChest,
		ResourceStorage temporaryStorage,
		Set<Production> baseProductions
	) {
		return new PlayerContextImp(player, shelves, decks, infiniteChest, temporaryStorage, baseProductions);
	}

	protected PlayerContext buildPlayerContext(Player player) {
		GameInfoConfig gameInfoConfig = gameRules.gameInfoConfig;

		Set<ResourceStorage> shelves = new HashSet<>();
		DifferentResourceTypesInDifferentStoragesRule differentResourcesInShelvesRule =
			new DifferentResourceTypesInDifferentStoragesRule();
		for(ResourceStorageConfig resourceStorageConf : gameInfoConfig.resourceShelves) {
			ResourceStorage resourceStorage;
			if(gameInfoConfig.differentResourcesInDifferentStorages)
				resourceStorage = buildResourceStorage(resourceStorageConf, differentResourcesInShelvesRule);
			else
				resourceStorage = buildResourceStorage(resourceStorageConf);
			shelves.add(resourceStorage);
		}

		List<PlayerOwnedDevelopmentCardDeck> decks = new ArrayList<>();
		for(int i=0; i<gameInfoConfig.numberOfPlayerOwnedDevelopmentCardDecks; i++) {
			String idDeck = generatePlayerOwnedDevCardDeckID(player, i+1);
			decks.add(initializePlayerOwnedDevelopmentCardDeck(idDeck));
		}

		ResourceStorage infiniteChest = initializeResourceStorageBuilder()
			.createResourceStorage(generateResourceStorageID());

		ResourceStorage temporaryStorage = initializeResourceStorageBuilder()
			.createResourceStorage(generateResourceStorageID());

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

	public PlayerOwnedDevelopmentCardDeck initializePlayerOwnedDevelopmentCardDeck(String idDeck) {
		return new PlayerOwnedDevelopmentCardDeckImp(idDeck, gameItemsManager);
	}

	protected String generatePlayerOwnedDevCardDeckID(Player player, int num) {
		return "PlayerDevCardDeck_ID_" + num + "_" + player.playerName;
	}

	protected String generateResourceStorageID() {
		numResourceStorageID++;
		return "ResourceStorage_ID_" + numResourceStorageID;
	}


	protected ResourceStorage buildResourceStorage(
		ResourceStorageConfig resourceStorageConf,
		ResourceStorageRule ...additionalRules
	) {
		String resourceStorageID = generateResourceStorageID();

		List<ResourceStorageRule> rules = resourceStorageConf.storage.rules.stream()
			.map(ResourceStorageConfig.StorageConfig.RuleConfig::createRule)
			.collect(Collectors.toList());

		rules.addAll(Arrays.stream(additionalRules).collect(Collectors.toList()));

		ResourceStorageBuilder builder = initializeResourceStorageBuilder();
		rules.forEach(builder::addRule);
		return builder.createResourceStorage(resourceStorageID);
	}

	public MarbleColour initializeMarbleColour(
		String marbleID,
		Optional<ResourceType> resourceType,
		int faithPoints,
		boolean isSpecialMarble,
		List<Colour> marbleColour
	) {
		return new MarbleColour(marbleID, gameItemsManager, resourceType, faithPoints, isSpecialMarble, marbleColour);
	}

	public Market initializeMarket(int nRows, int nColumns, Map<MarbleColour,Integer> marbles)
		throws WrongNumberOfMarblesException {
		return new MarketImp(nRows, nColumns, marbles);
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
				marbleConf.marbleConfig.isSpecial,
				marbleConf.marbleConfig.marbleColour
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

	public Production initializeProduction(
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

	protected String generateNewProductionID() {
		numProductionID++;
		return "Prod_ID_" + numProductionID;
	}

	public DevelopmentCard initializeDevelopmentCard(
		String cardID,
		DevelopmentCardLevel level,
		DevelopmentCardColour colour,
		Production production,
		int victoryPoints,
		Map<ResourceType, Integer> purchaseCost
	) {
		return new DevelopmentCard(cardID, gameItemsManager, level, colour, production, victoryPoints, purchaseCost);
	}

	protected DevelopmentCard buildDevelopmentCard(DevelopmentCardsConfig.DevelopmentCardConfig developmentCardConfig, Production production) {
		return initializeDevelopmentCard(
			developmentCardConfig.developmentCardID,
			developmentCardConfig.level,
			developmentCardConfig.colour,
			production,
			developmentCardConfig.victoryPoints,
			developmentCardConfig.purchaseCost
		);
	}

	public DevelopmentCardsTable initializeDevelopmentCardsTable(
		List<DevelopmentCard> cards,
		BiFunction<DevelopmentCardColour, DevelopmentCardLevel, String> getIdForDeckWithColourAndLevel
	) {
		return new DevelopmentCardsTableImp(cards, gameItemsManager, getIdForDeckWithColourAndLevel);
	}

	protected DevelopmentCardsTable buildDevelopmentCardsTable() {
		DevelopmentCardsConfig developmentCardsConfig = gameRules.developmentCardsConfig;

		List<DevelopmentCard> developmentCardList = new ArrayList<>();
		for(DevelopmentCardsConfig.DevelopmentCardConfig developmentCardConfig : developmentCardsConfig.developmentCards) {
			DevelopmentCard developmentCard = buildDevelopmentCard(developmentCardConfig, buildProduction(developmentCardConfig.production));
			developmentCardList.add(developmentCard);
		}

		return initializeDevelopmentCardsTable(
				developmentCardList,
				(colour, level) -> "DevCardDeckOnTable_ID_" + colour + "_" + level
			);
	}

	public VaticanReportSection initializeVaticanReportSection(
		int sectionInitialPos,
		int popeSpacePos,
		int sectionVictoryPoints
	) {
		return new VaticanReportSection(sectionInitialPos, popeSpacePos, sectionVictoryPoints);
	}

	public FaithPath initializeFaithPath(
		int faithPathLength,
		List<VaticanReportSection> vaticanSections,
		int[] victoryPointsByPosition
	) {
		return new FaithPathImp(faithPathLength, vaticanSections, victoryPointsByPosition, players, gameHistory);
	}

	public FaithPathSinglePlayer initializeFaithPathSinglePlayer(
		int faithPathLength,
		List<VaticanReportSection> vaticanSections,
		int[] victoryPointsByPosition
	) {
		return new FaithPathSinglePlayerImp(faithPathLength, vaticanSections, victoryPointsByPosition, players.iterator().next(), gameHistory);
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
		for(FaithPathConfig.VictoryPointsByPositionConfig victoryPointsConf : faithPathConfig.victoryPointsByPosition)
			for (cell = victoryPointsConf.startPosition; cell <= victoryPointsConf.endPosition; cell++)
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

	protected FaithPathSinglePlayer buildFaithPathSinglePlayer() throws GameContextCreationError {
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
		for(FaithPathConfig.VictoryPointsByPositionConfig victoryPointsConf : faithPathConfig.victoryPointsByPosition)
			for (cell = victoryPointsConf.startPosition; cell <= victoryPointsConf.endPosition; cell++)
				victoryPointsByPosition[cell] = victoryPointsConf.victoryPoints;

		try {
			return initializeFaithPathSinglePlayer(
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

	public List<Player> generateRandomPlayersOrder() {
		List<Player> playersList = new ArrayList<>(players);
		List<Player> playersInOrder = new ArrayList<>();
		int randNum;
		while (playersList.size() > 0) {
			randNum = randGenerator.nextInt(playersList.size());
			playersInOrder.add(playersList.remove(randNum));
		}
		return playersInOrder;
	}

	public LeaderCard initializeLeaderCard(
		String leaderCardID,
		Set<LeaderCardRequirement> requirements,
		Set<Production> productions,
		Set<ResourceStorage> resourceStorages,
		Set<DevelopmentCardCostDiscount> cardCostDiscounts,
		Set<WhiteMarbleSubstitution> specialMarbleSubstitutions,
		int victoryPoints
	) {
		return new LeaderCardImp(leaderCardID, gameItemsManager, requirements, productions, resourceStorages, cardCostDiscounts, specialMarbleSubstitutions, victoryPoints);
	}

	protected LeaderCard buildLeaderCard(LeaderCardsConfig.LeaderCardConfig leaderCardConfig) {
		String leaderCardID = leaderCardConfig.leaderCardID;

		Set<LeaderCardRequirement> requirements = leaderCardConfig.requirements.stream()
				.map(LeaderCardsConfig.LeaderCardConfig.RequirementConfig::createRequirement)
				.collect(Collectors.toSet());

		Set<Production> productions = new HashSet<>();
		for(ProductionConfig productionConfig : leaderCardConfig.productions) {
			Production production = buildProduction(productionConfig);
			productions.add(production);
		}

		Set<ResourceStorage> resourceStorages = new HashSet<>();
		for(ResourceStorageConfig resourceStorageConf : leaderCardConfig.resourceStorages) {
			ResourceStorage resourceStorage = buildResourceStorage(resourceStorageConf);
			resourceStorages.add(resourceStorage);
		}

		Set<DevelopmentCardCostDiscount> cardCostDiscounts = new HashSet<>();
		for(LeaderCardsConfig.LeaderCardConfig.DevelopmentCardCostDiscountConfig cardCostDiscountConfig : leaderCardConfig.developmentCardCostDiscounts) {
			DevelopmentCardCostDiscount cardCostDiscount = buildDevelopmentCardCostDiscount(cardCostDiscountConfig);
			cardCostDiscounts.add(cardCostDiscount);
		}

		Set<WhiteMarbleSubstitution> specialMarbleSubstitutions = new HashSet<>();
		for(LeaderCardsConfig.LeaderCardConfig.WhiteMarbleSubstitutionConfig whiteMarbleSubstitutionConfig : leaderCardConfig.specialMarbleSubstitutions) {
			WhiteMarbleSubstitution specialMarbleSubstitution = buildSpecialMarbleSubstitution(whiteMarbleSubstitutionConfig);
			specialMarbleSubstitutions.add(specialMarbleSubstitution);
		}

		int victoryPoints =leaderCardConfig.victoryPoints;

		return initializeLeaderCard(leaderCardID, requirements, productions, resourceStorages, cardCostDiscounts, specialMarbleSubstitutions, victoryPoints);
	}

	protected String generateNewSpecialMarbleSubstitutionID() {
		numSpecialMarbleSubstitutionID++;
		return "MarbleSubstitution_ID_" + numSpecialMarbleSubstitutionID;
	}

	public WhiteMarbleSubstitution initializeSpecialMarbleSubstitution(
		String marbleSubstitutionID,
		ResourceType resourceTypeToSubstitute
	) {
		return new WhiteMarbleSubstitution(marbleSubstitutionID, gameItemsManager, resourceTypeToSubstitute);
	}

	protected WhiteMarbleSubstitution buildSpecialMarbleSubstitution(LeaderCardsConfig.LeaderCardConfig.WhiteMarbleSubstitutionConfig whiteMarbleSubstitutionConfig) {
		return initializeSpecialMarbleSubstitution(
			generateNewSpecialMarbleSubstitutionID(),
			whiteMarbleSubstitutionConfig.resourceType
		);
	}

	protected String generateNewDevelopmentCardCostDiscountID() {
		numDevelopmentCardCostDiscountID++;
		return "DevCardCostDiscount_ID_" + numDevelopmentCardCostDiscountID;
	}

	public DevelopmentCardCostDiscount initializeDevelopmentCardCostDiscount(
		String costDiscountID,
		ResourceType resourceType,
		int amountToDiscount
	) {
		return new DevelopmentCardCostDiscount(costDiscountID, gameItemsManager, resourceType, amountToDiscount);
	}

	protected DevelopmentCardCostDiscount buildDevelopmentCardCostDiscount(LeaderCardsConfig.LeaderCardConfig.DevelopmentCardCostDiscountConfig cardCostDiscountConfig) {
		return initializeDevelopmentCardCostDiscount(
			generateNewDevelopmentCardCostDiscountID(),
			cardCostDiscountConfig.resourceType,
			cardCostDiscountConfig.amountToDiscount
		);
	}

	public ResourceStorageBuilder initializeResourceStorageBuilder() {
		return new ResourceStorageBuilder(gameItemsManager);
	}

}
