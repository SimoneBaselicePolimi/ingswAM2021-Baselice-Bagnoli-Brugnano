package it.polimi.ingsw.server.model.gameitems;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameItemsManagerTest {

    GameItemsManager itemsManager;

    LeaderCard lc1;

    LeaderCard lc2;

    DevelopmentCard dc1;

    @BeforeEach
    void setUp() {

        lc1 = new LeaderCard(
            "lc1",
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            0
        );

        lc2 = new LeaderCard(
            "lc2",
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            0
        );

        dc1 = new DevelopmentCard(
            "dc1",
            DevelopmentCardLevel.FIRST_LEVEL,
            DevelopmentCardColour.BLUE,
            mock(Production.class),
            0,
            new HashMap<>()
        );

        itemsManager = new GameItemsManager();
        itemsManager.addItem(lc1);
        itemsManager.addItem(lc2);
        itemsManager.addItem(dc1);

    }

    @Test
    void testGetAllItemsOfType() {
        assertEquals(Set.of(lc1, lc2), itemsManager.getAllItemsOfType(LeaderCard.class));
        assertEquals(Set.of(dc1), itemsManager.getAllItemsOfType(DevelopmentCard.class));
    }

    @Test
    void testGetItem() {
        assertEquals(lc1, itemsManager.getItem(LeaderCard.class, "lc1"));
        assertEquals(lc2, itemsManager.getItem(LeaderCard.class, "lc2"));
        assertEquals(dc1, itemsManager.getItem(DevelopmentCard.class, "dc1"));
    }

    @Test
    void testGetAllItemsOfTypeThatDoesNotExist() {
        assertEquals(new HashSet<>(), itemsManager.getAllItemsOfType(MarbleColour.class));
    }

    @Test
    void testGetItemWithInvalidId() {
        assertThrows(
            IllegalArgumentException.class,
            () -> itemsManager.getItem(LeaderCard.class, "ID_THAT_DOES_NOT_EXIST")
        );
    }

}