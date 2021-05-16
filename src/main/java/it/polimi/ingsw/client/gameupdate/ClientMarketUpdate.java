package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.MarketUpdateHandler;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;

public class ClientMarketUpdate extends ClientGameUpdate {

	public final MarbleColour[][] matrix;

	public final MarbleColour outMarble;

	public ClientMarketUpdate(MarbleColour[][] matrix, MarbleColour outMarble) {
		this.matrix = matrix;
		this.outMarble = outMarble;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new MarketUpdateHandler();
	}
}
