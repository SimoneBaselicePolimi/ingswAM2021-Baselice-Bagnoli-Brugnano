package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerTest {

    @Mock
    GameItemsManager gameItemsManager;

    @Test
    void testPlayerInitialization() {
        String name = "player1";
        Player player = new Player(name, gameItemsManager);
        assertEquals(name, player.getName());
    }

}