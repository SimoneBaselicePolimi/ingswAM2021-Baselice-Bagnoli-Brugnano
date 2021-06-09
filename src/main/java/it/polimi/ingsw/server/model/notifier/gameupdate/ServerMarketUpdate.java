package it.polimi.ingsw.server.model.notifier.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;

public class ServerMarketUpdate extends ServerGameUpdate {

	public final MarbleColour[][] matrix;

	public final MarbleColour outMarble;

	public ServerMarketUpdate(
		@JsonProperty("matrix") MarbleColour[][] matrix,
		@JsonProperty("outMarble") MarbleColour outMarble
	) {
		this.matrix = matrix;
		this.outMarble = outMarble;
	}
}
