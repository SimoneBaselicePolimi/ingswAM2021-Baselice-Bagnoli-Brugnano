package it.polimi.ingsw.server.model.gameitems;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardImp;
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

        lc1 = new LeaderCardImp(
            "lc1",
            gameItemsManager,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            0
        );

        lc2 = new LeaderCardImp(
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
            new HashSet<>(),
            0,
            new HashMap<>()
        );

        gameItemsManager.addItem(lc1);
        gameItemsManager.addItem(lc2);
        gameItemsManager.addItem(dc1);

    }

    @Test
    void testGetAllItemsOfType() {
        assertEquals(Set.of(lc1, lc2), gameItemsManager.getAllItemsOfType(LeaderCardImp.class));
        assertEquals(Set.of(dc1), gameItemsManager.getAllItemsOfType(DevelopmentCard.class));
    }

    @Test
    void testGetItem() {
        assertEquals(lc1, gameItemsManager.getItem(LeaderCardImp.class, "lc1"));
        assertEquals(lc2, gameItemsManager.getItem(LeaderCardImp.class, "lc2"));
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
            () -> gameItemsManager.getItem(LeaderCardImp.class, "ID_THAT_DOES_NOT_EXIST")
        );
    }

}