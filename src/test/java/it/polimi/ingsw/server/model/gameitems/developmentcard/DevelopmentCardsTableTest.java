package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DevelopmentCardsTableTest {

    @Mock
    BiFunction<DevelopmentCardColour, DevelopmentCardLevel, String> getIdForDeckWithColourAndLevel;

    @Mock
    GameItemsManager gameItemsManager;

    @Mock
    Set<Production> production1;

    @Mock
    Set<Production> production2;

    @Mock
    Set<Production> production3;

    @Mock
    Map<ResourceType, Integer> purchaseCost;

    DevelopmentCard developmentCard1, developmentCard2, developmentCard3, developmentCard4,
        developmentCard5, developmentCard6, developmentCard7, developmentCard8, developmentCard9,
        developmentCard10, developmentCard11, developmentCard12;

    List<DevelopmentCard> developmentCards;
    @BeforeEach
    void setUp() {

        developmentCard1 = new DevelopmentCard("testID1", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production1, 3, purchaseCost);
        developmentCard2 = new DevelopmentCard("testID2", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production1, 2, purchaseCost);
        developmentCard3 = new DevelopmentCard("testID3", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production2, 5, purchaseCost);
        developmentCard4 = new DevelopmentCard("testID4", gameItemsManager, DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.GREEN, production1, 2, purchaseCost);
        developmentCard5 = new DevelopmentCard("testID5", gameItemsManager, DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE, production3, 3, purchaseCost);
        developmentCard6 = new DevelopmentCard("testID6", gameItemsManager, DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW, production2, 1, purchaseCost);
        developmentCard7 = new DevelopmentCard("testID7", gameItemsManager, DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW, production3, 1, purchaseCost);
        developmentCard8 = new DevelopmentCard("testID8", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 1, purchaseCost);
        developmentCard9 = new DevelopmentCard("testID9", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 2, purchaseCost);
        developmentCard10 = new DevelopmentCard("testID10", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production2, 3, purchaseCost);
        developmentCard11 = new DevelopmentCard("testID11", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 4, purchaseCost);
        developmentCard12 = new DevelopmentCard("testID12", gameItemsManager, DevelopmentCardLevel.THIRD_LEVEL, DevelopmentCardColour.PURPLE, production1, 4, purchaseCost);

        developmentCards = new ArrayList<DevelopmentCard>();

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
    }

    /**
     * Tests DevelopmentCardsTable initialization passing to the constructor a list of development cards and,
     * on the other hand, tests if getDeckByLevelAndColour method return the right deck.
     * Checks if the cards are placed in the right deck on the table using getDeckByLevelAndColour and peekAll methods.
     * If the Card Deck doesn't exist, getDeckByLevelAndColour method must return a IllegalArgumentException.
     */
    @Test
    void testDevelopmentCardsTableConstructor (){
        DevelopmentCardsTable table = new DevelopmentCardsTableImp(developmentCards, gameItemsManager, getIdForDeckWithColourAndLevel);
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
            DevelopmentCardsTable table = new DevelopmentCardsTableImp(developmentCards, gameItemsManager, getIdForDeckWithColourAndLevel);

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
            DevelopmentCardsTable table = new DevelopmentCardsTableImp(developmentCards, gameItemsManager, getIdForDeckWithColourAndLevel);

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
            DevelopmentCardsTable table = new DevelopmentCardsTableImp(developmentCards, gameItemsManager, getIdForDeckWithColourAndLevel);

            for (DevelopmentCard card : table.getAvailableCards()) {
                assertEquals(card, table.getAvailableCardsAsMap().get(card.getLevel()).get(card.getColour()));
            }
        }

}
