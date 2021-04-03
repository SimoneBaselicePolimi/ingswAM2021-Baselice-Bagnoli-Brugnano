package it.polimi.ingsw.server.model.gameitems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhiteMarbleSubstitutionTest {
    /**
     * Tests the construction of White Marble Substitution Item. If null Resource type is passed as parameter
     * to the constructor, an Exception is thrown.
     */
    @Test
    void testWhiteMarbleSubstitutionConstructor() {
        assertThrows(
                IllegalArgumentException.class,
                ()->new WhiteMarbleSubstitution(null)
        );
        assertDoesNotThrow(
                ()->new WhiteMarbleSubstitution(ResourceType.SERVANTS)
        );
    }

    /**
     * Test the method to get the Resource type in substitution of White Marble.
     */
    @Test
    void testWhiteMarbleSubstitutionResource() {
        WhiteMarbleSubstitution substitution1 = new WhiteMarbleSubstitution(
                ResourceType.SHIELDS);
        WhiteMarbleSubstitution substitution2 = new WhiteMarbleSubstitution(
                ResourceType.COINS);
        WhiteMarbleSubstitution substitution3 = new WhiteMarbleSubstitution(
                ResourceType.SHIELDS);

        assertEquals(ResourceType.SHIELDS,substitution1.getResourceTypeToSubstitute());
        assertEquals(ResourceType.COINS, substitution2.getResourceTypeToSubstitute());
        assertEquals(ResourceType.SHIELDS,substitution3.getResourceTypeToSubstitute());
        assertEquals(substitution1.getResourceTypeToSubstitute(), substitution3.getResourceTypeToSubstitute());
    }

}