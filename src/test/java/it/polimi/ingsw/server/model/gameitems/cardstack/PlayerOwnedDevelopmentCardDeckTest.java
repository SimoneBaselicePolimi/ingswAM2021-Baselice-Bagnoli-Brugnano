package it.polimi.ingsw.server.model.gameitems.cardstack;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import static org.mockito.Mockito.lenient;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class PlayerOwnedDevelopmentCardDeckTest {

    @Mock
    DevelopmentCard card1;
    @Mock
    DevelopmentCard card2;
    @Mock
    DevelopmentCard card3;
    @Mock
    DevelopmentCard oneMoreTestCard;

    List<DevelopmentCard> testCardsInOrder;
    List<DevelopmentCard> testCardsNotInOrder;

    @BeforeEach
    void setUp() {
        lenient().when(card1.getLevel()).thenReturn(DevelopmentCardLevel.FIRST_LEVEL);
        lenient().when(card2.getLevel()).thenReturn(DevelopmentCardLevel.SECOND_LEVEL);
        lenient().when(card3.getLevel()).thenReturn(DevelopmentCardLevel.THIRD_LEVEL);
        lenient().when(oneMoreTestCard.getLevel()).thenReturn(DevelopmentCardLevel.FIRST_LEVEL);

        testCardsInOrder = new ArrayList<>();
        testCardsInOrder.add(card1);
        testCardsInOrder.add(card2);
        testCardsInOrder.add(card3);

        testCardsNotInOrder = new ArrayList<>();
        testCardsNotInOrder.add(card3);
        testCardsNotInOrder.add(card1);
        testCardsNotInOrder.add(card2);
    }

    @Test
    void testInitializeDevelopmentCardDeck() {
        PlayerOwnedDevelopmentCardDeck deck1 = new PlayerOwnedDevelopmentCardDeck(testCardsInOrder);
        List<DevelopmentCard> listDeckInOrder = new ArrayList<>();
        while(!deck1.isEmpty())
            listDeckInOrder.add(deck1.pop());

        PlayerOwnedDevelopmentCardDeck deck1Copy = new PlayerOwnedDevelopmentCardDeck(testCardsInOrder);
        List<DevelopmentCard> listDeckInOrderCopy = new ArrayList<>();
        while(!deck1Copy.isEmpty())
            listDeckInOrderCopy.add(deck1Copy.pop());

        PlayerOwnedDevelopmentCardDeck deck2 = new PlayerOwnedDevelopmentCardDeck(testCardsNotInOrder);
        List<DevelopmentCard> listDeckNotInOrder = new ArrayList<>();
        while(!deck2.isEmpty())
            listDeckNotInOrder.add(deck2.pop());

        assertEquals(listDeckInOrder, listDeckInOrderCopy);
        assertNotEquals(listDeckInOrder, listDeckNotInOrder);
    }

    @Test
    void testPushOnTop() throws ForbiddenPushOnTopException{
        PlayerOwnedDevelopmentCardDeck deck = new PlayerOwnedDevelopmentCardDeck(new ArrayList());
        assertThrows(EmptyStackException.class, deck::peek);

        assertThrows(ForbiddenPushOnTopException.class, () -> deck.pushOnTop(card3));
        assertThrows(ForbiddenPushOnTopException.class, () -> deck.pushOnTop(card2));

        assertThrows(EmptyStackException.class, deck::peek);

        deck.pushOnTop(card1);
        assertEquals(card1, deck.peek());
        assertThrows(ForbiddenPushOnTopException.class, () -> deck.pushOnTop(card3));
        assertEquals(card1, deck.peek());

        deck.pushOnTop(card2);
        assertEquals(card2, deck.peek());

        deck.pushOnTop(card3);
        assertEquals(card3, deck.peek());

        assertThrows(ForbiddenPushOnTopException.class, () -> deck.pushOnTop(card3));
    }

    @Test
    void testPop() throws ForbiddenPushOnTopException{
        PlayerOwnedDevelopmentCardDeck deck = new PlayerOwnedDevelopmentCardDeck(testCardsInOrder);

        while (!deck.isEmpty())
            deck.pop();
        assertThrows(EmptyStackException.class, deck::pop);

        deck.pushOnTop(oneMoreTestCard);
        assertEquals(oneMoreTestCard, deck.pop());

        deck.pushOnTop(oneMoreTestCard);
        assertEquals(oneMoreTestCard, deck.peek());
        deck.pop();
        assertTrue(deck.isEmpty());
        assertThrows(EmptyStackException.class, deck::peek);
        assertThrows(EmptyStackException.class, deck::pop);
    }

}
