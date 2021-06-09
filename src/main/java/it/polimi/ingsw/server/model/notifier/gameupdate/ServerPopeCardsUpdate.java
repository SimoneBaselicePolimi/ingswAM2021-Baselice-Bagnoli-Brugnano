package it.polimi.ingsw.server.model.notifier.gameupdate;


import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;

import java.util.List;
import java.util.Map;

public class ServerPopeCardsUpdate extends ServerGameUpdate {

	@SerializeAsMapWithIdKey
	public final Map<Player, List<PopeFavorCardState>> faithPopeCardsState;

	public ServerPopeCardsUpdate(
		@JsonProperty("faithPopeCardsState") Map<Player, List<PopeFavorCardState>> faithPopeCardsState
	) {
		this.faithPopeCardsState = faithPopeCardsState;
	}
}
