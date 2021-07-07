package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCoveredCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerShuffledDevelopmentCardDeckOnTableUpdate;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardsTableRepresentation;

import java.util.*;

public class DevelopmentCardsTableObservableProxy extends ObservableProxy<DevelopmentCardsTable> implements DevelopmentCardsTable {

    protected Map<
        ShuffledCoveredCardDeck<ServerDevelopmentCardRepresentation, DevelopmentCard>,
        ServerShuffledDevelopmentCardDeckOnTableUpdate
    > newUpdates = new HashMap<>();


    public DevelopmentCardsTableObservableProxy(DevelopmentCardsTable imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public List<DevelopmentCard> getAvailableCards() {
        return imp.getAvailableCards();
    }

    @Override
    public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour) {
        DevelopmentCard card = imp.popCard(level, colour);
        ServerShuffledDevelopmentCardDeckOnTableUpdate update;
        if(imp.getDeckByLevelAndColour(level, colour).isEmpty()) {
            update = new ServerShuffledDevelopmentCardDeckOnTableUpdate(
                imp.getDeckByLevelAndColour(level, colour),
                null,
                0
            );
        } else {
            update = new ServerShuffledDevelopmentCardDeckOnTableUpdate(
                imp.getDeckByLevelAndColour(level, colour),
                imp.getDeckByLevelAndColour(level, colour).peek(),
                imp.getDeckByLevelAndColour(level, colour).peekAll().size()
            );
        }
        newUpdates.put(imp.getDeckByLevelAndColour(level, colour), update);
        return card;
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
        Set<ServerGameUpdate> updates = new HashSet<>(newUpdates.values());
        newUpdates.clear();
        return updates;
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
