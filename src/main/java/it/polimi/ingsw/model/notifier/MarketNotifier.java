package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.gamecontext.market.Market;
import it.polimi.ingsw.model.gameitems.MarbleColour;
import it.polimi.ingsw.model.notifier.gameupdate.MarketUpdate;

import java.util.Map;
import java.util.Optional;

public class MarketNotifier extends Market implements Notifier<MarketUpdate> {

	public MarketNotifier(int nRows, int nColumns, Map<MarbleColour, Integer> marbles) {
		super(nRows, nColumns, marbles);
	}

	public Optional<MarketUpdate> getUpdate() {
		return null;
	}

	public MarbleColour[] fetchMarbleRow() {
		return null;
	}

	public MarbleColour[] fetchMarbleColumn() {
		return null;
	}

}
