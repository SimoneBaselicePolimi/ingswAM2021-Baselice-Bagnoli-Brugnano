package it.polimi.ingsw.server.model.gameitems.cardstack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

abstract class CardStackTest<C> {

    abstract CardStack<C> buildCardStack(List<C> cards);
    abstract List<C> getTestCards();
    abstract C getAnotherTestCard();

    @Test
    void testPushOnTop() {
        CardStack<C> deck = buildCardStack(new ArrayList<>());
        assertThrows(EmptyStackException.class, deck::peek);

        C newCard = getAnotherTestCard();
        deck.pushOnTop(newCard);
        assertEquals(newCard, deck.peek());
    }

    @Test
    void testPop() {
        CardStack<C> deck = buildCardStack(getTestCards());

        C newCard = getAnotherTestCard();
        deck.pushOnTop(newCard);
        assertEquals(newCard, deck.pop());
        while (!deck.isEmpty())
            deck.pop();
        assertThrows(EmptyStackException.class, deck::pop);

        deck.pushOnTop(newCard);
        assertEquals(newCard, deck.peek());
        deck.pop();
        assertTrue(deck.isEmpty());
        assertThrows(EmptyStackException.class, deck::peek);
        assertThrows(EmptyStackException.class, deck::pop);
    }

}