package it.polimi.ingsw.server.modelrepresentation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.ServerGameContextRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.*;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.marketrepresentation.ServerMarketRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ServerPlayerContextRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.*;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.*;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ServerCardDeckRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ServerCoveredCardsDeckRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ServerPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerDevelopmentCardColourAndLevelRequirementRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerDevelopmentCardColourRequirementRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "RepresentationType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ServerFaithPathEventRepresentation.class, name = "ServerFaithPathEventRepresentation"),
    @JsonSubTypes.Type(value = ServerFaithPathRepresentation.class, name = "ServerFaithPathRepresentation"),
    @JsonSubTypes.Type(value = ServerFaithPathSinglePlayerRepresentation.class, name = "ServerFaithPathSinglePlayerRepresentation"),
    @JsonSubTypes.Type(value = ServerVaticanReportSectionRepresentation.class, name = "ServerVaticanReportSectionRepresentation"),
    @JsonSubTypes.Type(value = ServerMarketRepresentation.class, name = "ServerMarketRepresentation"),
    @JsonSubTypes.Type(value = ServerPlayerContextRepresentation.class, name = "ServerPlayerContextRepresentation"),
    @JsonSubTypes.Type(value = ServerGameContextRepresentation.class, name = "ServerGameContextRepresentation"),
    @JsonSubTypes.Type(value = ServerGameActionRepresentation.class, name = "ServerGameActionRepresentation"),
    @JsonSubTypes.Type(value = ServerGameHistoryRepresentation.class, name = "ServerGameHistoryRepresentation"),
    @JsonSubTypes.Type(value = ServerCardDeckRepresentation.class, name = "ServerCardDeckRepresentation"),
    @JsonSubTypes.Type(value = ServerRegisteredIdentifiableItemRepresentation.class, name = "ServerRegisteredIdentifiableItemRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardsTableRepresentation.class, name = "ServerDevelopmentCardsTableRepresentation"),
    @JsonSubTypes.Type(value = ServerResourceStorageRuleRepresentation.class, name = "ServerResourceStorageRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerFaithPathLastPositionReachedActionRepresentation.class, name = "ServerFaithPathLastPositionReachedActionRepresentation"),
    @JsonSubTypes.Type(value = ServerFaithPathMoveActionRepresentation.class, name = "ServerFaithPathMoveActionRepresentation"),
    @JsonSubTypes.Type(value = ServerFaithPathVaticanReportActionRepresentation.class, name = "ServerFaithPathVaticanReportActionRepresentation"),
    @JsonSubTypes.Type(value = ServerActivateLeaderCardsActionRepresentation.class, name = "ServerActivateLeaderCardsActionRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentActionRepresentation.class, name = "ServerDevelopmentActionRepresentation"),
    @JsonSubTypes.Type(value = ServerDiscardedResourcesMarketActionRepresentation.class, name = "ServerDiscardedResourcesMarketActionRepresentation"),
    @JsonSubTypes.Type(value = ServerDiscardLeaderCardsActionRepresentation.class, name = "ServerDiscardLeaderCardsActionRepresentation"),
    @JsonSubTypes.Type(value = ServerMainTurnInitialActionRepresentation.class, name = "ServerMainTurnInitialActionRepresentation"),
    @JsonSubTypes.Type(value = ServerObtainedMarblesMarketActionRepresentation.class, name = "ServerObtainedMarblesMarketActionRepresentation"),
    @JsonSubTypes.Type(value = ServerObtainedResourcesMarketActionRepresentation.class, name = "ServerObtainedResourcesMarketActionRepresentation"),
    @JsonSubTypes.Type(value = ServerPostTurnFinalActionRepresentation.class, name = "ServerPostTurnFinalActionRepresentation"),
    @JsonSubTypes.Type(value = ServerProductionActionRepresentation.class, name = "ServerProductionActionRepresentation"),
    @JsonSubTypes.Type(value = ServerSetupChoiceActionRepresentation.class, name = "ServerSetupChoiceActionRepresentation"),
    @JsonSubTypes.Type(value = ServerSetupStartedActionRepresentation.class, name = "ServerSetupStartedActionRepresentation"),
    @JsonSubTypes.Type(value = ServerPlayerOwnedDevelopmentCardDeckRepresentation.class, name = "ServerPlayerOwnedDevelopmentCardDeckRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardColourAndLevelRequirementRepresentation.class, name = "ServerDevelopmentCardColourAndLevelRequirementRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardColourRequirementRepresentation.class, name = "ServerDevelopmentCardColourRequirementRepresentation"),
    @JsonSubTypes.Type(value = ServerResourceNumberRequirementRepresentation.class, name = "ServerResourceNumberRequirementRepresentation"),
    @JsonSubTypes.Type(value = ServerCardDeckRepresentation.class, name = "ServerCardDeckRepresentation"),
    @JsonSubTypes.Type(value = ServerCoveredCardsDeckRepresentation.class, name = "ServerCoveredCardsDeckRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardRepresentation.class, name = "ServerDevelopmentCardRepresentation"),
    @JsonSubTypes.Type(value = ServerLeaderCardRepresentation.class, name = "ServerLeaderCardRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardCostDiscountRepresentation.class, name = "ServerDevelopmentCardCostDiscountRepresentation"),
    @JsonSubTypes.Type(value = ServerMarbleColourRepresentation.class, name = "ServerMarbleColourRepresentation"),
    @JsonSubTypes.Type(value = ServerProductionRepresentation.class, name = "ServerProductionRepresentation"),
    @JsonSubTypes.Type(value = ServerWhiteMarbleSubstitutionRepresentation.class, name = "ServerWhiteMarbleSubstitutionRepresentation"),
    @JsonSubTypes.Type(value = ServerResourceStorageRepresentation.class, name = "ServerResourceStorageRepresentation"),
    @JsonSubTypes.Type(value = ServerPlayerRepresentation.class, name = "ServerPlayerRepresentation"),
    @JsonSubTypes.Type(value = ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation.class, name = "ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerMaxResourceNumberRuleRepresentation.class, name = "ServerMaxResourceNumberRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerSameResourceTypeRuleRepresentation.class, name = "ServerSameResourceTypeRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerSpecificResourceTypeRuleRepresentation.class, name = "ServerSpecificResourceTypeRuleRepresentation"),
})
public abstract class ServerRepresentation {

}
