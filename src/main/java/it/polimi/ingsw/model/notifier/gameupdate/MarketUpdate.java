package it.polimi.ingsw.model.notifier.gameupdate;

import it.polimi.ingsw.model.gameitems.MarbleColour;

public class MarketUpdate extends GameUpdate {

	public final MarbleColour[][] matrix;

	public final MarbleColour outMarble;

	public MarketUpdate(MarbleColour[][] matrix, MarbleColour outMarble) {
		this.matrix = matrix;
		this.outMarble = outMarble;
	}
}
