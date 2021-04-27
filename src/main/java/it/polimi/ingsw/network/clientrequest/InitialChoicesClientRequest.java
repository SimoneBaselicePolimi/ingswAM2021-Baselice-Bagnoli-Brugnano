package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;


import java.util.Map;
import java.util.Set;

public class InitialChoicesClientRequest extends ClientRequest {

	@SerializeAsSetOfIds
	public final Set<LeaderCard> leaderCardsChosenByThePlayer;

	@SerializeAsMapWithIdKey
	public final Map<ResourceStorage, Map<ResourceType, Integer>> chosenResourcesToAdd;

	public InitialChoicesClientRequest(
		Player player,
		Set<LeaderCard> leaderCardsChosenByThePlayer,
		Map<ResourceStorage, Map<ResourceType, Integer>> chosenResourcesToAdd
	) {
		super(player);
		this.leaderCardsChosenByThePlayer = leaderCardsChosenByThePlayer;
		this.chosenResourcesToAdd = chosenResourcesToAdd;
	}

	@SuppressWarnings("unchecked")
	public Map<Player, ServerMessage> callHandler(GameState state) throws ResourceStorageRuleViolationException {
		return(state.handleInitialChoiceCR(this));
	}

}
