package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ManageResourcesFromMarketClientRequestValidatorTest {

    @Mock
    GameManager gameManager;

    @Mock
    GameContext gameContext;

    @Mock
    PlayerContext playerContext;

    @Mock
    GameItemsManager gameItemsManager;

    @Mock
    ManageResourcesFromMarketClientRequestValidator validator;

    @Mock
    Player player;

    @Mock
    ResourceStorage storage1;

    @Mock
    ResourceStorage storage2;

    Map<ResourceStorage, Map<ResourceType, Integer>> invalidStarResourcesChosenToAddByStorage = new HashMap<>();
    Map<ResourceStorage, Map<ResourceType, Integer>> invalidNumberOfStarResourcesToAddByStorage = new HashMap<>();
    Map<ResourceStorage, Map<ResourceType, Integer>> validStarResourcesChosenToAddByStorage = new HashMap<>();
    Map<ResourceStorage, Map<ResourceType, Integer>> resourcesToAddByInvalidStorage = new HashMap<>();
    Map<ResourceStorage, Map<ResourceType, Integer>> resourcesToAddByValidStorage = new HashMap<>();
    Map<ResourceStorage, Map<ResourceType, Integer>> invalidResourcesToAddByValidStorage = new HashMap<>();
    Set<ResourceType> playerMarbleSubstitution = Set.of(ResourceType.COINS);
    Map<ResourceType, Integer> resourcesLeftInTemporaryStorage = new HashMap<>();
    Map<ResourceType, Integer> resourcesLeftInTemporaryStorage2 = Map.of(ResourceType.STONES, 1);

    @BeforeEach
    void setUp(){

        when(gameManager.getGameItemsManager()).thenReturn(gameItemsManager);
        when(gameManager.getGameContext()).thenReturn(gameContext);
        when(gameContext.getPlayerContext(player)).thenReturn(playerContext);
        when(playerContext.getActiveLeaderCardsWhiteMarblesMarketSubstitutions()).thenReturn(playerMarbleSubstitution);
        when(storage1.canAddResources(any())).thenReturn(false);
        when(storage2.canAddResources(any())).thenReturn(true);
        when(playerContext.getTempStarResources()).thenReturn(2);
        when(playerContext.getTemporaryStorageResources()).thenReturn(Map.of(
            ResourceType.SHIELDS, 3,
            ResourceType.STONES,2
        ));
    }

    @Test
    void testGetError(){

        Map<ResourceType, Integer> resources = Map.of(
            ResourceType.SHIELDS, 3,
            ResourceType.STONES,2
        );

        Map<ResourceType, Integer> invalidResources = Map.of(
            ResourceType.SHIELDS, 3,
            ResourceType.STONES,1
        );

        resourcesToAddByInvalidStorage.put(storage1, resources);
        resourcesToAddByValidStorage.put(storage2,resources);
        invalidResourcesToAddByValidStorage.put(storage2,invalidResources);

        Map<ResourceType, Integer> validStarResources = Map.of(ResourceType.COINS, 2);
        Map<ResourceType, Integer> invalidStarResources = Map.of(ResourceType.SERVANTS, 2);
        Map<ResourceType, Integer> invalidNumberOfStarResources = Map.of(ResourceType.COINS, 3);

        invalidStarResourcesChosenToAddByStorage.put(storage2, invalidStarResources);
        validStarResourcesChosenToAddByStorage.put(storage2, validStarResources);
        invalidNumberOfStarResourcesToAddByStorage.put(storage2, invalidNumberOfStarResources);

        ManageResourcesFromMarketClientRequest invalidRequestForStorage = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByInvalidStorage,
            validStarResourcesChosenToAddByStorage,
            resourcesLeftInTemporaryStorage
        );

        ManageResourcesFromMarketClientRequest invalidRequestForMarbleSubstitution = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            invalidStarResourcesChosenToAddByStorage,
            resourcesLeftInTemporaryStorage
        );

        ManageResourcesFromMarketClientRequest invalidNumberOfStarResourcesRequest = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            invalidNumberOfStarResourcesToAddByStorage,
            resourcesLeftInTemporaryStorage
        );

        ManageResourcesFromMarketClientRequest invalidResourcesRequest = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            invalidResourcesToAddByValidStorage,
            resourcesLeftInTemporaryStorage
        );

        ManageResourcesFromMarketClientRequest validRequest1 = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            validStarResourcesChosenToAddByStorage,
            resourcesLeftInTemporaryStorage
        );

        ManageResourcesFromMarketClientRequest validRequest2 = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            invalidNumberOfStarResourcesToAddByStorage,
            resourcesLeftInTemporaryStorage2
        );

        assertTrue(validator.getErrorMessage(invalidRequestForStorage, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(invalidRequestForMarbleSubstitution, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(invalidNumberOfStarResourcesRequest, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(invalidResourcesRequest, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(validRequest1, gameManager).isEmpty());
        assertTrue(validator.getErrorMessage(validRequest2, gameManager).isEmpty());

    }
}