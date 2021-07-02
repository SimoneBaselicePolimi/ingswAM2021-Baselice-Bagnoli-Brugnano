package it.polimi.ingsw.server.model.gameitems.cardstack;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

import java.util.EmptyStackException;
import java.util.List;

public interface CardDeck<C extends Representable<?>> extends IdentifiableItem {
    /**
     * Method to test if the Card Deck is empty.
     * @return true if this Card Deck is empty (no elements stored in it), false if there's at least one element in it
     */
    boolean isEmpty();

    /**
     * Method to peek the first Card on the top of the Deck without removing it from the stack.
     * @return Card on top of this Deck
     * @throws EmptyStackException if this Deck is empty
     */
    C peek() throws EmptyStackException;

    /**
     * Method to get all the Cards which are part of the Deck.
     * @return list of all the Cards stored in this Deck
     * @throws EmptyStackException if this Deck is empty
     */
    List<C> peekAll() throws EmptyStackException;

    /**
     * Method to remove the Card from the top of the Deck and get this Card.
     * @return Card which has just been removed from this Deck
     * @throws EmptyStackException if this Deck is empty
     */
    C pop() throws EmptyStackException;

    /**
     * Method to push a Card on the top of the Deck.
     * @param card Card which has to be pushed on the top of this Deck
     * @throws ForbiddenPushOnTopException if the rules imposed by this Deck do not allow this Card to be pushed on the
     * top of this Deck
     */
    void pushOnTop(C card) throws ForbiddenPushOnTopException;
}
