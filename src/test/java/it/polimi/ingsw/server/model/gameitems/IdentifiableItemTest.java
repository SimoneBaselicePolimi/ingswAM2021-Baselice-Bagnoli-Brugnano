package it.polimi.ingsw.server.model.gameitems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// "Composable" tests using JUnit5
// Inspired by this article: https://keyholesoftware.com/2018/02/06/encouraging-good-behavior-with-junit-5-test-interfaces/

public interface IdentifiableItemTest<I extends IdentifiableItem> {

    I initializeItemWithId(String id);

    @Test
    default void testGetItemId() {
        String id1 = "testID1";
        String id2 = "testID2";
        IdentifiableItem item1 = initializeItemWithId(id1);
        IdentifiableItem item2 = initializeItemWithId(id2);
        assertEquals(id1, item1.getItemID());
        assertEquals(id2, item2.getItemID());
    }

}