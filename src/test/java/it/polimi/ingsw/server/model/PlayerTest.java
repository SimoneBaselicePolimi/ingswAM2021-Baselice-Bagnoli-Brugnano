package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerInitialization() {
        String name = "player1";
        Player player = new Player(name);
        assertEquals(name, player.getName());
    }

}