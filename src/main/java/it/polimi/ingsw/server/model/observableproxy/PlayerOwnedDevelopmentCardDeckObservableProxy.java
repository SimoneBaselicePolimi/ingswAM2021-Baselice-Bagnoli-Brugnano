package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerPlayerOwnedDevelopmentCardDeckUpdate;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ServerPlayerOwnedDevelopmentCardDeckRepresentation;

import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerOwnedDevelopmentCardDeckObservableProxy extends ObservableProxy<PlayerOwnedDevelopmentCardDeck> implements PlayerOwnedDevelopmentCardDeck {

    protected boolean hasSomethingChanged = false;

    public PlayerOwnedDevelopmentCardDeckObservableProxy(PlayerOwnedDevelopmentCardDeck imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public ServerPlayerOwnedDevelopmentCardDeckRepresentation getServerRepresentation() {
        return imp.getServerRepresentation();
    }

    @Override
    public ServerPlayerOwnedDevelopmentCardDeckRepresentation getServerRepresentationForPlayer(Player player) {
        return imp.getServerRepresentationForPlayer(player);
    }

    @Override
    public String getItemID() {
        return imp.getItemID();
    }

    @Override
    public boolean isEmpty() {
        return imp.isEmpty();
    }

    @Override
    public DevelopmentCard peek() throws EmptyStackException {
        return imp.peek();
    }

    @Override
    public List<DevelopmentCard> peekAll() throws EmptyStackException {
        return imp.peekAll();
    }

    @Override
    public DevelopmentCard pop() throws EmptyStackException {
        hasSomethingChanged = true;
        return imp.pop();
    }

    @Override
    public void pushOnTop(DevelopmentCard card) throws ForbiddenPushOnTopException {
        hasSomethingChanged = true;
        imp.pushOnTop(card);
    }

    @Override
    public boolean isPushOnTopValid(DevelopmentCard card) {
        return imp.isPushOnTopValid(card);
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        if (hasSomethingChanged) {
            hasSomethingChanged = false;
            return Set.of(new ServerPlayerOwnedDevelopmentCardDeckUpdate(
                this,
                imp.peekAll())
            );
        }
        else
            return new HashSet<>();
    }
}
