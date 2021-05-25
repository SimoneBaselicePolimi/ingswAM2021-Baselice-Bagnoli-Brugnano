package it.polimi.ingsw.client.modelrepresentation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.*;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.*;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.*;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientDevelopmentCardColourAndLevelRequirementRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientDevelopmentCardColourRequirementRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientResourceNumberRequirementRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.*;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.servermessage.*;

import java.util.HashSet;
import java.util.Set;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "RepresentationType")
//@JsonSubTypes({
//    @JsonSubTypes.Type(value = ClientFaithPathEventRepresentation.class, name = "FaithPathEventRepresentation"),
//    @JsonSubTypes.Type(value = ClientFaithPathRepresentation.class, name = "FaithPathRepresentation"),
//    @JsonSubTypes.Type(value = ClientFaithPathSinglePlayerRepresentation.class, name = "FaithPathSinglePlayerRepresentation"),
//    @JsonSubTypes.Type(value = ClientVaticanReportSectionRepresentation.class, name = "VaticanReportSectionRepresentation"),
//    @JsonSubTypes.Type(value = ClientMarketRepresentation.class, name = "MarketRepresentation"),
//    @JsonSubTypes.Type(value = ClientPlayerContextRepresentation.class, name = "PlayerContextRepresentation"),
//    @JsonSubTypes.Type(value = ClientGameContextRepresentation.class, name = "GameContextRepresentation"),
//    @JsonSubTypes.Type(value = ClientGameActionRepresentation.class, name = "GameActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientGameHistoryRepresentation.class, name = "GameHistoryRepresentation"),
//    @JsonSubTypes.Type(value = ClientCardDeckRepresentation.class, name = "CardDeckRepresentation"),
//    @JsonSubTypes.Type(value = ClientRegisteredIdentifiableItemRepresentation.class, name = "RegisteredIdentifiableItemRepresentation"),
//    @JsonSubTypes.Type(value = ClientDevelopmentCardsTableRepresentation.class, name = "DevelopmentCardsTableRepresentation"),
//    @JsonSubTypes.Type(value = ClientResourceStorageRuleRepresentation.class, name = "ResourceStorageRuleRepresentation"),
//    @JsonSubTypes.Type(value = ClientFaithPathLastPositionReachedActionRepresentation.class, name = "FaithPathLastPositionReachedActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientFaithPathMoveActionRepresentation.class, name = "FaithPathMoveActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientFaithPathVaticanReportActionRepresentation.class, name = "FaithPathVaticanReportActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientActivateLeaderCardsActionRepresentation.class, name = "ActivateLeaderCardsActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientDevelopmentActionRepresentation.class, name = "DevelopmentActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientDiscardedResourcesMarketActionRepresentation.class, name = "DiscardedResourcesMarketActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientDiscardLeaderCardsActionRepresentation.class, name = "DiscardLeaderCardsActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientMainTurnInitialActionRepresentation.class, name = "MainTurnInitialActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientObtainedMarblesMarketActionRepresentation.class, name = "ObtainedMarblesMarketActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientObtainedResourcesMarketActionRepresentation.class, name = "ObtainedResourcesMarketActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientPostTurnFinalActionRepresentation.class, name = "PostTurnFinalActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientProductionActionRepresentation.class, name = "ProductionActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientSetupChoiceActionRepresentation.class, name = "SetupChoiceActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientSetupStartedActionRepresentation.class, name = "SetupStartedActionRepresentation"),
//    @JsonSubTypes.Type(value = ClientPlayerOwnedDevelopmentCardDeckRepresentation.class, name = "PlayerOwnedDevelopmentCardDeckRepresentation"),
//    @JsonSubTypes.Type(value = ClientDevelopmentCardColourAndLevelRequirementRepresentation.class, name = "DevelopmentCardColourAndLevelRequirementRepresentation"),
//    @JsonSubTypes.Type(value = ClientDevelopmentCardColourRequirementRepresentation.class, name = "DevelopmentCardColourRequirementRepresentation"),
//    @JsonSubTypes.Type(value = ClientResourceNumberRequirementRepresentation.class, name = "ResourceNumberRequirementRepresentation"),
//    @JsonSubTypes.Type(value = ClientCardDeckRepresentation.class, name = "CardDeckRepresentation"),
//    @JsonSubTypes.Type(value = ClientCoveredCardsDeckRepresentation.class, name = "CoveredCardsDeckRepresentation"),
//    @JsonSubTypes.Type(value = ClientDevelopmentCardRepresentation.class, name = "DevelopmentCardRepresentation"),
//    @JsonSubTypes.Type(value = ClientLeaderCardRepresentation.class, name = "LeaderCardRepresentation"),
//    @JsonSubTypes.Type(value = ClientDevelopmentCardCostDiscountRepresentation.class, name = "DevelopmentCardCostDiscountRepresentation"),
//    @JsonSubTypes.Type(value = ClientMarbleColourRepresentation.class, name = "MarbleColourRepresentation"),
//    @JsonSubTypes.Type(value = ClientProductionRepresentation.class, name = "ProductionRepresentation"),
//    @JsonSubTypes.Type(value = ClientWhiteMarbleSubstitutionRepresentation.class, name = "WhiteMarbleSubstitutionRepresentation"),
//    @JsonSubTypes.Type(value = ClientResourceStorageRepresentation.class, name = "ResourceStorageRepresentation"),
//    @JsonSubTypes.Type(value = ClientPlayerRepresentation.class, name = "PlayerRepresentation"),
//    @JsonSubTypes.Type(value = ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation.class, name = "DifferentResourceTypesInDifferentStoragesRuleRepresentation"),
//    @JsonSubTypes.Type(value = ClientMaxResourceNumberRuleRepresentation.class, name = "MaxResourceNumberRuleRepresentation"),
//    @JsonSubTypes.Type(value = ClientSameResourceTypeRuleRepresentation.class, name = "SameResourceTypeRuleRepresentation"),
//    @JsonSubTypes.Type(value = ClientSpecificResourceTypeRuleRepresentation.class, name = "SpecificResourceTypeRuleRepresentation"),
//})
public abstract class ClientRepresentation {
    Set<View> subscribedView = new HashSet<>();

    public void subscribe(View view) {
        subscribedView.add(view);
    }

    public void unsubscribe(View view) {
        subscribedView.remove(view);
    }

    protected void notifyViews() {
        subscribedView.forEach(View::updateView);
    }

}
