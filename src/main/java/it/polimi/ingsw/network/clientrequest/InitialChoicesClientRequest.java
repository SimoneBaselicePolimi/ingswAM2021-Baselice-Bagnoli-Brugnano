package it.polimi.ingsw.network.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.clientrequest.validator.InitialChoicesClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.utils.serialization.annotations.*;

import java.util.Map;
import java.util.Set;

public class InitialChoicesClientRequest extends ClientRequest {

	@SerializeAsSetOfIds
	public final Set<LeaderCard> leaderCardsChosenByThePlayer;

	@SerializeAsMapWithIdKey
	public final Map<ResourceStorage, Map<ResourceType, Integer>> chosenResourcesToAddByStorage;

	public InitialChoicesClientRequest(
		@JsonProperty("player") Player player,
		@JsonProperty("leaderCardsChosenByThePlayer") Set<LeaderCard> leaderCardsChosenByThePlayer,
		@JsonProperty("chosenResourcesToAddByStorage") Map<ResourceStorage, Map<ResourceType, Integer>> chosenResourcesToAddByStorage
	) {
		super(player);
		this.leaderCardsChosenByThePlayer = leaderCardsChosenByThePlayer;
		this.chosenResourcesToAddByStorage = chosenResourcesToAddByStorage;
	}

	@SuppressWarnings("unchecked")
	public Map<Player, ServerMessage> callHandler(GameState state) throws ResourceStorageRuleViolationException {
		return(state.handleInitialChoiceCR(this));
	}

	@Override
	public ClientRequestValidator getValidator() {
		return new InitialChoicesClientRequestValidator();
	}

}
