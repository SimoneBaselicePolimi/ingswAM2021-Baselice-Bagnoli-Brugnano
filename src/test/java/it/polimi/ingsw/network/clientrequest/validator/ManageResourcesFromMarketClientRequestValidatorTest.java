package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
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
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)

class ManageResourcesFromMarketClientRequestValidatorTest extends ValidatorTest<ManageResourcesFromMarketClientRequest, ManageResourcesFromMarketClientRequestValidator>
{

    @Mock
    GameItemsManager gameItemsManager;

    ManageResourcesFromMarketClientRequestValidator validator = new ManageResourcesFromMarketClientRequestValidator();

    @Mock
    ResourceStorage invalidStorage;

    @Mock
    ResourceStorage validStorage;

    Map<ResourceStorage, Map<ResourceType, Integer>> validStarResourcesChosenToAddByStorage = new HashMap<>();
    Map<ResourceStorage, Map<ResourceType, Integer>> resourcesToAddByValidStorage = new HashMap<>();

    @BeforeEach
    void setUp(){
        //TemporaryStorageResources = 3 SHIELDS, 2 STONES
        //TempStarResources = 2
        //playerMarbleSubstitution = COINS

        validStarResourcesChosenToAddByStorage.put(validStorage, Map.of(ResourceType.COINS, 2));

        resourcesToAddByValidStorage.put(validStorage, Map.of(ResourceType.SHIELDS, 3,ResourceType.STONES,2));

        lenient().when(gameManager.getGameItemsManager()).thenReturn(gameItemsManager);
        lenient().when(playerContext.getActiveLeaderCardsWhiteMarblesMarketSubstitutions()).thenReturn(Set.of(ResourceType.COINS));
        lenient().when(invalidStorage.canAddResources(any())).thenReturn(false);
        lenient().when(validStorage.canAddResources(any())).thenReturn(true);
        lenient().when(playerContext.getTempStarResources()).thenReturn(2);
        lenient().when(playerContext.getTemporaryStorageResources()).thenReturn(Map.of(
            ResourceType.SHIELDS, 3,
            ResourceType.STONES,2
        ));
    }

    @Test
    void testInvalidStorage() {

        ManageResourcesFromMarketClientRequest invalidStorageRequest = new ManageResourcesFromMarketClientRequest(
            player,
            Map.of(invalidStorage, Map.of(ResourceType.SHIELDS, 3,ResourceType.STONES,2)),
            validStarResourcesChosenToAddByStorage,
            new HashMap<>()
        );

        assertTrue(validator.getErrorMessage(invalidStorageRequest, gameManager).isPresent());
    }

    @Test
    void testTypeAndNumberOfStarResources() {

        ManageResourcesFromMarketClientRequest invalidNumberOfStarResourcesRequest = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            Map.of(validStorage, Map.of(ResourceType.SERVANTS, 2)),
            new HashMap<>()
        );

        ManageResourcesFromMarketClientRequest invalidTypeOfStarResourcesRequest = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            Map.of(validStorage, Map.of(ResourceType.SHIELDS, 3, ResourceType.STONES, 2)),
            new HashMap<>()
        );

        assertTrue(validator.getErrorMessage(invalidNumberOfStarResourcesRequest, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(invalidTypeOfStarResourcesRequest, gameManager).isPresent());

    }


    @Test
    void testTypeAndNumberOfResources() {

        ManageResourcesFromMarketClientRequest invalidNumberResourcesRequest = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            Map.of(validStorage, Map.of(ResourceType.SHIELDS, 1)),
            new HashMap<>()
        );

        ManageResourcesFromMarketClientRequest invalidTypeResourcesRequest = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            Map.of(validStorage, Map.of(ResourceType.COINS, 3, ResourceType.STONES, 2)),
            new HashMap<>()
        );

        assertTrue(validator.getErrorMessage(invalidNumberResourcesRequest, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(invalidTypeResourcesRequest, gameManager).isPresent());
    }

    @Test
    void testValidRequest() {

        ManageResourcesFromMarketClientRequest validRequest1 = new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            validStarResourcesChosenToAddByStorage,
            new HashMap<>()
        );

        ManageResourcesFromMarketClientRequest validRequest2 = new ManageResourcesFromMarketClientRequest(
            player,
            Map.of(validStorage, Map.of(ResourceType.SHIELDS, 1,ResourceType.STONES,2)),
            validStarResourcesChosenToAddByStorage,
            Map.of(ResourceType.SHIELDS, 2)
        );

        assertTrue(validator.getErrorMessage(validRequest1, gameManager).isEmpty());
        assertTrue(validator.getErrorMessage(validRequest2, gameManager).isEmpty());

    }

    @Override
    ManageResourcesFromMarketClientRequest createClientRequestToValidate() {
        return new ManageResourcesFromMarketClientRequest(
            player,
            resourcesToAddByValidStorage,
            validStarResourcesChosenToAddByStorage,
            new HashMap<>()
        );
    }

    @Override
    Class<ManageResourcesFromMarketClientRequestValidator> getValidatorType() {
        return ManageResourcesFromMarketClientRequestValidator.class;
    }

}