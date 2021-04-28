package it.polimi.ingsw.server.model.gameitems;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class WhiteMarbleSubstitutionTest {

    @Mock
    GameItemsManager gameItemsManager;

    /**
     * Tests the construction of White Marble Substitution Item. If null Resource type is passed as parameter
     * to the constructor, an Exception is thrown.
     */
    @Test
    void testWhiteMarbleSubstitutionConstructor() {
        assertThrows(
                IllegalArgumentException.class,
                ()->new WhiteMarbleSubstitution("ID1", gameItemsManager, null)
        );
        assertDoesNotThrow(
                ()->new WhiteMarbleSubstitution("ID2", gameItemsManager, ResourceType.SERVANTS)
        );
    }

    /**
     * Test the method to get the Resource type in substitution of White Marble.
     */
    @Test
    void testWhiteMarbleSubstitutionResource() {
        WhiteMarbleSubstitution substitution1 = new WhiteMarbleSubstitution(
                "ID1", gameItemsManager, ResourceType.SHIELDS);
        WhiteMarbleSubstitution substitution2 = new WhiteMarbleSubstitution(
                "ID2", gameItemsManager, ResourceType.COINS);
        WhiteMarbleSubstitution substitution3 = new WhiteMarbleSubstitution(
                "ID3", gameItemsManager, ResourceType.SHIELDS);

        assertEquals(ResourceType.SHIELDS,substitution1.getResourceTypeToSubstitute());
        assertEquals(ResourceType.COINS, substitution2.getResourceTypeToSubstitute());
        assertEquals(ResourceType.SHIELDS,substitution3.getResourceTypeToSubstitute());
        assertEquals(substitution1.getResourceTypeToSubstitute(), substitution3.getResourceTypeToSubstitute());
    }

}