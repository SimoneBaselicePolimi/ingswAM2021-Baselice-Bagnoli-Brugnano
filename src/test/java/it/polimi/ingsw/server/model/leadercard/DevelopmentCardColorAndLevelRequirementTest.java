package it.polimi.ingsw.server.model.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.leadercard.DevelopmentCardColorAndLevelRequirement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class) //Needed to use annotation @Mock

public class DevelopmentCardColorAndLevelRequirementTest {
    @Mock
    PlayerContext playerContext1;

    @Mock
    PlayerContext playerContext2;

    @Mock
    PlayerContext playerContext3;

    @Mock
    Production production;


    @BeforeEach
    void setUp() {
        List<DevelopmentCard> list1 = new ArrayList<DevelopmentCard>();
        List<DevelopmentCard> list2 = new ArrayList<DevelopmentCard>();
        List<DevelopmentCard> emptyList = new ArrayList<DevelopmentCard>();

        DevelopmentCard developmentCard1 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production,3);
        DevelopmentCard developmentCard2 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.GREEN, production,2);
        DevelopmentCard developmentCard3 = new DevelopmentCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE, production,1);
        DevelopmentCard developmentCard4 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production,3);

        DevelopmentCard developmentCard5 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.YELLOW, production,1);
        DevelopmentCard developmentCard6 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.YELLOW, production,5);
        DevelopmentCard developmentCard7 = new DevelopmentCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW, production,2);

        list1.add(developmentCard1);
        list1.add(developmentCard1);
        list1.add(developmentCard1);
        list1.add(developmentCard2);
        list1.add(developmentCard2);
        list1.add(developmentCard3);
        list1.add(developmentCard4);
        list1.add(developmentCard4);
        list1.add(developmentCard4);
        list1.add(developmentCard5); // 3 BLUE FIRST, 2 GREEN FIRST, 1 BLUE SECOND, 3 PURPLE THIRD, 1 YELLOW THIRD

        list2.add(developmentCard6);
        list2.add(developmentCard6);
        list2.add(developmentCard6);
        list2.add(developmentCard6);
        list2.add(developmentCard6);
        list2.add(developmentCard7);
        list2.add(developmentCard7); //5 YELLOW FIRST, 2 YELLOW SECOND

        lenient().when(playerContext1.getAllDevelopmentCards()).thenReturn(list1);
        lenient().when(playerContext2.getAllDevelopmentCards()).thenReturn(list2);
        lenient().when(playerContext3.getAllDevelopmentCards()).thenReturn(emptyList);
    }

    DevelopmentCardColorAndLevelRequirement requirement1 = new DevelopmentCardColorAndLevelRequirement(DevelopmentCardColour.BLUE, DevelopmentCardLevel.FIRST_LEVEL,3);
    DevelopmentCardColorAndLevelRequirement requirement2 = new DevelopmentCardColorAndLevelRequirement(DevelopmentCardColour.BLUE, DevelopmentCardLevel.SECOND_LEVEL,2);
    DevelopmentCardColorAndLevelRequirement requirement3 = new DevelopmentCardColorAndLevelRequirement(DevelopmentCardColour.YELLOW, DevelopmentCardLevel.THIRD_LEVEL,1);
    DevelopmentCardColorAndLevelRequirement requirement4 = new DevelopmentCardColorAndLevelRequirement(DevelopmentCardColour.YELLOW, DevelopmentCardLevel.SECOND_LEVEL,1);
    DevelopmentCardColorAndLevelRequirement requirement5 = new DevelopmentCardColorAndLevelRequirement(DevelopmentCardColour.PURPLE, DevelopmentCardLevel.THIRD_LEVEL,2);
    DevelopmentCardColorAndLevelRequirement requirement6 = new DevelopmentCardColorAndLevelRequirement(DevelopmentCardColour.GREEN, DevelopmentCardLevel.FIRST_LEVEL,3);

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

