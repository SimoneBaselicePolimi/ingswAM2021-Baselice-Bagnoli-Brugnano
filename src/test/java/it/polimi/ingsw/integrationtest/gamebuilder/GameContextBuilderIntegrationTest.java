package it.polimi.ingsw.integrationtest.gamebuilder;

import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.GameContextBuilder;
import it.polimi.ingsw.server.model.gamecontext.GameContextCreationError;
import it.polimi.ingsw.server.model.gamecontext.ObservableGameContextBuilder;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.RegisteredIdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.utils.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class GameContextBuilderIntegrationTest {

    final static String CONFIG_RULES_PATH = "testConfigFiles/StandardGameRules";

    GameRules testGameRules;

    GameContext gameContext;

    GameItemsManager gameItemsManager = new GameItemsManager();

    Player player1, player2, player3, player4;

    Set<Player> players;

    final static int MARKET_N_ROWS = 3;
    final static int MARKET_N_COLUMNS = 4;

    final static int NUMBER_OF_DECKS_ON_TABLE = 2;

    final static DevelopmentCardLevel DEV_CARD_G1_LEVEL = DevelopmentCardLevel.FIRST_LEVEL;
    final static DevelopmentCardColour DEV_CARD_G1_COLOUR = DevelopmentCardColour.GREEN;
    final static Map<ResourceType, Integer> DEV_CARD_G1_PROD1_COST = Map.of(ResourceType.COINS, 1);
    final static int DEV_CARD_G1_PROD1_STAR_COST = 0;
    final static Map<ResourceType, Integer> DEV_CARD_G1_PROD1_REWARD = new HashMap<>();
    final static int DEV_CARD_G1_PROD1_STAR_REWARD = 0;
    final static int DEV_CARD_G1_PROD1_FAITH_REWARD = 1;
    final static int DEV_CARD_G1_VICTORY_POINTS = 1;
    final static Map<ResourceType, Integer> DEV_CARD_G1_PURCHASE_COST = Map.of(ResourceType.SHIELDS, 2);

    Set<String> developmentCardsIds = Set.of(
        "DEV_G1",
        "DEV_G2",
        "DEV_G3",
        "DEV_Y1",
        "DEV_Y2"
    );

    @BeforeEach
    void buildGameContext() throws IOException, GameContextCreationError {
        player1 = new Player("id1");
        player2 = new Player("id2");
        player3 = new Player("id3");
        player4 = new Player("id4");

        players = Set.of(
            player1,
            player2,
            player3,
            player4
        );

        testGameRules = FileManager.getFileManagerInstance().getGameRules(CONFIG_RULES_PATH);

        GameContextBuilder contextBuilder = new ObservableGameContextBuilder(players, testGameRules, gameItemsManager);
        gameContext = contextBuilder.buildGameContext();
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
    void testMarket() {
        Market market = gameContext.getMarket();

        assertEquals(MARKET_N_ROWS, market.getNumOfRows());
        assertEquals(MARKET_N_COLUMNS, market.getNumOfColumns());
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

}
