package it.polimi.ingsw.server.model.gameupdate;


import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;

import java.util.List;
import java.util.Map;

public class ServerPopeCardsUpdate extends ServerGameUpdate {

	@SerializeAsMapWithIdKey
	public final Map<Player, List<PopeFavorCardState>> faithPopeCardsStates;

	public ServerPopeCardsUpdate(
		@JsonProperty("faithPopeCardsStates") Map<Player, List<PopeFavorCardState>> faithPopeCardsStates
	) {
		this.faithPopeCardsStates = faithPopeCardsStates;
	}
}