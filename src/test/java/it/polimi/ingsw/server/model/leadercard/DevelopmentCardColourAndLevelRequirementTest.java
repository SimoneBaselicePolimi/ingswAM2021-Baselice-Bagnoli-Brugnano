package it.polimi.ingsw.server.model.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.leadercard.DevelopmentCardColourAndLevelRequirement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class DevelopmentCardColourAndLevelRequirementTest {
    @Mock
    GameItemsManager gameItemsManager;

    @Mock
    PlayerContext playerContext1;

    @Mock
    PlayerContext playerContext2;

    @Mock
    PlayerContext playerContext3;

    @Mock
    Production production;

    DevelopmentCardColourAndLevelRequirement requirement1 =
        new DevelopmentCardColourAndLevelRequirement(DevelopmentCardColour.BLUE, DevelopmentCardLevel.FIRST_LEVEL,3);
    DevelopmentCardColourAndLevelRequirement requirement2 =
        new DevelopmentCardColourAndLevelRequirement(DevelopmentCardColour.BLUE, DevelopmentCardLevel.SECOND_LEVEL,2);
    DevelopmentCardColourAndLevelRequirement requirement3 =
        new DevelopmentCardColourAndLevelRequirement(DevelopmentCardColour.YELLOW, DevelopmentCardLevel.THIRD_LEVEL,1);
    DevelopmentCardColourAndLevelRequirement requirement4 =
        new DevelopmentCardColourAndLevelRequirement(DevelopmentCardColour.YELLOW, DevelopmentCardLevel.SECOND_LEVEL,1);
    DevelopmentCardColourAndLevelRequirement requirement5 =
        new DevelopmentCardColourAndLevelRequirement(DevelopmentCardColour.PURPLE, DevelopmentCardLevel.THIRD_LEVEL,2);
    DevelopmentCardColourAndLevelRequirement requirement6 =
        new DevelopmentCardColourAndLevelRequirement(DevelopmentCardColour.GREEN, DevelopmentCardLevel.FIRST_LEVEL,3);

    @BeforeEach
    void setUp() {

        // 3 BLUE FIRST LEVEL, 2 GREEN FIRST LEVEL, 1 BLUE SECOND LEVEL, 3 PURPLE THIRD LEVEL, 1 YELLOW THIRD LEVEL
        lenient().when(playerContext1.getAllDevelopmentCards()).thenReturn(Set.of(
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production,3, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production,3, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production,3, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.GREEN, production,2, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.GREEN, production,2, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE, production,1, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production,3, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production,3, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production,3, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.YELLOW, production,1, new HashMap<>())
        ));

        // 5 YELLOW FIRST LEVEL, 2 YELLOW SECOND LEVEL
        lenient().when(playerContext2.getAllDevelopmentCards()).thenReturn(Set.of(
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.YELLOW, production,5, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.YELLOW, production,5, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.YELLOW, production,5, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.YELLOW, production,5, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.YELLOW, production,5, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW, production,2, new HashMap<>()),
            new DevelopmentCard("testID", gameItemsManager, DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW, production,2, new HashMap<>())
        ));

        lenient().when(playerContext3.getAllDevelopmentCards()).thenReturn(new HashSet<>());

    }

    /**
     * Tests the method to verify if a player has the necessary requirements to activate a leader card:
     * the method returns true if the player has the necessary development cards (with specific colour and level).
     */
    @Test
    void checkRequirementTest(){
        assertTrue(requirement1.checkRequirement(playerContext1));
        assertFalse(requirement1.checkRequirement(playerContext2));
        assertFalse(requirement2.checkRequirement(playerContext1));
        assertFalse(requirement2.checkRequirement(playerContext2));
        assertTrue(requirement3.checkRequirement(playerContext1));
        assertFalse(requirement3.checkRequirement(playerContext2));
        assertFalse(requirement4.checkRequirement(playerContext1));
        assertTrue(requirement4.checkRequirement(playerContext2));
        assertTrue(requirement5.checkRequirement(playerContext1));
        assertFalse(requirement5.checkRequirement(playerContext2));
        assertFalse(requirement6.checkRequirement(playerContext1));
        assertFalse(requirement6.checkRequirement(playerContext2));
    }

    /**
     * Tests the method to verify if a player has the necessary requirements to activate a leader card
     * passing to the method a player who doesn't have any development card.
     * The method must always return false.
     */
    @Test
    void checkRequirementTestWithEmptyList (){
        assertFalse(requirement1.checkRequirement(playerContext3));
        assertFalse(requirement2.checkRequirement(playerContext3));
        assertFalse(requirement3.checkRequirement(playerContext3));
        assertFalse(requirement4.checkRequirement(playerContext3));
        assertFalse(requirement5.checkRequirement(playerContext3));
        assertFalse(requirement6.checkRequirement(playerContext3));
    }

}

