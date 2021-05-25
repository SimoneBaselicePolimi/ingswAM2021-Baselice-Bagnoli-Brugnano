package it.polimi.ingsw.client.gameupdate;


import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.PopeCardsUpdateHandler;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;

import java.util.List;
import java.util.Map;

public class ClientPopeCardsUpdate extends ClientGameUpdate {

	@SerializeAsMapWithIdKey
	public final Map<Player, List<PopeFavorCardState>> faithPopeCardsStates;

	public ClientPopeCardsUpdate(Map<Player, List<PopeFavorCardState>> faithPopeCardsStates) {
		this.faithPopeCardsStates = faithPopeCardsStates;
	}

	@Override
	public GameUpdateHandler getHandler() {
		return new PopeCardsUpdateHandler();
	}
}
