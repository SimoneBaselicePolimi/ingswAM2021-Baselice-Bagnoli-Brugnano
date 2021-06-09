package it.polimi.ingsw.server.controller.clientrequest.validator;

import it.polimi.ingsw.server.controller.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class ProductionActionClientRequestValidatorTest extends ValidatorTest<ProductionActionClientRequest, ProductionActionClientRequestValidator>
{
    ProductionActionClientRequestValidator validator = getValidator();

    Set<Production> productionsThePlayerCanActivate = TestUtils.generateSetOfMockWithID(Production.class, 5);
    Iterator<Production> iter;
    Production rightProduction1;
    Production rightProduction2;

    @BeforeEach
    void setUp() {

        //rightProduction1
        //- Cost : 0
        //- StarResourceCost : 2
        //- StarResourceReward : 3

        //rightProduction2
        //- Cost : 1 COINS, 2 SHIELDS
        //- StarResourceCost : 0
        //- StarResourceReward : 0

        //The player has 1 COINS, 5 SHIELDS, 3 STONES

        iter = productionsThePlayerCanActivate.iterator();
        rightProduction1 = iter.next();
        rightProduction2 = iter.next();

        when(rightProduction1.getProductionStarResourceCost()).thenReturn(2);
        when(rightProduction2.getProductionStarResourceCost()).thenReturn(0);

        when(rightProduction1.getProductionStarResourceReward()).thenReturn(3);
        when(rightProduction2.getProductionStarResourceReward()).thenReturn(0);

        when(rightProduction1.getProductionResourceCost()).thenReturn(new HashMap<>());
        when(rightProduction2.getProductionResourceCost()).thenReturn(Map.of(ResourceType.COINS, 1, ResourceType.SHIELDS, 2));

        lenient().when(playerContext.getActiveProductions()).thenReturn(productionsThePlayerCanActivate);

        lenient().when(playerContext.getAllResources()).thenReturn(Map.of(ResourceType.COINS, 1, ResourceType.SHIELDS, 5, ResourceType.STONES,3));
    }

    @Test
    void testNotActivatedProduction(){

        ProductionActionClientRequest request = new ProductionActionClientRequest(
            player,
            Set.of(mock(Production.class)),
            null,
            null
        );

        assertTrue(validator.getErrorMessage(request, gameManager).isPresent());
    }

    @Test
    void testNumberOfStarResourcesCost(){

        ProductionActionClientRequest request1 = new ProductionActionClientRequest(
            player,
            Set.of(rightProduction1),
            Map.of(ResourceType.STONES,2),
            Map.of(ResourceType.COINS, 3)
        );

        ProductionActionClientRequest request2 = new ProductionActionClientRequest(
            player,
            Set.of(rightProduction1,rightProduction2),
            Map.of(ResourceType.SHIELDS, 3),
            Map.of(ResourceType.COINS, 3)
        );

        assertTrue(validator.getErrorMessage(request1, gameManager).isEmpty());
        assertTrue(validator.getErrorMessage(request2, gameManager).isPresent());
    }

    @Test
    void testNumberOfStarResourcesAward(){

        ProductionActionClientRequest request1 = new ProductionActionClientRequest(
            player,
            Set.of(rightProduction1),
            Map.of(ResourceType.STONES,2),
            Map.of(ResourceType.COINS, 2, ResourceType.SERVANTS,1)
        );

        ProductionActionClientRequest request2 = new ProductionActionClientRequest(
            player,
            Set.of(rightProduction1,rightProduction2),
            Map.of(ResourceType.STONES,2),
            Map.of(ResourceType.COINS, 2)
        );

        assertTrue(validator.getErrorMessage(request1, gameManager).isEmpty());
        assertTrue(validator.getErrorMessage(request2, gameManager).isPresent());
    }

    @Test
    void notEnoughResources(){

        ProductionActionClientRequest request1 = new ProductionActionClientRequest(
            player,
            Set.of(rightProduction1,rightProduction2),
            Map.of(ResourceType.SHIELDS,1, ResourceType.COINS,1),
            Map.of(ResourceType.COINS, 2, ResourceType.SERVANTS,1)
        );

        ProductionActionClientRequest request2 = new ProductionActionClientRequest(
            player,
            Set.of(rightProduction1, rightProduction2),
            Map.of(ResourceType.SERVANTS, 2),
            Map.of(ResourceType.COINS, 2, ResourceType.SERVANTS,1)
        );

        ProductionActionClientRequest request3 = new ProductionActionClientRequest(
            player,
            Set.of(rightProduction2),
            new HashMap<>(),
            new HashMap<>()
        );

        assertTrue(validator.getErrorMessage(request1, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request2, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request3, gameManager).isEmpty());
    }

    @Override
    ProductionActionClientRequest createClientRequestToValidate() {
        return new ProductionActionClientRequest(
            player,
            Set.of(mock(Production.class)),
            null,
            null
        );
    }

    @Override
    Class<ProductionActionClientRequestValidator> getValidatorType() {
        return ProductionActionClientRequestValidator.class;
    }

}