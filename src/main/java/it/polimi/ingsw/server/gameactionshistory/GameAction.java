package it.polimi.ingsw.server.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.server.gameactionshistory.singleplayer.DiscardCardsSinglePlayerTokenAction;
import it.polimi.ingsw.server.gameactionshistory.singleplayer.FaithPathMoveBlackCrossAction;
import it.polimi.ingsw.server.gameactionshistory.singleplayer.MoveBlackCrossAndShuffleSinglePlayerTokenAction;
import it.polimi.ingsw.server.gameactionshistory.singleplayer.MoveBlackCrossSinglePlayerTokenAction;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "gameActionType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ActivateLeaderCardsAction.class, name = "ActivateLeaderCardsAction"),
    @JsonSubTypes.Type(value = DevelopmentAction.class, name = "DevelopmentAction"),
    @JsonSubTypes.Type(value = DiscardedResourcesMarketAction.class, name = "DiscardedResourcesMarketAction"),
    @JsonSubTypes.Type(value = DiscardLeaderCardsAction.class, name = "DiscardLeaderCardsAction"),
    @JsonSubTypes.Type(value = FaithPathLastPositionReachedAction.class, name = "FaithPathLastPositionReachedAction"),
    @JsonSubTypes.Type(value = FaithPathMoveAction.class, name = "FaithPathMoveAction"),
    @JsonSubTypes.Type(value = FaithPathVaticanReportAction.class, name = "FaithPathVaticanReportAction"),
    @JsonSubTypes.Type(value = MainTurnInitialAction.class, name = "MainTurnInitialAction"),
    @JsonSubTypes.Type(value = ObtainedMarblesMarketAction.class, name = "ObtainedMarblesMarketAction"),
    @JsonSubTypes.Type(value = ObtainedResourcesMarketAction.class, name = "ObtainedResourcesMarketAction"),
    @JsonSubTypes.Type(value = PostTurnFinalAction.class, name = "PostTurnFinalAction"),
    @JsonSubTypes.Type(value = ProductionAction.class, name = "ProductionAction"),
    @JsonSubTypes.Type(value = SetupChoiceAction.class, name = "SetupChoiceAction"),
    @JsonSubTypes.Type(value = SetupStartedAction.class, name = "SetupStartedAction"),
    @JsonSubTypes.Type(value = DiscardCardsSinglePlayerTokenAction.class, name = "DiscardCardsSinglePlayerTokenAction"),
    @JsonSubTypes.Type(value = MoveBlackCrossAndShuffleSinglePlayerTokenAction.class, name = "MoveBlackCrossAndShuffleSinglePlayerTokenAction"),
    @JsonSubTypes.Type(value = MoveBlackCrossSinglePlayerTokenAction.class, name = "MoveBlackCrossSinglePlayerTokenAction"),
    @JsonSubTypes.Type(value = FaithPathMoveBlackCrossAction.class, name = "FaithPathMoveBlackCrossAction")
})
public abstract class GameAction {
}
