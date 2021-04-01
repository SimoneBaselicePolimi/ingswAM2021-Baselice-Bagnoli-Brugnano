package it.polimi.ingsw.server.model.gameitems.cardstack;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShuffledCardDeckTest {

    List<String> testCards = List.of(
            "card1",
            "card2",
            "card3",
            "card4",
            "card5",
            "card6"
    );

    String oneMoreTestCard = "anotherCard";

    @Test
    void testRandomShuffle() {

        ShuffledCardDeck<String> deck1 = new ShuffledCardDeck<>(new Random(1), testCards);
        List<String> listDeck1 = new ArrayList<>();
        while(!deck1.isEmpty())
            listDeck1.add(deck1.pop());

        ShuffledCardDeck<String> deck1_copy = new ShuffledCardDeck<>(new Random(1), testCards);
        List<String> listDeck1_copy = new ArrayList<>();
        while(!deck1_copy.isEmpty())
            listDeck1_copy.add(deck1_copy.pop());

        ShuffledCardDeck<String> deck2 = new ShuffledCardDeck<>(new Random(2), testCards);
        List<String> listDeck2 = new ArrayList<>();
        while(!deck2.isEmpty())
            listDeck2.add(deck2.pop());

        assertEquals(listDeck1, listDeck1_copy);
        assertNotEquals(listDeck1, listDeck2);

    }

    @Test
    void testPushOnTop() throws ForbiddenPushOnTopException{
        ShuffledCardDeck<String> deck = new ShuffledCardDeck<>(new ArrayList<>());
        assertThrows(EmptyStackException.class, deck::peek);

        deck.pushOnTop(oneMoreTestCard);
        assertEquals(oneMoreTestCard, deck.peek());
    }

    @Test
    void testPop() throws ForbiddenPushOnTopException{
        ShuffledCardDeck<String> deck = new ShuffledCardDeck<>(testCards);

        deck.pushOnTop(oneMoreTestCard);
        assertEquals(oneMoreTestCard, deck.pop());
        while (!deck.isEmpty())
            deck.pop();
        assertThrows(EmptyStackException.class, deck::pop);

        deck.pushOnTop(oneMoreTestCard);
        assertEquals(oneMoreTestCard, deck.peek());
        deck.pop();
        assertTrue(deck.isEmpty());
        assertThrows(EmptyStackException.class, deck::peek);
        assertThrows(EmptyStackException.class, deck::pop);
    }

}