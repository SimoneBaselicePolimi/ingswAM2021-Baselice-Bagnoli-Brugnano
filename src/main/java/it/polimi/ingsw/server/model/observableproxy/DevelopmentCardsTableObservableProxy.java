package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCoveredCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerShuffledDevelopmentCardDeckOnTableUpdate;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardsTableRepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DevelopmentCardsTableObservableProxy extends ObservableProxy<DevelopmentCardsTable> implements DevelopmentCardsTable {

    protected boolean hasSomethingChanged = false;
    protected DevelopmentCardLevel level;
    protected DevelopmentCardColour colour;

    public DevelopmentCardsTableObservableProxy(DevelopmentCardsTable imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public List<DevelopmentCard> getAvailableCards() {
        return imp.getAvailableCards();
    }

    @Override
    public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour) {
        hasSomethingChanged = true;
        this.level = level;
        this.colour = colour;
        return imp.popCard(level, colour);
    }

    @Override
    public Map<DevelopmentCardLevel, Map<DevelopmentCardColour, DevelopmentCard>> getAvailableCardsAsMap() {
        return imp.getAvailableCardsAsMap();
    }

    @Override
    public ShuffledCoveredCardDeck<ServerDevelopmentCardRepresentation, DevelopmentCard> getDeckByLevelAndColour(DevelopmentCardLevel cardLevel, DevelopmentCardColour cardColour) throws IllegalArgumentException {
        return imp.getDeckByLevelAndColour(cardLevel, cardColour);
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        if(hasSomethingChanged) {
            hasSomethingChanged = false;
            return Set.of(new ServerShuffledDevelopmentCardDeckOnTableUpdate(
                imp.getDeckByLevelAndColour(level, colour),
                imp.getDeckByLevelAndColour(level, colour).peek(),
                imp.getDeckByLevelAndColour(level, colour).peekAll().size()
                )
            );
        }
        else
            return new HashSet<>();
    }

    @Override
    public ServerDevelopmentCardsTableRepresentation getServerRepresentation() {
        return imp.getServerRepresentation();
    }

    @Override
    public ServerDevelopmentCardsTableRepresentation getServerRepresentationForPlayer(Player player) {
        return imp.getServerRepresentationForPlayer(player);
    }

}
