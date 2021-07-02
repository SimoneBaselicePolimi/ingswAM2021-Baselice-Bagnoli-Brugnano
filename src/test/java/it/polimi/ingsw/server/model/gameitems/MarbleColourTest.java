package it.polimi.ingsw.server.model.gameitems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MarbleColourTest implements IdentifiableItemTest<MarbleColour> {

    @Mock
    GameItemsManager gameItemsManager;

    MarbleColour red1, red2, red3, blue1, green1;

    @BeforeEach
    void setUp() {
        // Marbles Initialization
        red1 = new MarbleColour(
            "RedMarble", gameItemsManager, Optional.of(ResourceType.SERVANTS), 1, false);
        red2 = new MarbleColour(
            "RedMarble", gameItemsManager, Optional.of(ResourceType.SERVANTS), 1, false);
        red3 = new MarbleColour(
            "RedMarble", gameItemsManager,Optional.of(ResourceType.SHIELDS), 2, true);
        blue1 = new MarbleColour(
            "BlueMarble", gameItemsManager, Optional.of(ResourceType.COINS), 0, false);
        green1 = new MarbleColour(
            "GreenMarble", gameItemsManager, Optional.of(ResourceType.COINS), 0, false);
    }

    /**
     * Tests the class constructor and the methods to get different attributes of a Marble.
     */
    @Test
    void MarbleColourConstructorTest() {
        assertEquals(Optional.of(ResourceType.SERVANTS), red1.getResourceType());
        assertEquals(Optional.of(ResourceType.SHIELDS), red3.getResourceType());
        assertEquals("RedMarble", red1.getItemID());
        assertEquals(2, red3.getFaithPoints());
        assertTrue(red3.isSpecialMarble());
    }

    /**
     * Tests the equality between two Marbles only checking the equality of their ID.
     */
    @Test
    void testEquals() {
        assertEquals(red1, red2);
        assertEquals(red1, red3);
        assertEquals(red2,red3);
        assertNotEquals(red1,blue1);
        assertNotEquals(red3, blue1);
        assertNotEquals(blue1,green1);
    }

    @Override
    public MarbleColour initializeItemWithId(String id) {
        return new MarbleColour(id, gameItemsManager, Optional.empty(), 0, false);
    }

}