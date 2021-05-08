package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.network.clientrequest.InitialChoicesClientRequest;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)

class InitialChoicesClientRequestValidatorTest {

    @Mock
    ResourceStorage storage1;

    @Mock
    ResourceStorage storage2;

    @Mock
    PlayerContext playerContext;

    Map<ResourceStorage, Map<ResourceType, Integer>> twoResourcesChosen = new HashMap<>();
    Map<ResourceStorage, Map<ResourceType, Integer>> fiveResourcesChosen = new HashMap<>();
    Map<ResourceStorage, Map<ResourceType, Integer>> invalidStorageWithOneResourceChosen = new HashMap<>();

    Player player1 = new Player("first");
    Player player2 = new Player("second");

    List<Player> playersInOrder = List.of(player1, player2);

    @Mock
    GameManager gameManager;

    @Mock
    GameContext gameContext;

    Set<LeaderCard> leaderCardsGivenToThePlayer;

    Iterator<LeaderCard> iter;
    LeaderCard rightCard1;
    LeaderCard rightCard2;

    @Mock
    LeaderCard differentCard;

    int numberOfLeadersCardsGivenToThePlayer = 4;
    int numberOfLeadersCardsThePlayerKeeps = 2;

    InitialChoicesClientRequestValidator validator = new InitialChoicesClientRequestValidator();

    GameRules gameRules;

    Map<Player, Integer> playerInitialFaithPoints = Map.of(
        player1, 0,
        player2, 1
    );

    Map<Player, Integer> playerInitialStarResources = Map.of(
        player1, 1,
        player2, 5
    );

    int maxNumberOfPlayers = 3;

    @BeforeEach
    void setUp() {

        leaderCardsGivenToThePlayer = TestUtils.generateSetOfMockWithID(LeaderCard.class, 4);
        iter = leaderCardsGivenToThePlayer.iterator();
        rightCard1 = iter.next();
        rightCard2 = iter.next();

        Map<Integer, GameInfoConfig.GameSetup.InitialPlayerResourcesAndFaithPoints> initialResources = new HashMap<>();
        for (int i = 0; i < playersInOrder.size(); i++){
            Player player = playersInOrder.get(i);
            initialResources.put(
                i + 1,
                new GameInfoConfig.GameSetup.InitialPlayerResourcesAndFaithPoints(
                    playerInitialStarResources.get(player),
                    playerInitialFaithPoints.get(player))
            );
        }

        gameRules = new GameRules(
            new GameInfoConfig(
                maxNumberOfPlayers,
                false,
                new GameInfoConfig.GameSetup(
                    numberOfLeadersCardsGivenToThePlayer,
                    numberOfLeadersCardsThePlayerKeeps,
                    initialResources
                ),
                0,
                null,
                null,
                false,
                false
            ),
            null,
            null,
            null,
            null
        );

        lenient().when(gameManager.getGameContext()).thenReturn(gameContext);
        lenient().when(gameManager.getGameRules()).thenReturn(gameRules);
        lenient().when(gameContext.getPlayerContext(player1)).thenReturn(playerContext);
        lenient().when(gameContext.getPlayerContext(player2)).thenReturn(playerContext);
        lenient().when(gameContext.getPlayersTurnOrder()).thenReturn(playersInOrder);
        lenient().when(gameManager.getPlayers()).thenReturn(new HashSet<>(playersInOrder));
        lenient().when(storage1.canAddResources(any())).thenReturn(false);
        lenient().when(storage2.canAddResources(any())).thenReturn(true);
        lenient().when(leaderCardsGivenToThePlayer.contains(differentCard)).thenReturn(false);

        twoResourcesChosen.put(storage2, Map.of(ResourceType.STONES, 1, ResourceType.SERVANTS, 1));
        fiveResourcesChosen.put(storage2, Map.of(ResourceType.SHIELDS, 3, ResourceType.COINS, 2));
        invalidStorageWithOneResourceChosen.put(storage1, Map.of(ResourceType.COINS, 1));
    }

    @Test

    void getValidatorFromClientRequest() {
        InitialChoicesClientRequest request = new InitialChoicesClientRequest(
            player1,
            Set.of(rightCard1, rightCard2),
            twoResourcesChosen
        );

        assertTrue(request.getValidator() instanceof InitialChoicesClientRequestValidator);
    }

    @Test
    void testGetError(){

        assertTrue(validator.getErrorMessage(new InitialChoicesClientRequest(
                player1,
                Set.of(rightCard1, rightCard2),
                twoResourcesChosen
            ), gameManager
        ).isPresent());

        assertTrue(validator.getErrorMessage(new InitialChoicesClientRequest(
                player2,
                Set.of(rightCard1, rightCard2),
                fiveResourcesChosen
            ), gameManager
        ).isEmpty());

        assertTrue(validator.getErrorMessage(new InitialChoicesClientRequest(
                player2,
                Set.of(rightCard1, rightCard2),
                twoResourcesChosen
            ), gameManager
        ).isPresent());

        assertTrue(validator.getErrorMessage(new InitialChoicesClientRequest(
                player1,
                Set.of(rightCard1, rightCard2),
                invalidStorageWithOneResourceChosen
            ), gameManager
        ).isPresent());

        assertTrue(validator.getErrorMessage(new InitialChoicesClientRequest(
                player2,
                Set.of(rightCard1),
                fiveResourcesChosen
            ), gameManager
        ).isPresent());

        assertTrue(validator.getErrorMessage(new InitialChoicesClientRequest(
                player2,
                Set.of(differentCard, rightCard2),
                fiveResourcesChosen
            ), gameManager
        ).isPresent());

        assertTrue(validator.getErrorMessage(new InitialChoicesClientRequest(
                player2,
                Set.of(differentCard, rightCard1),
                twoResourcesChosen
            ), gameManager
        ).isPresent());
    }
}