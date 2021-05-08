package it.polimi.ingsw.network.clientrequest.validator;
import static org.junit.jupiter.api.Assertions.*;
import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.network.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ProductionActionClientRequestValidatorTest {
    @Mock
    GameManager gameManager;

    @Mock
    GameContext gameContext;

    @Mock
    PlayerContext playerContext;

    ProductionActionClientRequestValidator validator = new ProductionActionClientRequestValidator();

    @Mock
    Player player;

    @Mock
    Production differentProduction;

    Map<ResourceType, Integer> starResourceCost = Map.of(
        ResourceType.COINS, 1,
        ResourceType.SHIELDS, 2
    );

    Map<ResourceType, Integer> starResourceReward = Map.of(
        ResourceType.STONES,3
    );

    Set<Production> productionsThePlayerCanActivate = new HashSet<>();
    Iterator<Production> iter;
    Production rightProduction;

    @BeforeEach
    void setUp() {
//lenient().when(gameManager.getGameItemsManager()).thenReturn(gameItemsManager);
//        lenient().when(gameManager.getGameContext()).thenReturn(gameContext);
//        lenient().when(gameContext.getPlayerContext(player)).thenReturn(playerContext);
//        lenient().when(playerContext.getActiveLeaderCardsWhiteMarblesMarketSubstitutions()).thenReturn(playerMarbleSubstitution);
//        lenient().when(storage1.canAddResources(any())).thenReturn(false);
//        lenient().when(storage2.canAddResources(any())).thenReturn(true);
//        lenient().when(playerContext.getTempStarResources()).thenReturn(2);
//        lenient().when(playerContext.getTemporaryStorageResources()).thenReturn(Map.of(
//            ResourceType.SHIELDS, 3,
//            ResourceType.STONES,2
//        ));
        lenient().when(gameManager.getGameContext()).thenReturn(gameContext);
        lenient().when(gameContext.getPlayerContext(player)).thenReturn(playerContext);
        lenient().when(playerContext.getActiveProductions()).thenReturn(productionsThePlayerCanActivate);
        lenient().when(productionsThePlayerCanActivate.contains(differentProduction)).thenReturn(false);
    }

    @Test
    void getValidatorFromClientRequest() {
        ProductionActionClientRequest request = new ProductionActionClientRequest(
            player,
            Set.of(rightProduction),
            null,
            null
        );
        assertTrue(request.getValidator() instanceof ProductionActionClientRequestValidator);
    }

    @Test
    void testGetError(){
        iter = productionsThePlayerCanActivate.iterator();
        rightProduction = iter.next();

//    public final Set<Production> productions;
//    public final Map<ResourceType, Integer> starResourceCost;
//    public final Map<ResourceType, Integer> starResourceReward;
        assertTrue(validator.getErrorMessage(new ProductionActionClientRequest(
            player,
            Set.of(differentProduction),
            null,
            null
        ),
            gameManager
        ).isPresent());

        assertTrue(validator.getErrorMessage(new ProductionActionClientRequest(
                player,
                Set.of(differentProduction),
                null,
                null
            ),
            gameManager
        ).isPresent());
    }

}