package it.polimi.ingsw.server.model.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.leadercard.DevelopmentCardColourRequirement;
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
public class DevelopmentCardColourRequirementTest {

    @Mock
    GameItemsManager gameItemsManager;

    @Mock
    PlayerContext playerContext1;

    @Mock
    PlayerContext playerContext2;

    @Mock
    PlayerContext playerContext3;

    @Mock
    Set<Production> production;

    DevelopmentCardColourRequirement requirement1, requirement2, requirement3, requirement4, requirement5, requirement6;

    @BeforeEach
    void setUp() {
        requirement1 = new DevelopmentCardColourRequirement(
            DevelopmentCardColour.BLUE, 3
        );
        requirement2 = new DevelopmentCardColourRequirement(
            DevelopmentCardColour.BLUE, 1
        );
        requirement3 = new DevelopmentCardColourRequirement(
            DevelopmentCardColour.YELLOW, 1
        );
        requirement4 = new DevelopmentCardColourRequirement(
            DevelopmentCardColour.YELLOW, 4
        );
        requirement5 = new DevelopmentCardColourRequirement(
            DevelopmentCardColour.PURPLE, 1
        );
        requirement6 = new DevelopmentCardColourRequirement(
            DevelopmentCardColour.GREEN, 0
        );

        // 2 BLUE, 1 GREEN, 1 PURPLE, 1 YELLOW
        Set<DevelopmentCard> developmentCardSet1 = Set.of(
            new DevelopmentCard("testID1", gameItemsManager, DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE, production,3, new HashMap<>()),
            new DevelopmentCard("testID2", gameItemsManager, DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE, production,1, new HashMap<>()),
            new DevelopmentCard("testID3", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.GREEN, production,2, new HashMap<>()),
            new DevelopmentCard("testID4", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production,3, new HashMap<>()),
            new DevelopmentCard("testID5", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.YELLOW, production,1, new HashMap<>())
        );

        //3 YELLOW
        Set<DevelopmentCard> developmentCardSet2 = Set.of(
            new DevelopmentCard("testID6", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.YELLOW, production,1, new HashMap<>()),
            new DevelopmentCard("testID7", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.YELLOW, production,1, new HashMap<>()),
            new DevelopmentCard("testID8", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.YELLOW, production,1, new HashMap<>())
        );

        lenient().when(playerContext1.getAllDevelopmentCards()).thenReturn(developmentCardSet1);

        lenient().when(playerContext2.getAllDevelopmentCards()).thenReturn(developmentCardSet2);

        lenient().when(playerContext3.getAllDevelopmentCards()).thenReturn(new HashSet<>());
    }

    /**
     * Tests the method to verify if a player has the necessary requirements to activate a leader card:
     * the method returns true if the player has the necessary development cards (with specific colour).
     */
    @Test
    void checkRequirementTest(){
        assertFalse(requirement1.checkRequirement(playerContext1));
        assertFalse(requirement1.checkRequirement(playerContext2));
        assertTrue(requirement2.checkRequirement(playerContext1));
        assertFalse(requirement2.checkRequirement(playerContext2));
        assertTrue(requirement3.checkRequirement(playerContext1));
        assertTrue(requirement3.checkRequirement(playerContext2));
        assertFalse(requirement4.checkRequirement(playerContext2));
        assertTrue(requirement5.checkRequirement(playerContext1));
        assertFalse(requirement5.checkRequirement(playerContext2));
        assertTrue(requirement6.checkRequirement(playerContext1));
        assertTrue(requirement6.checkRequirement(playerContext2));
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
        assertTrue(requirement6.checkRequirement(playerContext3));
    }

}
