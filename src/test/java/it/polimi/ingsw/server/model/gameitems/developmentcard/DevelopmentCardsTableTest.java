package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;
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

    @Mock
    Map<ResourceType, Integer> purchaseCost;

        DevelopmentCard developmentCard1 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production1, 3, purchaseCost);
        DevelopmentCard developmentCard2 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production1, 2, purchaseCost);
        DevelopmentCard developmentCard3 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production2, 5, purchaseCost);
        DevelopmentCard developmentCard4 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.GREEN, production1, 2, purchaseCost);
        DevelopmentCard developmentCard5 = new DevelopmentCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE, production3, 3, purchaseCost);
        DevelopmentCard developmentCard6 = new DevelopmentCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW, production2, 1, purchaseCost);
        DevelopmentCard developmentCard7 = new DevelopmentCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW, production3, 1, purchaseCost);
        DevelopmentCard developmentCard8 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 1, purchaseCost);
        DevelopmentCard developmentCard9 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 2, purchaseCost);
        DevelopmentCard developmentCard10 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production2, 3, purchaseCost);
        DevelopmentCard developmentCard11 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 4, purchaseCost);
        DevelopmentCard developmentCard12 = new DevelopmentCard(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 4, purchaseCost);


    @Test
    void testDevelopmentCardsTableConstructor (){

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

        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE).peekAll().contains(developmentCard1));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE).peekAll().contains(developmentCard2));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE).peekAll().contains(developmentCard3));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.GREEN).peekAll().contains(developmentCard4));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE).peekAll().contains(developmentCard5));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW).peekAll().contains(developmentCard6));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW).peekAll().contains(developmentCard7));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE).peekAll().contains(developmentCard8));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE).peekAll().contains(developmentCard9));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE).peekAll().contains(developmentCard10));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE).peekAll().contains(developmentCard11));
        assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE).peekAll().contains(developmentCard12));
        assertFalse(table.getDeckByLevelAndColour(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE).peekAll().contains(developmentCard2));
        assertFalse(table.getDeckByLevelAndColour(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE).peekAll().contains(developmentCard1));
        assertFalse(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.GREEN).peekAll().contains(developmentCard3));
        //assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.YELLOW).isEmpty());
        }

        @Test
        void testGetAvailableCards(){

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


            assertTrue(table.getAvailableCards().contains(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL,DevelopmentCardColour.BLUE).peek()));
            assertTrue(table.getAvailableCards().contains(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL,DevelopmentCardColour.GREEN).peek()));
            assertTrue(table.getAvailableCards().contains(table.getDeckByLevelAndColour(DevelopmentCardLevel.THIRD_LEVEL,DevelopmentCardColour.PURPLE).peek()));
            //assertFalse(table.getAvailableCards().contains(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL,DevelopmentCardColour.YELLOW).peek()));
            assertEquals(table.getAvailableCards().size(), 5);
            assertNotEquals(table.getAvailableCards().size(), 4);
        }

        @Test
        void testPopCard (){
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

            assertEquals(table.popCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE), table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE).pop());

        }

        @Test
        void testGetAvailableCardsAsMap(){
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

            assertTrue(table.getAvailableCards().contains(table.getAvailableCardsAsMap().get(DevelopmentCardLevel.FIRST_LEVEL).get(DevelopmentCardColour.BLUE)));
            assertTrue(table.getAvailableCards().contains(table.getAvailableCardsAsMap().get(DevelopmentCardLevel.FIRST_LEVEL).get(DevelopmentCardColour.GREEN)));
            assertTrue(table.getAvailableCards().contains(table.getAvailableCardsAsMap().get(DevelopmentCardLevel.THIRD_LEVEL).get(DevelopmentCardColour.PURPLE)));
            assertEquals(table.getAvailableCards().size(), table.getAvailableCardsAsMap().size());
        }

}
