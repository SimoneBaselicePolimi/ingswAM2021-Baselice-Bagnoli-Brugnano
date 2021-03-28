package it.polimi.ingsw.server.model.gameitems;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MarbleColourTest {

    MarbleColour red1 = new MarbleColour(
            Optional.of(ResourceType.SERVANTS), 1, false, "RedMarble");
    MarbleColour red2 = new MarbleColour(
            Optional.of(ResourceType.SERVANTS), 1, false, "RedMarble");
    MarbleColour red3 = new MarbleColour(
            Optional.of(ResourceType.SHIELDS), 2, true, "RedMarble");
    MarbleColour blue1 = new MarbleColour(
            Optional.of(ResourceType.COINS), 0, false, "BlueMarble");
    MarbleColour green1 = new MarbleColour(
            Optional.of(ResourceType.COINS), 0, false, "GreenMarble");

    @Test
    void testEquals() {
        assertEquals(red1, red2);
        assertEquals(red1, red3);
        assertEquals(red2,red3);
        assertNotEquals(red1,blue1);
        assertNotEquals(red3, blue1);
        assertNotEquals(blue1,green1);
    }
}