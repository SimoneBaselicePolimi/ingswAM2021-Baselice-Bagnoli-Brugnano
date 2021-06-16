package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "gameActionType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientActivateLeaderCardsActionRepresentation.class, name = "ActivateLeaderCardsAction"),
    @JsonSubTypes.Type(value = ClientDevelopmentActionRepresentation.class, name = "DevelopmentAction"),
    @JsonSubTypes.Type(value = ClientDiscardedResourcesMarketActionRepresentation.class, name = "DiscardedResourcesMarketAction"),
    @JsonSubTypes.Type(value = ClientDiscardLeaderCardsActionRepresentation.class, name = "DiscardLeaderCardsAction"),
    @JsonSubTypes.Type(value = ClientFaithPathLastPositionReachedActionRepresentation.class, name = "FaithPathLastPositionReachedAction"),
    @JsonSubTypes.Type(value = ClientFaithPathMoveActionRepresentation.class, name = "FaithPathMoveAction"),
    @JsonSubTypes.Type(value = ClientFaithPathVaticanReportActionRepresentation.class, name = "FaithPathVaticanReportAction"),
    @JsonSubTypes.Type(value = ClientMainTurnInitialActionRepresentation.class, name = "MainTurnInitialAction"),
    @JsonSubTypes.Type(value = ClientObtainedMarblesMarketActionRepresentation.class, name = "ObtainedMarblesMarketAction"),
    @JsonSubTypes.Type(value = ClientObtainedResourcesMarketActionRepresentation.class, name = "ObtainedResourcesMarketAction"),
    @JsonSubTypes.Type(value = ClientPostTurnFinalActionRepresentation.class, name = "PostTurnFinalAction"),
    @JsonSubTypes.Type(value = ClientProductionActionRepresentation.class, name = "ProductionAction"),
    @JsonSubTypes.Type(value = ClientSetupChoiceActionRepresentation.class, name = "SetupChoiceAction"),
    @JsonSubTypes.Type(value = ClientSetupStartedActionRepresentation.class, name = "SetupStartedAction")
})
public abstract class ClientGameActionRepresentation extends ClientRepresentation {

    public abstract List<FormattedChar> getActionMessage();

}
