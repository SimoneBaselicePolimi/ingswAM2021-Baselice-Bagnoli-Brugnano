package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.gamecontext.market.Market;
import it.polimi.ingsw.model.MarbleColour[ ];

public class MarketNotifier extends Market implements Notifier {

	public Optional<MarketUpdate> getUpdate() {
		return null;
	}

	public MarbleColour[ ] fetchMarbleRow() {
		return null;
	}

	public MarbleColour[ ] fetchMarbleColumn() {
		return null;
	}


	/**
	 * @see Notifier#getUpdate()
	 */
	public Optional<GameUpdate> getUpdate() {
		return null;
	}

}
