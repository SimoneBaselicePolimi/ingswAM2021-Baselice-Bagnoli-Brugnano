package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;

public class MarketUpdate extends GameUpdate {

	public final MarbleColour[][] matrix;

	public final MarbleColour outMarble;

	public MarketUpdate(MarbleColour[][] matrix, MarbleColour outMarble) {
		this.matrix = matrix;
		this.outMarble = outMarble;
	}
}
