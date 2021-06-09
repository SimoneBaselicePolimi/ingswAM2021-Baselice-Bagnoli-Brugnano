package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

//TODO rename ClientRequest in PlayerRequest
public abstract class ClientRequest {

	@SerializeIdOnly
	public final Player player;

	public ClientRequest(@JsonProperty("player") Player player) {
		this.player = player;
	}

}
