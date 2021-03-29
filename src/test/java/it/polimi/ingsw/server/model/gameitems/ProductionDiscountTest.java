package it.polimi.ingsw.server.model.gameitems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionDiscountTest {
    /**
     * Tests the construction of different Production Discount Items. If null Resource type or a negative number
     * are passed as parameters to the constructor, an Exception is thrown.
     */
    @Test
    void testProductionDiscountConstructor() {
        assertThrows(
                IllegalArgumentException.class,
                ()->new ProductionDiscount(null, 2)
        );
        assertThrows(
                IllegalArgumentException.class,
                ()->new ProductionDiscount(ResourceType.COINS, -3)
        );
        assertDoesNotThrow(
                ()->new ProductionDiscount(ResourceType.SERVANTS, 2)
        );
    }

    /**
     * Test the methods to get all the attributes of Production Discount Items.
     * Compares attributes from different Production Discount Items.
     */
    @Test
    void testGetProductionDiscountAttributes() {
        ProductionDiscount prodDiscount1 = new ProductionDiscount(
               ResourceType.SHIELDS, 3);
        ProductionDiscount prodDiscount2 = new ProductionDiscount(
                ResourceType.SHIELDS, 1);

        assertEquals(ResourceType.SHIELDS,prodDiscount1.getResourceTypeToDiscount());
        assertEquals(3, prodDiscount1.getDiscount());

        assertEquals(prodDiscount1.getResourceTypeToDiscount(), prodDiscount2.getResourceTypeToDiscount());
        assertNotEquals(prodDiscount1.getDiscount(), prodDiscount2.getDiscount());
    }
}
