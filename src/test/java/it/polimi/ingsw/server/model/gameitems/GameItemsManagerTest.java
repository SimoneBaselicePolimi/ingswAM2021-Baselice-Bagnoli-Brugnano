package it.polimi.ingsw.server.model.gameitems;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameItemsManagerTest {

    GameItemsManager gameItemsManager;

    LeaderCard lc1;

    LeaderCard lc2;

    DevelopmentCard dc1;

    @BeforeEach
    void setUp() {
        gameItemsManager = new GameItemsManager();

        lc1 = new LeaderCard(
            "lc1",
            gameItemsManager,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            0
        );

        lc2 = new LeaderCard(
            "lc2",
            gameItemsManager,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            0
        );

        dc1 = new DevelopmentCard(
            "dc1",
            gameItemsManager,
            DevelopmentCardLevel.FIRST_LEVEL,
            DevelopmentCardColour.BLUE,
            null,
            0,
            new HashMap<>()
        );

        gameItemsManager.addItem(lc1);
        gameItemsManager.addItem(lc2);
        gameItemsManager.addItem(dc1);

    }

    @Test
    void testGetAllItemsOfType() {
        assertEquals(Set.of(lc1, lc2), gameItemsManager.getAllItemsOfType(LeaderCard.class));
        assertEquals(Set.of(dc1), gameItemsManager.getAllItemsOfType(DevelopmentCard.class));
    }

    @Test
    void testGetItem() {
        assertEquals(lc1, gameItemsManager.getItem(LeaderCard.class, "lc1"));
        assertEquals(lc2, gameItemsManager.getItem(LeaderCard.class, "lc2"));
        assertEquals(dc1, gameItemsManager.getItem(DevelopmentCard.class, "dc1"));
    }

    @Test
    void testGetAllItemsOfTypeThatDoesNotExist() {
        assertEquals(new HashSet<>(), gameItemsManager.getAllItemsOfType(MarbleColour.class));
    }

    @Test
    void testGetItemWithInvalidId() {
        assertThrows(
            IllegalArgumentException.class,
            () -> gameItemsManager.getItem(LeaderCard.class, "ID_THAT_DOES_NOT_EXIST")
        );
    }

}