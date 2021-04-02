package it.polimi.ingsw.server.model.gameitems.cardstack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class CardDeckTest<C> {

    abstract CardDeck<C> buildCardStack(List<C> cards);
    abstract List<C> getTestCards();
    abstract C getAnotherTestCard();

    @Test
    void testPushOnTop() throws ForbiddenPushOnTopException{
        CardDeck<C> deck = buildCardStack(new ArrayList<>());
        assertThrows(EmptyStackException.class, deck::peek);

        C newCard = getAnotherTestCard();
        deck.pushOnTop(newCard);
        assertEquals(newCard, deck.peek());
    }

    @Test
    void testPop() throws ForbiddenPushOnTopException{
        CardDeck<C> deck = buildCardStack(getTestCards());

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