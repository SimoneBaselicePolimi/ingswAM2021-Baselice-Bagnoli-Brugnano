package it.polimi.ingsw.server.model.gameitems.singleplayertokens;

import it.polimi.ingsw.server.gameactionshistory.singleplayer.DiscardCardsSinglePlayerTokenAction;
import it.polimi.ingsw.server.model.gamecontext.SinglePlayerGameContext;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCoveredCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;

import java.util.HashSet;
import java.util.Set;

public class DiscardCardsToken extends SinglePlayerToken {

    public final int numOfCardsToDiscard;
    public final DevelopmentCardColour cardColour;

    public DiscardCardsToken(
        int numOfCardsToDiscard,
        DevelopmentCardColour cardColour
    ) {
        this.numOfCardsToDiscard = numOfCardsToDiscard;
        this.cardColour = cardColour;
    }

    @Override
    public void executeTokenAction(SinglePlayerGameContext singlePlayerGameContext, GameHistory gameHistory) {
        int cardLeftToDiscard = numOfCardsToDiscard;
        DevelopmentCardLevel cardLevel = DevelopmentCardLevel.FIRST_LEVEL;
        Set<DevelopmentCard> discardedCards = new HashSet<>();
        while (cardLeftToDiscard > 0 && cardLevel != null) {
            ShuffledCoveredCardDeck<ServerDevelopmentCardRepresentation, DevelopmentCard> deck =
                singlePlayerGameContext.getDevelopmentCardsTable().getDeckByLevelAndColour(cardLevel, cardColour);
            while (!deck.isEmpty() && cardLeftToDiscard > 0) {
                discardedCards.add(deck.pop());
                cardLeftToDiscard--;
            }
            cardLevel = cardLevel.nextLevel();
        }
        if(cardLeftToDiscard > 0) {
            //TODO SP01 add end of the game
        }
        gameHistory.addAction(new DiscardCardsSinglePlayerTokenAction(cardColour, discardedCards));
    }

}
