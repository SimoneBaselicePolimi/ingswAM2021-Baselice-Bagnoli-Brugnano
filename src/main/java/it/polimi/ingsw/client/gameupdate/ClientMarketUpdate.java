package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.MarketUpdateHandler;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;

public class ClientMarketUpdate extends ClientGameUpdate {

	public final ClientMarbleColourRepresentation[][] matrix;

	public final ClientMarbleColourRepresentation outMarble;

	public ClientMarketUpdate(ClientMarbleColourRepresentation[][] matrix, ClientMarbleColourRepresentation outMarble) {
		this.matrix = matrix;
		this.outMarble = outMarble;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new MarketUpdateHandler();
	}
}
