package it.polimi.ingsw.server.model.gameitems.cardstack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShuffledCardStackTest extends CardStackTest<String>{

    @Override
    CardStack<String> buildCardStack(List<String> cards) {
        return new ShuffledCardStack<>(cards);
    }

    @Override
    List<String> getTestCards() {
        return List.of(
                "card1",
                "card2",
                "card3",
                "card4",
                "card5",
                "card6"
        );
    }

    @Override
    String getAnotherTestCard() {
        return "anotherTestCard";
    }

    @Test
    void testRandomShuffle() {

        ShuffledCardStack<String> deck1 = new ShuffledCardStack<>(new Random(1), getTestCards());
        List<String> listDeck1 = new ArrayList<>();
        while(!deck1.isEmpty())
            listDeck1.add(deck1.pop());

        ShuffledCardStack<String> deck1_copy = new ShuffledCardStack<>(new Random(1), getTestCards());
        List<String> listDeck1_copy = new ArrayList<>();
        while(!deck1_copy.isEmpty())
            listDeck1_copy.add(deck1_copy.pop());

        ShuffledCardStack<String> deck2 = new ShuffledCardStack<>(new Random(2), getTestCards());
        List<String> listDeck2 = new ArrayList<>();
        while(!deck2.isEmpty())
            listDeck2.add(deck1.pop());

        assertEquals(listDeck1, listDeck1_copy);
        assertNotEquals(listDeck1, listDeck2);

    }

}