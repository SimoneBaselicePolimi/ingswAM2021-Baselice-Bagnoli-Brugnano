package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;

public class ServerMarketUpdate extends ServerGameUpdate {

	public final MarbleColour[][] matrix;

	public final MarbleColour outMarble;

	public ServerMarketUpdate(MarbleColour[][] matrix, MarbleColour outMarble) {
		this.matrix = matrix;
		this.outMarble = outMarble;
	}
}
