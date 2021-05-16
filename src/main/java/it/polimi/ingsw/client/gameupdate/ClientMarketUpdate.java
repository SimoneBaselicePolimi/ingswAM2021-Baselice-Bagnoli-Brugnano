package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.MarketUpdateHandler;
import it.polimi.ingsw.client.model.MarbleColourRepresentation;

public class ClientMarketUpdate extends ClientGameUpdate {

	public final MarbleColourRepresentation[][] matrix;

	public final MarbleColourRepresentation outMarble;

	public ClientMarketUpdate(MarbleColourRepresentation[][] matrix, MarbleColourRepresentation outMarble) {
		this.matrix = matrix;
		this.outMarble = outMarble;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new MarketUpdateHandler();
	}
}
