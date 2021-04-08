package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * Tests DevelopmentCardsTable initialization passing to the constructor a list of development cards and,
     * on the other hand, tests if getDeckByLevelAndColour method return the right deck.
     * Checks if the cards are placed in the right deck on the table using getDeckByLevelAndColour and peekAll methods.
     * If the Card Deck doesn't exist, getDeckByLevelAndColour method must return a IllegalArgumentException.
     */
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


        assertEquals(
                Set.of(developmentCard1, developmentCard2, developmentCard3),
                new HashSet<>(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE).peekAll())
        );
        assertEquals(
                Set.of(developmentCard4),
                new HashSet<>(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.GREEN).peekAll())
        );
        assertEquals(
                Set.of(developmentCard5),
                new HashSet<>(table.getDeckByLevelAndColour(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE).peekAll())
        );
        assertEquals(
                Set.of(developmentCard7, developmentCard6),
                new HashSet<>(table.getDeckByLevelAndColour(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW).peekAll())
        );
        assertEquals(
                Set.of(developmentCard8, developmentCard9, developmentCard10, developmentCard11, developmentCard12),
                new HashSet<>(table.getDeckByLevelAndColour(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE).peekAll())
        );
        assertThrows(IllegalArgumentException.class,
                () -> table.getDeckByLevelAndColour(DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.YELLOW));
        }

    /**
     * Tests the method to get all the Cards which are available (on the top of card decks)
     * Checks if the cards on the top of card decks are the same of the ones returned
     * in getAvailableCards method. Also checks that the size of the list which contains available cards is correct.
     */
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
            assertEquals(table.getAvailableCards().size(), 5);
            assertNotEquals(table.getAvailableCards().size(), 4);
        }

    /**
     * Tests the removal of a Card from the top of the Card Deck
     * and checks if the pop method returns the same card which has just been removed.
     * If the Card Deck is empty and the pop method is invoked, it must return a EmptyStackException.
     */
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

            List<DevelopmentCard> list1 = table.getDeckByLevelAndColour(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW).peekAll();
            DevelopmentCard card1 = table.popCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW);
            DevelopmentCard card2 = table.popCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW);
            assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW).isEmpty());
            assertThrows(EmptyStackException.class,
                    () -> table.popCard(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW));

            assertEquals(
                    Set.of(card1, card2),
                    new HashSet<>(list1)
            );
            table.popCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE);
            table.popCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE);
            table.popCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE);
            assertTrue(table.getDeckByLevelAndColour(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE).isEmpty());
        }

        /**
         * Tests the method to get all the Cards which are available (on the top of card decks)
         * Checks if the cards returned by getAvailableCards method are the same of the ones returned
         * in getAvailableCardsAsMap method, even if they are in a list in the first case and in a map in the second.
         */
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

            for (DevelopmentCard card : table.getAvailableCards()) {
                assertEquals(card, table.getAvailableCardsAsMap().get(card.getLevel()).get(card.getColour()));
            }
        }


}
