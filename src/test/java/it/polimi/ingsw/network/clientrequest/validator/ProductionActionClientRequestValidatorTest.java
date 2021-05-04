package it.polimi.ingsw.network.clientrequest.validator;
//
import static org.junit.jupiter.api.Assertions.*;
//
//import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
//import it.polimi.ingsw.server.model.Player;
//import it.polimi.ingsw.server.model.gamecontext.GameContext;
//import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
//import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
//import it.polimi.ingsw.server.model.gameitems.Production;
//import it.polimi.ingsw.server.model.gameitems.ResourceType;
//import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
//import it.polimi.ingsw.server.model.gamemanager.GameManager;
//import it.polimi.ingsw.server.model.storage.ResourceStorage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//
class ProductionActionClientRequestValidatorTest {
//
//    @Mock
//    GameManager gameManager;
//
//    @Mock
//    GameContext gameContext;
//
//    @Mock
//    PlayerContext playerContext;
//
//    @Mock
//    GameItemsManager gameItemsManager;
//
//    @Mock
//    ManageResourcesFromMarketClientRequestValidator validator;
//
//    @Mock
//    Player player;
//
//    @Mock
//    Production differentProduction;
//
//    Map<ResourceType, Integer> starResourceCost = Map.of(
//        ResourceType.COINS, 1,
//        ResourceType.SHIELDS, 2
//    );
//
//    Map<ResourceType, Integer> starResourceReward = Map.of(
//        ResourceType.STONES,3
//    );
//
//    Set<Production> productionsThePlayerCanActivate;
//    Iterator<Production> iter;
//    Production rightProduction;
//
//    @BeforeEach
//    void setUp() {
//
//        when(gameManager.getGameContext()).thenReturn(gameContext);
//        when(gameContext.getPlayerContext(player)).thenReturn(playerContext);
//        when(playerContext.getActiveProductions()).thenReturn(productionsThePlayerCanActivate);
//        when(productionsThePlayerCanActivate.contains(differentProduction)).thenReturn(false);
//    }
//
//    @Test
//    void testGetError(){
//        iter = productionsThePlayerCanActivate.iterator();
//        rightProduction = iter.next();
//
//        ManageResourcesFromMarketClientRequest notActivateProductionRequest = new ManageResourcesFromMarketClientRequest(
//
//        )
//
//        assertTrue(validator.getErrorMessage(validRequest1, gameManager).isEmpty())
//    }

}