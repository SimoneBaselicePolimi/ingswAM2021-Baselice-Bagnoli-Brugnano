package it.polimi.ingsw.server.controller.clientrequest.validator;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.server.controller.clientrequest.InitialChoicesClientRequest;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardImp;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class InitialChoicesClientRequestValidatorTest extends ValidatorTest<InitialChoicesClientRequest, InitialChoicesClientRequestValidator>{

    InitialChoicesClientRequestValidator validator = getValidator();

    @Mock
    ResourceStorage validShelve;

    @Mock
    ResourceStorage validShelveWithViolationRule;

    @Mock
    ResourceStorage invalidStorage;

    int numberOfLeadersCardsGivenToThePlayer = 4;
    int numberOfLeadersCardsThePlayerKeeps = 2;

    Map<Player, Integer> playerInitialFaithPoints;
    Map<Player, Integer> playerInitialStarResources;

    GameRules gameRules;

    @BeforeEach
    void setUp(){

        playerInitialFaithPoints = Map.of(player, 0);
        playerInitialStarResources = Map.of(player, 3);

        Map<Integer, GameInfoConfig.GameSetup.InitialPlayerResourcesAndFaithPoints> initialResources = new HashMap<>();
        initialResources.put(1, new GameInfoConfig.GameSetup.InitialPlayerResourcesAndFaithPoints(
            playerInitialStarResources.get(player),
            playerInitialFaithPoints.get(player)
        ));

        gameRules = new GameRules(
            new GameInfoConfig(
                1,
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
                0
            ),
            null,
            null,
            null,
            null
        );

        lenient().when(gameManager.getGameRules()).thenReturn(gameRules);
        lenient().when(gameContext.getPlayersTurnOrder()).thenReturn(List.of(player));
        lenient().when(playerContext.getShelves()).thenReturn(Set.of(validShelve, validShelveWithViolationRule));
        lenient().when(validShelve.canAddResources(any())).thenReturn(true);
        lenient().when(invalidStorage.canAddResources(any())).thenReturn(false);
        lenient().when(validShelveWithViolationRule.canAddResources(any())).thenReturn(false);
    }

    @Test
    void testNumberOfStarResources(){

        InitialChoicesClientRequest request1 = new InitialChoicesClientRequest(
                player,
                Set.of(mock(LeaderCardImp.class), mock(LeaderCardImp.class)),
                Map.of(validShelve, Map.of(ResourceType.COINS, 3, ResourceType.STONES, 1))
        );

        InitialChoicesClientRequest request2 = new InitialChoicesClientRequest(
            player,
            Set.of(mock(LeaderCardImp.class), mock(LeaderCardImp.class)),
            Map.of(validShelve, Map.of(ResourceType.SHIELDS, 1))
        );

        assertTrue(validator.getErrorMessage(request1, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request2, gameManager).isPresent());
    }

    @Test
    void testValidStorages(){

        InitialChoicesClientRequest request1 = new InitialChoicesClientRequest(
            player,
            Set.of(mock(LeaderCardImp.class), mock(LeaderCardImp.class)),
            Map.of(invalidStorage, Map.of(ResourceType.COINS, 2, ResourceType.STONES, 1))
        );

        InitialChoicesClientRequest request2 = new InitialChoicesClientRequest(
            player,
            Set.of(mock(LeaderCardImp.class), mock(LeaderCardImp.class)),
            Map.of(validShelveWithViolationRule, Map.of(ResourceType.COINS, 2, ResourceType.STONES, 1))
        );

        InitialChoicesClientRequest request3 = new InitialChoicesClientRequest(
            player,
            Set.of(mock(LeaderCardImp.class), mock(LeaderCardImp.class)),
            Map.of(validShelve, Map.of(ResourceType.COINS, 2, ResourceType.STONES, 1))
        );

        assertTrue(validator.getErrorMessage(request1, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request2, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request3, gameManager).isEmpty());
    }

    @Test
    void testNumberOfCards(){

        InitialChoicesClientRequest request1 = new InitialChoicesClientRequest(
            player,
            Set.of(mock(LeaderCardImp.class)),
            Map.of(invalidStorage, Map.of(ResourceType.COINS, 2, ResourceType.STONES, 1))
        );

        InitialChoicesClientRequest request2 = new InitialChoicesClientRequest(
            player,
            Set.of(mock(LeaderCardImp.class), mock(LeaderCardImp.class), mock(LeaderCardImp.class)),
            Map.of(invalidStorage, Map.of(ResourceType.COINS, 2, ResourceType.STONES, 1))
        );

        InitialChoicesClientRequest request3 = new InitialChoicesClientRequest(
            player,
            new HashSet<>(),
            Map.of(invalidStorage, Map.of(ResourceType.COINS, 2, ResourceType.STONES, 1))
        );

        assertTrue(validator.getErrorMessage(request1, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request2, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request3, gameManager).isPresent());

    }

    @Override
    InitialChoicesClientRequest createClientRequestToValidate() {
        return new InitialChoicesClientRequest(
            player,
            new HashSet<>(),
            new HashMap<>()
        );
    }

    @Override
    Class<InitialChoicesClientRequestValidator> getValidatorType() {
        return InitialChoicesClientRequestValidator.class;
    }
}