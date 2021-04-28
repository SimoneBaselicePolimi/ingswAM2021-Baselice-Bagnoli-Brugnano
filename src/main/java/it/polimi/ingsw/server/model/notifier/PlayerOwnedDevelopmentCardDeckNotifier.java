package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.EmptyStackException;
import java.util.Set;

public class PlayerOwnedDevelopmentCardDeckNotifier extends PlayerOwnedDevelopmentCardDeck implements Notifier {

	protected PlayerOwnedDevelopmentCardDeckNotifier(String deckID, GameItemsManager gameItemsManager) {
		super(deckID, gameItemsManager);
	}

	public Set<GameUpdate> getUpdates() {
		return null;
	}

	public DevelopmentCard pop() throws EmptyStackException {
		return null;
	}

	public void pushOnTop(DevelopmentCard card) {

	}

}
