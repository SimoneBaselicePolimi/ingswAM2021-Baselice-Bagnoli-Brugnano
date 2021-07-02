package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "clientRequestType")
@JsonSubTypes({
	@JsonSubTypes.Type(value = ActivateLeaderCardClientRequest.class, name = "ActivateLeaderCardClientRequest"),
	@JsonSubTypes.Type(value = CustomClientRequest.class, name = "CustomClientRequest"),
	@JsonSubTypes.Type(value = DevelopmentActionClientRequest.class, name = "DevelopmentActionClientRequest"),
	@JsonSubTypes.Type(value = DiscardLeaderCardClientRequest.class, name = "DiscardLeaderCardClientRequest"),
	@JsonSubTypes.Type(value = EndTurnClientRequest.class, name = "EndTurnClientRequest"),
	@JsonSubTypes.Type(value = InitialChoicesClientRequest.class, name = "InitialChoicesClientRequest"),
	@JsonSubTypes.Type(value = ManageResourcesFromMarketClientRequest.class, name = "ManageResourcesFromMarketClientRequest"),
	@JsonSubTypes.Type(value = MarketActionFetchColumnClientRequest.class, name = "MarketActionFetchColumnClientRequest"),
	@JsonSubTypes.Type(value = MarketActionFetchRowClientRequest.class, name = "MarketActionFetchRowClientRequest"),
	@JsonSubTypes.Type(value = ProductionActionClientRequest.class, name = "ProductionActionClientRequest")
})
public abstract class ClientRequest {

	@SerializeIdOnly
	public final Player player;

	public ClientRequest(
		@JsonProperty("player") Player player
	) {
		this.player = player;
	}

}
