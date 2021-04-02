package it.polimi.ingsw.server.model.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.leadercard.DevelopmentCardColorRequirement;
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
public class DevelopmentCardColorRequirementTest {
    @Mock
        PlayerContext playerContext1;

    @Mock
        PlayerContext playerContext2;

    @Mock
        PlayerContext playerContext3;


    @BeforeEach
        void setUp() {
        List<DevelopmentCard> list1 = new ArrayList<DevelopmentCard>();
        List<DevelopmentCard> list2 = new ArrayList<DevelopmentCard>();
        List<DevelopmentCard> emptyList = new ArrayList<DevelopmentCard>();

        DevelopmentCard developmentCard1 = new DevelopmentCard(any(), DevelopmentCardColour.BLUE, any(),3);
        DevelopmentCard developmentCard2 = new DevelopmentCard(any(), DevelopmentCardColour.GREEN, any(),2);
        DevelopmentCard developmentCard3 = new DevelopmentCard(any(), DevelopmentCardColour.BLUE, any(),1);
        DevelopmentCard developmentCard4 = new DevelopmentCard(any(), DevelopmentCardColour.PURPLE, any(),3);

        DevelopmentCard developmentCard5 = new DevelopmentCard(any(), DevelopmentCardColour.YELLOW, any(),1);
        DevelopmentCard developmentCard6 = new DevelopmentCard(any(), DevelopmentCardColour.YELLOW, any(),5);
        DevelopmentCard developmentCard7 = new DevelopmentCard(any(), DevelopmentCardColour.YELLOW, any(),2);

        list1.add(developmentCard1);
        list1.add(developmentCard2);
        list1.add(developmentCard3);
        list1.add(developmentCard4);
        list1.add(developmentCard5); // 2 BLUE, 1 GREEN, 1 PURPLE, 1 YELLOW

        list2.add(developmentCard5);
        list2.add(developmentCard6);
        list2.add(developmentCard7); //3 YELLOW

        lenient().when(playerContext1.getAllDevelopmentCards()).thenReturn(list1);
        lenient().when(playerContext2.getAllDevelopmentCards()).thenReturn(list2);
        lenient().when(playerContext3.getAllDevelopmentCards()).thenReturn(emptyList);
        }

        DevelopmentCardColorRequirement requirement1 = new DevelopmentCardColorRequirement(DevelopmentCardColour.BLUE, 3);
        DevelopmentCardColorRequirement requirement2 = new DevelopmentCardColorRequirement(DevelopmentCardColour.BLUE, 1);
        DevelopmentCardColorRequirement requirement3 = new DevelopmentCardColorRequirement(DevelopmentCardColour.YELLOW, 1);
        DevelopmentCardColorRequirement requirement4 = new DevelopmentCardColorRequirement(DevelopmentCardColour.YELLOW, 4);
        DevelopmentCardColorRequirement requirement5 = new DevelopmentCardColorRequirement(DevelopmentCardColour.PURPLE, 1);
        DevelopmentCardColorRequirement requirement6 = new DevelopmentCardColorRequirement(DevelopmentCardColour.GREEN, 0);

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
