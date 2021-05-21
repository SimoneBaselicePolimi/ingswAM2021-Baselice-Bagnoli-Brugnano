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
    @JsonSubTypes.Type(value = ServerFaithPathEventRepresentation.class, name = "ClientFaithPathEventRepresentation"),
    @JsonSubTypes.Type(value = ServerFaithPathRepresentation.class, name = "ClientFaithPathRepresentation"),
    @JsonSubTypes.Type(value = ServerFaithPathSinglePlayerRepresentation.class, name = "ClientFaithPathSinglePlayerRepresentation"),
    @JsonSubTypes.Type(value = ServerVaticanReportSectionRepresentation.class, name = "ClientVaticanReportSectionRepresentation"),
    @JsonSubTypes.Type(value = ServerMarketRepresentation.class, name = "ClientMarketRepresentation"),
    @JsonSubTypes.Type(value = ServerPlayerContextRepresentation.class, name = "ClientPlayerContextRepresentation"),
    @JsonSubTypes.Type(value = ServerGameContextRepresentation.class, name = "ClientGameContextRepresentation"),
    @JsonSubTypes.Type(value = ServerGameActionRepresentation.class, name = "ClientGameActionRepresentation"),
    @JsonSubTypes.Type(value = ServerGameHistoryRepresentation.class, name = "ClientGameHistoryRepresentation"),
    @JsonSubTypes.Type(value = ServerCardDeckRepresentation.class, name = "ClientCardDeckRepresentation"),
    @JsonSubTypes.Type(value = ServerRegisteredIdentifiableItemRepresentation.class, name = "ClientRegisteredIdentifiableItemRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardsTableRepresentation.class, name = "ClientDevelopmentCardsTableRepresentation"),
    @JsonSubTypes.Type(value = ServerResourceStorageRuleRepresentation.class, name = "ClientResourceStorageRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerFaithPathLastPositionReachedActionRepresentation.class, name = "ClientFaithPathLastPositionReachedActionRepresentation"),
    @JsonSubTypes.Type(value = ServerFaithPathMoveActionRepresentation.class, name = "ClientFaithPathMoveActionRepresentation"),
    @JsonSubTypes.Type(value = ServerFaithPathVaticanReportActionRepresentation.class, name = "ClientFaithPathVaticanReportActionRepresentation"),
    @JsonSubTypes.Type(value = ServerActivateLeaderCardsActionRepresentation.class, name = "ClientActivateLeaderCardsActionRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentActionRepresentation.class, name = "ClientDevelopmentActionRepresentation"),
    @JsonSubTypes.Type(value = ServerDiscardedResourcesMarketActionRepresentation.class, name = "ClientDiscardedResourcesMarketActionRepresentation"),
    @JsonSubTypes.Type(value = ServerDiscardLeaderCardsActionRepresentation.class, name = "ClientDiscardLeaderCardsActionRepresentation"),
    @JsonSubTypes.Type(value = ServerMainTurnInitialActionRepresentation.class, name = "ClientMainTurnInitialActionRepresentation"),
    @JsonSubTypes.Type(value = ServerObtainedMarblesMarketActionRepresentation.class, name = "ClientObtainedMarblesMarketActionRepresentation"),
    @JsonSubTypes.Type(value = ServerObtainedResourcesMarketActionRepresentation.class, name = "ClientObtainedResourcesMarketActionRepresentation"),
    @JsonSubTypes.Type(value = ServerPostTurnFinalActionRepresentation.class, name = "ClientPostTurnFinalActionRepresentation"),
    @JsonSubTypes.Type(value = ServerProductionActionRepresentation.class, name = "ClientProductionActionRepresentation"),
    @JsonSubTypes.Type(value = ServerSetupChoiceActionRepresentation.class, name = "ClientSetupChoiceActionRepresentation"),
    @JsonSubTypes.Type(value = ServerSetupStartedActionRepresentation.class, name = "ClientSetupStartedActionRepresentation"),
    @JsonSubTypes.Type(value = ServerPlayerOwnedDevelopmentCardDeckRepresentation.class, name = "ClientPlayerOwnedDevelopmentCardDeckRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardColourAndLevelRequirementRepresentation.class, name = "ClientDevelopmentCardColourAndLevelRequirementRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardColourRequirementRepresentation.class, name = "ClientDevelopmentCardColourRequirementRepresentation"),
    @JsonSubTypes.Type(value = ServerResourceNumberRequirementRepresentation.class, name = "ClientResourceNumberRequirementRepresentation"),
    @JsonSubTypes.Type(value = ServerCardDeckRepresentation.class, name = "ClientCardDeckRepresentation"),
    @JsonSubTypes.Type(value = ServerCoveredCardsDeckRepresentation.class, name = "ClientCoveredCardsDeckRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardRepresentation.class, name = "ClientDevelopmentCardRepresentation"),
    @JsonSubTypes.Type(value = ServerLeaderCardRepresentation.class, name = "ClientLeaderCardRepresentation"),
    @JsonSubTypes.Type(value = ServerDevelopmentCardCostDiscountRepresentation.class, name = "ClientDevelopmentCardCostDiscountRepresentation"),
    @JsonSubTypes.Type(value = ServerMarbleColourRepresentation.class, name = "ClientMarbleColourRepresentation"),
    @JsonSubTypes.Type(value = ServerProductionRepresentation.class, name = "ClientProductionRepresentation"),
    @JsonSubTypes.Type(value = ServerWhiteMarbleSubstitutionRepresentation.class, name = "ClientWhiteMarbleSubstitutionRepresentation"),
    @JsonSubTypes.Type(value = ServerResourceStorageRepresentation.class, name = "ClientResourceStorageRepresentation"),
    @JsonSubTypes.Type(value = ServerPlayerRepresentation.class, name = "ClientPlayerRepresentation"),
    @JsonSubTypes.Type(value = ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation.class, name = "ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerMaxResourceNumberRuleRepresentation.class, name = "ClientMaxResourceNumberRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerSameResourceTypeRuleRepresentation.class, name = "ClientSameResourceTypeRuleRepresentation"),
    @JsonSubTypes.Type(value = ServerSpecificResourceTypeRuleRepresentation.class, name = "ClientSpecificResourceTypeRuleRepresentation"),
})
public abstract class ServerRepresentation {

}
