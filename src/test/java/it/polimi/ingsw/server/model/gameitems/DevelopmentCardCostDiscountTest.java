package it.polimi.ingsw.server.model.gameitems;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardCostDiscountTest {

    @Mock
    GameItemsManager gameItemsManager;

    /**
     * Tests the construction of different Development Card Cost Discount Items. If null Resource type or
     * a negative number are passed as parameters to the constructor, an Exception is thrown.
     */
    @Test
    void testDevelopmentCardCostDiscountConstructor() {
        assertThrows(
                IllegalArgumentException.class,
                ()->new DevelopmentCardCostDiscount("ID1", gameItemsManager, null, 2)
        );
        assertThrows(
                IllegalArgumentException.class,
                ()->new DevelopmentCardCostDiscount("ID2", gameItemsManager, ResourceType.COINS, -3)
        );
        assertDoesNotThrow(
                ()->new DevelopmentCardCostDiscount("ID3", gameItemsManager, ResourceType.SERVANTS, 2)
        );
    }

    /**
     * Test the methods to get all the attributes of Discount Items.
     * Compares attributes of different Discount Items.
     */
    @Test
    void testGetProductionDiscountAttributes() {
        DevelopmentCardCostDiscount prodDiscount1 = new DevelopmentCardCostDiscount(
               "ID1", gameItemsManager, ResourceType.SHIELDS, 3);
        DevelopmentCardCostDiscount prodDiscount2 = new DevelopmentCardCostDiscount(
                "ID2", gameItemsManager, ResourceType.SHIELDS, 1);

        assertEquals(ResourceType.SHIELDS,prodDiscount1.getResourceTypeToDiscount());
        assertEquals(3, prodDiscount1.getAmountToDiscount());

        assertEquals(prodDiscount1.getResourceTypeToDiscount(), prodDiscount2.getResourceTypeToDiscount());
        assertNotEquals(prodDiscount1.getAmountToDiscount(), prodDiscount2.getAmountToDiscount());
    }
}
