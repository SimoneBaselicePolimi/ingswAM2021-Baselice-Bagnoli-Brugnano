package it.polimi.ingsw.client.gameupdate;


import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.PopeCardsUpdateHandler;
import it.polimi.ingsw.client.model.PlayerRepresentation;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;

import java.util.List;
import java.util.Map;

public class ClientPopeCardsUpdate extends ClientGameUpdate {

	@SerializeAsMapWithIdKey
	public Map<PlayerRepresentation, List<PopeFavorCardState>> faithPopeCardsStates;

	@Override
	public GameUpdateHandler getHandler() {
		return new PopeCardsUpdateHandler();
	}
}
