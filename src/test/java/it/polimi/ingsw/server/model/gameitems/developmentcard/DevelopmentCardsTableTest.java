package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class DevelopmentCardsTableTest {

    @Mock
    Production production1;

    @Mock
    Production production2;

    @Mock
    Production production3;



    //@BeforeEach
    //void setUp() {
    //    when(trueRule1.checkRule(any(), any())).thenReturn(true);
    @Test
    void getAvailableCardsTest (){
        DevelopmentCard developmentCard1 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production1, 3);
        DevelopmentCard developmentCard2 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production1, 2);
        DevelopmentCard developmentCard3 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production2, 5);
        DevelopmentCard developmentCard4 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.GREEN, production1, 2);
        DevelopmentCard developmentCard5 = new DevelopmentCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE, production3, 3);
        DevelopmentCard developmentCard6 = new DevelopmentCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW, production2, 1);
        DevelopmentCard developmentCard7 = new DevelopmentCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW, production3, 1);
        DevelopmentCard developmentCard8 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 1);
        DevelopmentCard developmentCard9 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 2);
        DevelopmentCard developmentCard10 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production2, 3);
        DevelopmentCard developmentCard11 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 4);
        DevelopmentCard developmentCard12 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 4);

        List<DevelopmentCard> developmentCards = new ArrayList<DevelopmentCard>();
        developmentCards.add(developmentCard1);
        developmentCards.add(developmentCard2);
        developmentCards.add(developmentCard3);
        developmentCards.add(developmentCard4);
        developmentCards.add(developmentCard5);
        developmentCards.add(developmentCard6);
        developmentCards.add(developmentCard7);
        developmentCards.add(developmentCard8);
        developmentCards.add(developmentCard9);
        developmentCards.add(developmentCard10);
        developmentCards.add(developmentCard11);
        developmentCards.add(developmentCard12);

        DevelopmentCardsTable table = new DevelopmentCardsTable(developmentCards);
        table.getAvailableCards();

    }
}
