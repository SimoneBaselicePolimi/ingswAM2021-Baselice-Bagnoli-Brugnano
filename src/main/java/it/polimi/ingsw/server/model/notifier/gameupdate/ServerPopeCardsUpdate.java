package it.polimi.ingsw.server.model.notifier.gameupdate;


import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;

import java.util.List;
import java.util.Map;

public class ServerPopeCardsUpdate extends ServerGameUpdate {

	@SerializeAsMapWithIdKey
	public Map<Player, List<PopeFavorCardState>> faithPopeCards;

}
