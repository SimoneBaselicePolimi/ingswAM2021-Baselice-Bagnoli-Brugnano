package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InitialChoicesClientRequest extends ClientRequest {

	@SerializeAsSetOfIds
	public final Set<ClientLeaderCardRepresentation> leaderCardsChosenByThePlayer;

	@SerializeAsMapWithIdKey
	public final Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> chosenResourcesToAddByStorage;

	public InitialChoicesClientRequest(
		@JsonProperty("player") Player player,
		@JsonProperty("leaderCardsChosenByThePlayer") Set<ClientLeaderCardRepresentation> leaderCardsChosenByThePlayer,
		@JsonProperty("chosenResourcesToAddByStorage") Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> chosenResourcesToAddByStorage
	) {
		super(player);
		this.leaderCardsChosenByThePlayer = leaderCardsChosenByThePlayer;
		this.chosenResourcesToAddByStorage = chosenResourcesToAddByStorage == null ? new HashMap<>() : chosenResourcesToAddByStorage;
	}

}
