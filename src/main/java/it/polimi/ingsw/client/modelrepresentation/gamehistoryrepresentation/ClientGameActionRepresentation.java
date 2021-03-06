package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.singleplayer.ClientDiscardCardsSinglePlayerTokenActionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.singleplayer.ClientFaithPathMoveBlackCrossActionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.singleplayer.ClientMoveBlackCrossAndShuffleSinglePlayerTokenActionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.singleplayer.ClientMoveBlackCrossSinglePlayerTokenActionRepresentation;

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
    @JsonSubTypes.Type(value = ClientSetupStartedActionRepresentation.class, name = "SetupStartedAction"),
    @JsonSubTypes.Type(value = ClientDiscardCardsSinglePlayerTokenActionRepresentation.class, name = "DiscardCardsSinglePlayerTokenAction"),
    @JsonSubTypes.Type(value = ClientMoveBlackCrossAndShuffleSinglePlayerTokenActionRepresentation.class, name = "MoveBlackCrossAndShuffleSinglePlayerTokenAction"),
    @JsonSubTypes.Type(value = ClientMoveBlackCrossSinglePlayerTokenActionRepresentation.class, name = "MoveBlackCrossSinglePlayerTokenAction"),
    @JsonSubTypes.Type(value = ClientFaithPathMoveBlackCrossActionRepresentation.class, name = "FaithPathMoveBlackCrossAction")
})
public abstract class ClientGameActionRepresentation extends ClientRepresentation {

    @JsonIgnore
    public abstract String getActionMessage();

    @JsonIgnore
    public List<FormattedChar> getActionMessageForCli() {
        return FormattedChar.convertStringToFormattedCharList(getActionMessage());
    }

}
