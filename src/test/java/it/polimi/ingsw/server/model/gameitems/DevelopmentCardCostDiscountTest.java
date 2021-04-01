package it.polimi.ingsw.server.model.gameitems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardCostDiscountTest {
    /**
     * Tests the construction of different Development Card Cost Discount Items. If null Resource type or
     * a negative number are passed as parameters to the constructor, an Exception is thrown.
     */
    @Test
    void testDevelopmentCardCostDiscountConstructor() {
        assertThrows(
                IllegalArgumentException.class,
                ()->new DevelopmentCardCostDiscount(null, 2)
        );
        assertThrows(
                IllegalArgumentException.class,
                ()->new DevelopmentCardCostDiscount(ResourceType.COINS, -3)
        );
        assertDoesNotThrow(
                ()->new DevelopmentCardCostDiscount(ResourceType.SERVANTS, 2)
        );
    }

    /**
     * Test the methods to get all the attributes of Discount Items.
     * Compares attributes of different Discount Items.
     */
    @Test
    void testGetProductionDiscountAttributes() {
        DevelopmentCardCostDiscount prodDiscount1 = new DevelopmentCardCostDiscount(
               ResourceType.SHIELDS, 3);
        DevelopmentCardCostDiscount prodDiscount2 = new DevelopmentCardCostDiscount(
                ResourceType.SHIELDS, 1);

        assertEquals(ResourceType.SHIELDS,prodDiscount1.getResourceTypeToDiscount());
        assertEquals(3, prodDiscount1.getAmountToDiscount());

        assertEquals(prodDiscount1.getResourceTypeToDiscount(), prodDiscount2.getResourceTypeToDiscount());
        assertNotEquals(prodDiscount1.getAmountToDiscount(), prodDiscount2.getAmountToDiscount());
    }
}
