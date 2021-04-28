package it.polimi.ingsw.server.model.gameitems;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductionTest {

    @Mock
    GameItemsManager gameItemsManager;

    // Resource Map initialization
    Map<ResourceType, Integer> singleResource = Map.of(
        ResourceType.COINS, 2
    );

    Map<ResourceType, Integer> twoResources = Map.of(
        ResourceType.SHIELDS, 15,
        ResourceType.SERVANTS, 3
    );

    Map<ResourceType, Integer> negativeValue = Map.of(
        ResourceType.COINS, 2,
        ResourceType.STONES, -1
    );

    /**
     * Tests the construction of different Production Items. If negative values in the Map, negative number or a null ID
     * are passed as parameters to the constructor, an Exception is thrown. Empty Map are allowed.
     */
    @Test
    void testProductionConstructor() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Production(
                "Prod1", gameItemsManager, negativeValue, twoResources, 0, 1, 2
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new Production(
                "Prod2", gameItemsManager, singleResource, negativeValue, 0, 1, 2
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new Production(
                null, gameItemsManager, negativeValue, twoResources, 0, 1, 2
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new Production(
                "Prod3", gameItemsManager, singleResource, twoResources, 0, -1, 2
            )
        );
        assertDoesNotThrow(
            () -> new Production(
                "Prod4", gameItemsManager, singleResource, twoResources, 0, 1, 2
            )
        );
        assertDoesNotThrow(
            () -> new Production(
                "Prod5", gameItemsManager, singleResource, new HashMap<>(), 0, 1, 2
            )
        );
    }

    /**
     * Test the methods to get all the attributes of Production Items.
     * Compares attributes of different Production Items.
     */
    @Test
    void testGetProductionAttributes() {
        Production p1 = new Production(
            "ID_1", gameItemsManager, singleResource, twoResources, 0, 1, 2
        );
        Production p2 = new Production(
            "ID_2", gameItemsManager, twoResources, new HashMap<>(), 5, 10, 0
        );

        assertEquals(singleResource, p1.getProductionResourceCost());
        assertEquals(twoResources, p1.getProductionResourceReward());
        assertEquals(0, p1.getProductionStarResourceCost());
        assertEquals(1, p1.getProductionStarResourceReward());
        assertEquals(2, p1.getProductionFaithReward());
        assertEquals("ID_1", p1.getItemId());

        assertEquals(p1.getProductionResourceReward(), p2.getProductionResourceCost());
        assertEquals(p1.getProductionStarResourceCost(), p2.getProductionFaithReward());
        assertNotEquals(p1.getProductionResourceCost(), p2.getProductionResourceCost());
    }

    /**
     * Tests the equality between two Production Items only checking the equality of their ID.
     */
    @Test
    void testProductionEquals() {
        assertEquals(
            new Production(
                "SameIDProd", gameItemsManager, singleResource, singleResource, 1, 2, 3
            ),
            new Production(
                "SameIDProd", gameItemsManager, singleResource, twoResources, 0, 1, 3
            )
        );
        assertNotEquals(
            new Production(
                "IDProd", gameItemsManager, twoResources, singleResource, 1, 0, 3
            ),
            new Production(
                "DifferentIDProd", gameItemsManager, twoResources, singleResource, 1, 0, 3
            )
        );
    }

}