package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.clientrequest.validator.InitialChoicesClientRequestValidator;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;

import java.util.Map;
import java.util.Set;

public class InitialChoicesClientRequest extends ClientRequest {

	@SerializeAsSetOfIds
	public final Set<ClientLeaderCardRepresentation> leaderCardsChosenByThePlayer;

	@SerializeAsMapWithIdKey
	public final Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> chosenResourcesToAddByStorage;

	public InitialChoicesClientRequest(
		Player player,
		Set<ClientLeaderCardRepresentation> leaderCardsChosenByThePlayer,
		Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> chosenResourcesToAddByStorage
	) {
		super(player);
		this.leaderCardsChosenByThePlayer = leaderCardsChosenByThePlayer;
		this.chosenResourcesToAddByStorage = chosenResourcesToAddByStorage;
	}

}
