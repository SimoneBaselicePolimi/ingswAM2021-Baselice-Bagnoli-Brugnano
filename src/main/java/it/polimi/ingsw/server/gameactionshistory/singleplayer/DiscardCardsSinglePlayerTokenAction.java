package it.polimi.ingsw.server.gameactionshistory.singleplayer;

import it.polimi.ingsw.server.gameactionshistory.GameAction;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//TODO SP01 implement client side
public class DiscardCardsSinglePlayerTokenAction extends GameAction {

    public final DevelopmentCardColour cardColour;
    public final Map<DevelopmentCardLevel, Long> cardDiscardedByLevel;

    public DiscardCardsSinglePlayerTokenAction(
        DevelopmentCardColour cardColour,
        Set<DevelopmentCard> cardsDiscarded
    ) {
        this.cardColour = cardColour;
        this.cardDiscardedByLevel = cardsDiscarded.stream()
            .collect(Collectors.groupingBy(DevelopmentCard::getLevel, Collectors.counting()));
    }

}
