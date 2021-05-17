package it.polimi.ingsw.integrationtest.gamebuilder;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.configfile.ProductionConfig;
import it.polimi.ingsw.configfile.ResourceStorageConfig;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.GameContextBuilder;
import it.polimi.ingsw.server.model.gamecontext.GameContextCreationError;
import it.polimi.ingsw.server.model.gamecontext.ObservableGameContextBuilder;
import it.polimi.ingsw.server.model.gamecontext.faith.VaticanReportSection;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.market.WrongNumberOfMarblesException;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gameitems.leadercard.*;
import it.polimi.ingsw.server.model.storage.MaxResourceNumberRule;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.SpecificResourceTypeRule;
import it.polimi.ingsw.utils.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GameContextBuilderIntegrationTest {

    static final String CONFIG_RULES_PATH = "testConfigFiles/StandardGameRules";

    GameRules gameRules;

    GameContextBuilder gameContextBuilder;

    GameContext gameContext;

    GameItemsManager gameItemsManager = new GameItemsManager();

    GameHistory gameHistory = new GameHistory();

    Player player1, player2, player3, player4;

    Set<Player> players;

    static final int MARKET_N_ROWS = 3;
    static final int MARKET_N_COLUMNS = 4;

    static final Map<String, Integer> MARBLES_IN_MARKET = Map.of(
        "MARBLE_WHITE", 4,
        "MARBLE_BLUE", 2,
        "MARBLE_GREY", 2,
        "MARBLE_YELLOW", 2,
        "MARBLE_PURPLE", 2,
        "MARBLE_RED", 1
    );

    static final ResourceType MARBLE_BLUE_RESOURCE_TYPE = ResourceType.SHIELDS;
    static final int MARBLE_BLUE_NUM_FAITH_POINTS = 0;
    static final boolean MARBLE_BLUE_IS_SPECIAL = false;

    static final ResourceType MARBLE_RED_RESOURCE_TYPE = null;
    static final int MARBLE_RED_NUM_FAITH_POINTS = 1;
    static final boolean MARBLE_RED_IS_SPECIAL = false;

    static final ResourceType MARBLE_WHITE_RESOURCE_TYPE = null;
    static final int MARBLE_WHITE_NUM_FAITH_POINTS = 0;
    static final boolean MARBLE_WHITE_IS_SPECIAL = true;

    static final int FAITH_PATH_LENGTH = 25;
    static final int NUM_OF_VAT_REP_SECTIONS = 3;

    static final int VAT_REP_SECTION_1_INITIAL_POS = 5;
    static final int VAT_REP_SECTION_1_POPE_SPACE_POS = 8;
    static final int VAT_REP_SECTION_1_VICTORY_POINTS = 2;

    static final int VICTORY_POINTS_BY_POS_1_START_POS = 0;
    static final int VICTORY_POINTS_BY_POS_1_END_POS = 2;
    static final int VICTORY_POINTS_BY_POS_1_NUM_VICTORY_POINTS = 0;

    static final int VICTORY_POINTS_BY_POS_2_START_POS = 3;
    static final int VICTORY_POINTS_BY_POS_2_END_POS = 5;
    static final int VICTORY_POINTS_BY_POS_2_NUM_VICTORY_POINTS = 1;

    static final int NUMBER_OF_DECKS_ON_TABLE = 2;

    static final Set<String> developmentCardsIds = Set.of(
        "DEV_G1",
        "DEV_G2",
        "DEV_G3",
        "DEV_Y1",
        "DEV_Y2"
    );

    static final DevelopmentCardLevel DEV_CARD_G1_LEVEL = DevelopmentCardLevel.FIRST_LEVEL;
    static final DevelopmentCardColour DEV_CARD_G1_COLOUR = DevelopmentCardColour.GREEN;
    static final Map<ResourceType, Integer> DEV_CARD_G1_PROD1_COST = Map.of(ResourceType.COINS, 1);
    static final int DEV_CARD_G1_PROD1_STAR_COST = 0;
    static final Map<ResourceType, Integer> DEV_CARD_G1_PROD1_REWARD = new HashMap<>();
    static final int DEV_CARD_G1_PROD1_STAR_REWARD = 0;
    static final int DEV_CARD_G1_PROD1_FAITH_REWARD = 1;
    static final int DEV_CARD_G1_VICTORY_POINTS = 1;
    static final Map<ResourceType, Integer> DEV_CARD_G1_PURCHASE_COST = Map.of(ResourceType.SHIELDS, 2);

    static final Set<String> leaderCardsIds = Set.of(
        "LEADER_DISC_1",
        "LEADER_STOR_1",
        "LEADER_PROD_1",
        "LEADER_SUB_1"
    );

    static final DevelopmentCardColour LEAD_CARD_DISC_1_REQ1_CARD_COLOUR = DevelopmentCardColour.YELLOW;
    static final int LEAD_CARD_DISC_1_REQ1_NUM_OF_CARDS = 1;
    static final ResourceType LEAD_CARD_DISC_1_DISC1_RESOURCE_TYPE = ResourceType.COINS;
    static final int LEAD_CARD_DISC_1_DISC1_AMOUNT_TO_DISCOUNT = 1;
    static final int LEAD_CARD_DISC_1_VICTORY_POINTS = 2;

    static final ResourceType LEAD_CARD_STOR_1_REQ1_RESOURCE_TYPE = ResourceType.SHIELDS;
    static final int LEAD_CARD_STOR_1_REQ1_RESOURCE_NUMBER = 5;
    static final int LEAD_CARD_STOR_1_STOR1_MAX_NUMBER = 2;
    static final ResourceType LEAD_CARD_STOR_1_STOR1_RESOURCE_TYPE = ResourceType.COINS;
    static final int LEAD_CARD_STOR_1_VICTORY_POINTS = 3;

    static final DevelopmentCardColour LEAD_CARD_PROD_1_REQ1_CARD_COLOUR = DevelopmentCardColour.YELLOW;
    static final DevelopmentCardLevel LEAD_CARD_PROD_1_REQ1_CARD_LEVEL = DevelopmentCardLevel.SECOND_LEVEL;
    static final int LEAD_CARD_PROD_1_REQ1_NUM_OF_CARDS = 1;
    static final Map<ResourceType, Integer> LEAD_CARD_PROD_1_PROD1_COST = Map.of(ResourceType.SHIELDS, 1);
    static final int LEAD_CARD_PROD_1_PROD1_STAR_COST = 0;
    static final Map<ResourceType, Integer> LEAD_CARD_PROD_1_PROD1_REWARD = new HashMap<>();
    static final int LEAD_CARD_PROD_1_PROD1_STAR_REWARD = 1;
    static final int LEAD_CARD_PROD_1_PROD1_FAITH_REWARD = 1;
    static final int LEAD_CARD_PROD_1_VICTORY_POINTS = 4;

    static final DevelopmentCardColour LEAD_CARD_SUB_1_REQ1_CARD_COLOUR = DevelopmentCardColour.YELLOW;
    static final int LEAD_CARD_SUB_1_REQ1_NUM_OF_CARDS = 2;
    static final ResourceType LEAD_CARD_SUB_1_SUB1_RESOURCE_TYPE = ResourceType.SERVANTS;
    static final int LEAD_CARD_SUB_1_VICTORY_POINTS = 5;

    static final int MAX_NUM_OF_PLAYERS = 4;
    static final boolean SINGLE_PLAYER_ENABLED = true;
    static final int NUM_LEAD_CARDS_GIVEN_TO_PLAYER = 4;
    static final int NUM_LEAD_CARDS_PLAYER_KEEPS = 2;
    static final int FIRST_PLAYER_STAR_RESOURCES = 0;
    static final int FIRST_PLAYER_FAITH_POINTS = 0;
    static final int NUM_PLAYER_OWNED_DEV_CARD_DECKS = 3;
    static final Map<ResourceType, Integer> BASIC_PROD_COST = new HashMap<>();
    static final int BASIC_PROD_STAR_COST = 2;
    static final Map<ResourceType, Integer> BASIC_PROD_REWARD = new HashMap<>();
    static final int BASIC_PROD_STAR_REWARD = 1;
    static final int BASIC_PROD_FAITH_REWARD = 0;
    static final int GAME_RESOURCE_STORAGE_MAX_NUM_RESOURCES = 1;
    static final boolean DIFFERENT_RESOURCES_IN_DIFFERENT_STORAGES = true;
    static final int NUM_RESOURCES_REWARDING_ONE_VICTORY_POINTS = 5;

    @BeforeEach
    void buildGameContext() throws IOException, GameContextCreationError {
        player1 = new Player("id1", gameItemsManager);
        player2 = new Player("id2", gameItemsManager);
        player3 = new Player("id3", gameItemsManager);
        player4 = new Player("id4", gameItemsManager);

        players = Set.of(
            player1,
            player2,
            player3,
            player4
        );

        gameRules = FileManager.getFileManagerInstance().getGameRules(CONFIG_RULES_PATH);

        gameContextBuilder = spy(new ObservableGameContextBuilder(players, gameRules, gameItemsManager, gameHistory));
        gameContext = gameContextBuilder.buildGameContext();
    }

    @Test
    void testGameContext() {
        //Checks that getPlayersTurnOrder() returns an ordered list with all the expected players once.
        Set<Player> playersNotMatched = new HashSet<>(players);
        for(Player player : gameContext.getPlayersTurnOrder()) {
            assertTrue(playersNotMatched.contains(player));
            playersNotMatched.remove(player);
        }
        assertTrue(playersNotMatched.isEmpty());

        assertNotNull(gameContext.getMarket());
        assertNotNull(gameContext.getDevelopmentCardsTable());
        assertNotNull(gameContext.getFaithPath());
        assertNotNull(gameContext.getPlayersTurnOrder());

        for(Player player : players)
            assertNotNull(gameContext.getPlayerContext(player));
    }

    @Test
    void testMarket() throws WrongNumberOfMarblesException {
        ArgumentCaptor<Map<MarbleColour,Integer>> marbleCaptor = ArgumentCaptor.forClass(Map.class);
        verify(gameContextBuilder).initializeMarket(
            eq(MARKET_N_ROWS),
            eq(MARKET_N_COLUMNS),
            marbleCaptor.capture()
        );
        Map<MarbleColour,Integer> marbles = marbleCaptor.getValue();
        Map<String, Integer> marbleIDToNumber = marbles.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey().getItemId(), Map.Entry::getValue));
        assertEquals(MARBLES_IN_MARKET, marbleIDToNumber);

        Market market = gameContext.getMarket();

        assertEquals(MARKET_N_ROWS, market.getNumOfRows());
        assertEquals(MARKET_N_COLUMNS, market.getNumOfColumns());
        assertTrue(MARBLES_IN_MARKET.containsKey(market.getOutMarble().getItemId()));
    }

    @Test
    void testMarbleColour() {
        MarbleColour marbleBlue = gameItemsManager.getItem(MarbleColour.class, "MARBLE_BLUE");
        MarbleColour marbleRed = gameItemsManager.getItem(MarbleColour.class, "MARBLE_RED");
        MarbleColour marbleWhite = gameItemsManager.getItem(MarbleColour.class, "MARBLE_WHITE");

        assertEquals(Optional.ofNullable(MARBLE_BLUE_RESOURCE_TYPE), marbleBlue.getResourceType());
        assertEquals(MARBLE_BLUE_NUM_FAITH_POINTS, marbleBlue.getFaithPoints());
        assertEquals(MARBLE_BLUE_IS_SPECIAL, marbleBlue.isSpecialMarble());

        assertEquals(Optional.ofNullable(MARBLE_RED_RESOURCE_TYPE), marbleRed.getResourceType());
        assertEquals(MARBLE_RED_NUM_FAITH_POINTS, marbleRed.getFaithPoints());
        assertEquals(MARBLE_RED_IS_SPECIAL, marbleRed.isSpecialMarble());

        assertEquals(Optional.ofNullable(MARBLE_WHITE_RESOURCE_TYPE), marbleWhite.getResourceType());
        assertEquals(MARBLE_WHITE_NUM_FAITH_POINTS, marbleWhite.getFaithPoints());
        assertEquals(MARBLE_WHITE_IS_SPECIAL, marbleWhite.isSpecialMarble());
    }

    @Test
    void testFaithPath() {
        ArgumentCaptor<List<VaticanReportSection>> vaticanReportSectionsCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<int[]> victoryPointsByPositionCaptor = ArgumentCaptor.forClass(int[].class);

        verify(gameContextBuilder).initializeFaithPath(
            eq(FAITH_PATH_LENGTH),
            vaticanReportSectionsCaptor.capture(),
            victoryPointsByPositionCaptor.capture()
        );
        List<VaticanReportSection> vaticanReportSections = vaticanReportSectionsCaptor.getValue();
        int[] victoryPointsByPosition = victoryPointsByPositionCaptor.getValue();

        assertEquals(NUM_OF_VAT_REP_SECTIONS, vaticanReportSections.size());
        assertEquals(VAT_REP_SECTION_1_INITIAL_POS, vaticanReportSections.stream().iterator().next().getSectionInitialPos());
        assertEquals(VAT_REP_SECTION_1_POPE_SPACE_POS, vaticanReportSections.stream().iterator().next().getPopeSpacePos());
        assertEquals(VAT_REP_SECTION_1_VICTORY_POINTS, vaticanReportSections.stream().iterator().next().getSectionVictoryPoints());

        assertEquals(FAITH_PATH_LENGTH, victoryPointsByPosition.length);
        for(int cell = VICTORY_POINTS_BY_POS_1_START_POS; cell <= VICTORY_POINTS_BY_POS_1_END_POS; cell++)
            assertEquals(VICTORY_POINTS_BY_POS_1_NUM_VICTORY_POINTS, victoryPointsByPosition[cell]);
        for(int cell = VICTORY_POINTS_BY_POS_2_START_POS; cell <= VICTORY_POINTS_BY_POS_2_END_POS; cell++)
            assertEquals(VICTORY_POINTS_BY_POS_2_NUM_VICTORY_POINTS, victoryPointsByPosition[cell]);
    }

    @Test
    void testDevelopmentCard() {
        Set<String> cardsId = gameItemsManager.getAllItemsOfType(DevelopmentCard.class).stream()
            .map(RegisteredIdentifiableItem::getItemId)
            .collect(Collectors.toSet());

        assertEquals(developmentCardsIds, cardsId);

        DevelopmentCard dev_g1 = gameItemsManager.getItem(DevelopmentCard.class, "DEV_G1");

        assertEquals(DEV_CARD_G1_LEVEL, dev_g1.getLevel());
        assertEquals(DEV_CARD_G1_COLOUR, dev_g1.getColour());
        assertEquals(DEV_CARD_G1_PROD1_COST, dev_g1.getProductions().stream().iterator().next().getProductionResourceCost());
        assertEquals(DEV_CARD_G1_PROD1_STAR_COST, dev_g1.getProductions().stream().iterator().next().getProductionStarResourceCost());
        assertEquals(DEV_CARD_G1_PROD1_REWARD, dev_g1.getProductions().stream().iterator().next().getProductionResourceReward());
        assertEquals(DEV_CARD_G1_PROD1_STAR_REWARD, dev_g1.getProductions().stream().iterator().next().getProductionStarResourceReward());
        assertEquals(DEV_CARD_G1_PROD1_FAITH_REWARD, dev_g1.getProductions().stream().iterator().next().getProductionFaithReward());
        assertEquals(DEV_CARD_G1_VICTORY_POINTS, dev_g1.getVictoryPoints());
        assertEquals(DEV_CARD_G1_PURCHASE_COST, dev_g1.getPurchaseCost());
    }

    @Test
    void testDevelopmentCardsTable() {
        DevelopmentCardsTable developmentCardsTable = gameContext.getDevelopmentCardsTable();

        assertTrue(developmentCardsIds.containsAll(
            developmentCardsTable.getAvailableCards().stream()
            .map(RegisteredIdentifiableItem::getItemId)
            .collect(Collectors.toSet())
        ));

        assertEquals(NUMBER_OF_DECKS_ON_TABLE, developmentCardsTable.getAvailableCards().size());
    }

    @Test
    void testLeaderCard() {
        Set<String> cardsId = gameItemsManager.getAllItemsOfType(LeaderCard.class).stream()
            .map(RegisteredIdentifiableItem::getItemId)
            .collect(Collectors.toSet());

        assertEquals(leaderCardsIds, cardsId);

        LeaderCard leader_disc_1 = gameItemsManager.getItem(LeaderCard.class, "LEADER_DISC_1");

        ArgumentCaptor<Set<LeaderCardRequirement>> requirementsCaptor = ArgumentCaptor.forClass(Set.class);
        verify(gameContextBuilder).initializeLeaderCard(
            eq("LEADER_DISC_1"),
            requirementsCaptor.capture(),
            anySet(),
            anySet(),
            anySet(),
            anySet(),
            anyInt()
        );
        Set<LeaderCardRequirement> requirements_leader_disc_1 = requirementsCaptor.getValue();

        assertTrue(requirements_leader_disc_1.stream()
            .anyMatch(
                req -> req instanceof DevelopmentCardColourRequirement
                && ((DevelopmentCardColourRequirement) req).cardColour == LEAD_CARD_DISC_1_REQ1_CARD_COLOUR
                && ((DevelopmentCardColourRequirement) req).numberOfCards == LEAD_CARD_DISC_1_REQ1_NUM_OF_CARDS
            )
        );

        assertEquals(LEAD_CARD_DISC_1_DISC1_RESOURCE_TYPE, leader_disc_1.getDevelopmentCardCostDiscount().stream().iterator().next().getResourceTypeToDiscount());
        assertEquals(LEAD_CARD_DISC_1_DISC1_AMOUNT_TO_DISCOUNT, leader_disc_1.getDevelopmentCardCostDiscount().stream().iterator().next().getAmountToDiscount());
        assertEquals(new HashSet<>(), leader_disc_1.getProductions());
        assertEquals(new HashSet<>(), leader_disc_1.getResourceStorages());
        assertEquals(new HashSet<>(), leader_disc_1.getWhiteMarbleSubstitutions());
        assertEquals(LEAD_CARD_DISC_1_VICTORY_POINTS, leader_disc_1.getVictoryPoints());

        LeaderCard leader_stor_1 = gameItemsManager.getItem(LeaderCard.class, "LEADER_STOR_1");

        ArgumentCaptor<Set<ResourceStorage>> resourceStoragesCaptor = ArgumentCaptor.forClass(Set.class);
        verify(gameContextBuilder).initializeLeaderCard(
            eq("LEADER_STOR_1"),
            requirementsCaptor.capture(),
            anySet(),
            resourceStoragesCaptor.capture(),
            anySet(),
            anySet(),
            anyInt()
        );
        Set<LeaderCardRequirement> requirements_leader_stor_1 = requirementsCaptor.getValue();
        Set<ResourceStorage> resource_storages_leader_stor_1 = resourceStoragesCaptor.getValue();

        assertTrue(requirements_leader_stor_1.stream()
            .anyMatch(
                req -> req instanceof ResourceNumberRequirement
                    && ((ResourceNumberRequirement) req).resourceType == LEAD_CARD_STOR_1_REQ1_RESOURCE_TYPE
                    && ((ResourceNumberRequirement) req).resourceNumber == LEAD_CARD_STOR_1_REQ1_RESOURCE_NUMBER
            )
        );

        assertTrue(resource_storages_leader_stor_1.stream()
            .anyMatch(
                storage -> storage.rules.stream()
                    .anyMatch(
                        rule -> rule instanceof MaxResourceNumberRule
                        && ((MaxResourceNumberRule) rule).maxResources == LEAD_CARD_STOR_1_STOR1_MAX_NUMBER
                )
            )
        );

        assertTrue(resource_storages_leader_stor_1.stream()
            .anyMatch(
                storage -> storage.rules.stream()
                    .anyMatch(
                        rule -> rule instanceof SpecificResourceTypeRule
                            && ((SpecificResourceTypeRule) rule).resourceType == LEAD_CARD_STOR_1_STOR1_RESOURCE_TYPE
                    )
            )
        );

        assertEquals(LEAD_CARD_STOR_1_VICTORY_POINTS, leader_stor_1.getVictoryPoints());

        LeaderCard leader_prod_1 = gameItemsManager.getItem(LeaderCard.class, "LEADER_PROD_1");

        verify(gameContextBuilder).initializeLeaderCard(
            eq("LEADER_PROD_1"),
            requirementsCaptor.capture(),
            anySet(),
            anySet(),
            anySet(),
            anySet(),
            anyInt()
        );
        Set<LeaderCardRequirement> requirements_leader_prod_1 = requirementsCaptor.getValue();

        assertTrue(requirements_leader_prod_1.stream()
            .anyMatch(
                req -> req instanceof DevelopmentCardColourAndLevelRequirement
                    && ((DevelopmentCardColourAndLevelRequirement) req).cardColour == LEAD_CARD_PROD_1_REQ1_CARD_COLOUR
                    && ((DevelopmentCardColourAndLevelRequirement) req).cardLevel == LEAD_CARD_PROD_1_REQ1_CARD_LEVEL
                    && ((DevelopmentCardColourAndLevelRequirement) req).numberOfCards == LEAD_CARD_PROD_1_REQ1_NUM_OF_CARDS
            )
        );

        assertEquals(LEAD_CARD_PROD_1_PROD1_COST, leader_prod_1.getProductions().stream().iterator().next().getProductionResourceCost());
        assertEquals(LEAD_CARD_PROD_1_PROD1_STAR_COST, leader_prod_1.getProductions().stream().iterator().next().getProductionStarResourceCost());
        assertEquals(LEAD_CARD_PROD_1_PROD1_REWARD, leader_prod_1.getProductions().stream().iterator().next().getProductionResourceReward());
        assertEquals(LEAD_CARD_PROD_1_PROD1_STAR_REWARD, leader_prod_1.getProductions().stream().iterator().next().getProductionStarResourceReward());
        assertEquals(LEAD_CARD_PROD_1_PROD1_FAITH_REWARD, leader_prod_1.getProductions().stream().iterator().next().getProductionFaithReward());
        assertEquals(LEAD_CARD_PROD_1_VICTORY_POINTS, leader_prod_1.getVictoryPoints());

        LeaderCard leader_sub_1 = gameItemsManager.getItem(LeaderCard.class, "LEADER_SUB_1");

        verify(gameContextBuilder).initializeLeaderCard(
            eq("LEADER_SUB_1"),
            requirementsCaptor.capture(),
            anySet(),
            anySet(),
            anySet(),
            anySet(),
            anyInt()
        );
        Set<LeaderCardRequirement> requirements_leader_sub_1 = requirementsCaptor.getValue();

        assertTrue(requirements_leader_sub_1.stream()
            .anyMatch(
                req -> req instanceof DevelopmentCardColourRequirement
                    && ((DevelopmentCardColourRequirement) req).cardColour == LEAD_CARD_SUB_1_REQ1_CARD_COLOUR
                    && ((DevelopmentCardColourRequirement) req).numberOfCards == LEAD_CARD_SUB_1_REQ1_NUM_OF_CARDS
            )
        );

        assertEquals(LEAD_CARD_SUB_1_SUB1_RESOURCE_TYPE, leader_sub_1.getWhiteMarbleSubstitutions().stream().iterator().next().getResourceTypeToSubstitute());
        assertEquals(LEAD_CARD_SUB_1_VICTORY_POINTS, leader_sub_1.getVictoryPoints());
    }

    @Test
    void testGameInfo() {
        GameInfoConfig gameInfoConfig = gameRules.gameInfoConfig;

        assertEquals(MAX_NUM_OF_PLAYERS, gameInfoConfig.maxNumberOfPlayers);
        assertEquals(SINGLE_PLAYER_ENABLED, gameInfoConfig.singlePlayerEnabled);
        assertEquals(NUM_LEAD_CARDS_GIVEN_TO_PLAYER, gameInfoConfig.gameSetup.numberOfLeadersCardsGivenToThePlayer);
        assertEquals(NUM_LEAD_CARDS_PLAYER_KEEPS, gameInfoConfig.gameSetup.numberOfLeadersCardsThePlayerKeeps);
        assertEquals(FIRST_PLAYER_STAR_RESOURCES, gameInfoConfig.gameSetup.initialPlayerResourcesBasedOnPlayOrder.get(1).starResources);
        assertEquals(FIRST_PLAYER_FAITH_POINTS, gameInfoConfig.gameSetup.initialPlayerResourcesBasedOnPlayOrder.get(1).faithPoints);
        assertEquals(NUM_PLAYER_OWNED_DEV_CARD_DECKS, gameInfoConfig.numberOfPlayerOwnedDevelopmentCardDecks);

        ProductionConfig basicProduction = gameInfoConfig.basicProductionPower.stream().iterator().next();
        assertEquals(BASIC_PROD_COST, basicProduction.costs.resources);
        assertEquals(BASIC_PROD_STAR_COST, basicProduction.costs.starResources);
        assertEquals(BASIC_PROD_REWARD, basicProduction.rewards.resources);
        assertEquals(BASIC_PROD_STAR_REWARD, basicProduction.rewards.starResources);
        assertEquals(BASIC_PROD_FAITH_REWARD, basicProduction.rewards.faithPoints);

        assertTrue(gameInfoConfig.resourceShelves.stream()
            .anyMatch(
                s -> s.storage.rules.stream()
                    .anyMatch(
                        r -> r instanceof ResourceStorageConfig.StorageConfig.MaxResourceNumberRuleConfig
                            && ((ResourceStorageConfig.StorageConfig.MaxResourceNumberRuleConfig) r).maxNumber == GAME_RESOURCE_STORAGE_MAX_NUM_RESOURCES
                    )
            )
        );

        assertTrue(gameInfoConfig.resourceShelves.stream()
            .anyMatch(
                s -> s.storage.rules.stream()
                    .anyMatch(
                        r -> r instanceof ResourceStorageConfig.StorageConfig.SameResourceTypeRuleConfig
                    )
            )
        );

        assertEquals(DIFFERENT_RESOURCES_IN_DIFFERENT_STORAGES, gameInfoConfig.differentResourcesInDifferentStorages);
        assertEquals(NUM_RESOURCES_REWARDING_ONE_VICTORY_POINTS, gameInfoConfig.numberOfResourcesRewardingOneVictoryPoint);
    }

}
