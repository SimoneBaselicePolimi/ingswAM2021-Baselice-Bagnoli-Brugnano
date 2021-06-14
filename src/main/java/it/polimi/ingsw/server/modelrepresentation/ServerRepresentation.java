package it.polimi.ingsw.server.modelrepresentation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.ServerGameContextRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.*;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.marketrepresentation.ServerMarketRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ServerPlayerContextRepresentation;
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

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "RepresentationType")
//@JsonSubTypes({
//    @JsonSubTypes.Type(value = ServerFaithPathEventRepresentation.class, name = "FaithPathEventRepresentation"),
//    @JsonSubTypes.Type(value = ServerFaithPathRepresentation.class, name = "FaithPathRepresentation"),
//    @JsonSubTypes.Type(value = ServerFaithPathSinglePlayerRepresentation.class, name = "FaithPathSinglePlayerRepresentation"),
//    @JsonSubTypes.Type(value = ServerVaticanReportSectionRepresentation.class, name = "VaticanReportSectionRepresentation"),
//    @JsonSubTypes.Type(value = ServerMarketRepresentation.class, name = "MarketRepresentation"),
//    @JsonSubTypes.Type(value = ServerPlayerContextRepresentation.class, name = "PlayerContextRepresentation"),
//    @JsonSubTypes.Type(value = ServerGameContextRepresentation.class, name = "GameContextRepresentation"),
//    @JsonSubTypes.Type(value = ServerGameActionRepresentation.class, name = "GameActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerGameHistoryRepresentation.class, name = "GameHistoryRepresentation"),
//    @JsonSubTypes.Type(value = ServerCardDeckRepresentation.class, name = "CardDeckRepresentation"),
//    @JsonSubTypes.Type(value = ServerRegisteredIdentifiableItemRepresentation.class, name = "RegisteredIdentifiableItemRepresentation"),
//    @JsonSubTypes.Type(value = ServerDevelopmentCardsTableRepresentation.class, name = "DevelopmentCardsTableRepresentation"),
//    @JsonSubTypes.Type(value = ServerResourceStorageRuleRepresentation.class, name = "ResourceStorageRuleRepresentation"),
//    @JsonSubTypes.Type(value = ServerFaithPathLastPositionReachedActionRepresentation.class, name = "FaithPathLastPositionReachedActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerFaithPathMoveActionRepresentation.class, name = "FaithPathMoveActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerFaithPathVaticanReportActionRepresentation.class, name = "FaithPathVaticanReportActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerActivateLeaderCardsActionRepresentation.class, name = "ActivateLeaderCardsActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerDevelopmentActionRepresentation.class, name = "DevelopmentActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerDiscardedResourcesMarketActionRepresentation.class, name = "DiscardedResourcesMarketActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerDiscardLeaderCardsActionRepresentation.class, name = "DiscardLeaderCardsActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerMainTurnInitialActionRepresentation.class, name = "MainTurnInitialActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerObtainedMarblesMarketActionRepresentation.class, name = "ObtainedMarblesMarketActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerObtainedResourcesMarketActionRepresentation.class, name = "ObtainedResourcesMarketActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerPostTurnFinalActionRepresentation.class, name = "PostTurnFinalActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerProductionActionRepresentation.class, name = "ProductionActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerSetupChoiceActionRepresentation.class, name = "SetupChoiceActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerSetupStartedActionRepresentation.class, name = "SetupStartedActionRepresentation"),
//    @JsonSubTypes.Type(value = ServerPlayerOwnedDevelopmentCardDeckRepresentation.class, name = "PlayerOwnedDevelopmentCardDeckRepresentation"),
//    @JsonSubTypes.Type(value = ServerDevelopmentCardColourAndLevelRequirementRepresentation.class, name = "DevelopmentCardColourAndLevelRequirementRepresentation"),
//    @JsonSubTypes.Type(value = ServerDevelopmentCardColourRequirementRepresentation.class, name = "DevelopmentCardColourRequirementRepresentation"),
//    @JsonSubTypes.Type(value = ServerResourceNumberRequirementRepresentation.class, name = "ResourceNumberRequirementRepresentation"),
//    @JsonSubTypes.Type(value = ServerCardDeckRepresentation.class, name = "CardDeckRepresentation"),
//    @JsonSubTypes.Type(value = ServerCoveredCardsDeckRepresentation.class, name = "CoveredCardsDeckRepresentation"),
//    @JsonSubTypes.Type(value = ServerDevelopmentCardRepresentation.class, name = "DevelopmentCardRepresentation"),
//    @JsonSubTypes.Type(value = ServerLeaderCardRepresentation.class, name = "LeaderCardRepresentation"),
//    @JsonSubTypes.Type(value = ServerDevelopmentCardCostDiscountRepresentation.class, name = "DevelopmentCardCostDiscountRepresentation"),
//    @JsonSubTypes.Type(value = ServerMarbleColourRepresentation.class, name = "MarbleColourRepresentation"),
//    @JsonSubTypes.Type(value = ServerProductionRepresentation.class, name = "ProductionRepresentation"),
//    @JsonSubTypes.Type(value = ServerWhiteMarbleSubstitutionRepresentation.class, name = "WhiteMarbleSubstitutionRepresentation"),
//    @JsonSubTypes.Type(value = ServerResourceStorageRepresentation.class, name = "ResourceStorageRepresentation"),
//    @JsonSubTypes.Type(value = Player.class, name = "PlayerRepresentation"),
//    @JsonSubTypes.Type(value = ServerDifferentResourceTypesInDifferentStoragesRuleRepresentation.class, name = "DifferentResourceTypesInDifferentStoragesRuleRepresentation"),
//    @JsonSubTypes.Type(value = ServerMaxResourceNumberRuleRepresentation.class, name = "MaxResourceNumberRuleRepresentation"),
//    @JsonSubTypes.Type(value = ServerSameResourceTypeRuleRepresentation.class, name = "SameResourceTypeRuleRepresentation"),
//    @JsonSubTypes.Type(value = ServerSpecificResourceTypeRuleRepresentation.class, name = "SpecificResourceTypeRuleRepresentation"),
//})
public abstract class ServerRepresentation {

}
