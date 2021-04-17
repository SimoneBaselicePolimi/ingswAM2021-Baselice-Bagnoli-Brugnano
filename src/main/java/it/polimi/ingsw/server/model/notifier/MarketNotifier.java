package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.market.WrongNumberOfMarblesException;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.Map;
import java.util.Set;

public class MarketNotifier extends Market implements Notifier {

	public MarketNotifier(int nRows, int nColumns, Map<MarbleColour, Integer> marbles)
			throws WrongNumberOfMarblesException {
		super(nRows, nColumns, marbles);
	}

	public Set<GameUpdate> getUpdates() {
		return null;
	}

	public MarbleColour[] fetchMarbleRow() {
		return null;
	}

	public MarbleColour[] fetchMarbleColumn() {
		return null;
	}

}
