package it.polimi.ingsw.server.model.gameitems.cardstack;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ServerPlayerOwnedDevelopmentCardDeckRepresentation;

public interface PlayerOwnedDevelopmentCardDeck extends CardDeck<DevelopmentCard>, Representable<ServerPlayerOwnedDevelopmentCardDeckRepresentation> {

    /**
     * Method to push a Development Card on the top of the Deck if the Level of the Card is suitable for this Deck and
     * if there are no more than three Cards stored in it.
     * @param card Card which has to be pushed on the top of this Deck
     * @throws ForbiddenPushOnTopException if the insertion of the Card passed as parameter do not follow
     * one of the rules imposed by the Development Card Deck
     */
    void pushOnTop(DevelopmentCard card) throws ForbiddenPushOnTopException;

    /**
     * Method to test if the insertion of the Card passed as parameter in this Deck is valid or not, following
     * the rules of the Development Card Deck based on the Levels of Cards.
     * @param card Card which has to be tested before pushing it on the top of the Card Deck
     * @return true if the insertion is valid as the Level of the Card is suitable for this Deck
     * (from the top to the bottom, a Third Level Card on a Second Level Card on a First Level Card), false otherwise
     */
    boolean isPushOnTopValid(DevelopmentCard card);
}
