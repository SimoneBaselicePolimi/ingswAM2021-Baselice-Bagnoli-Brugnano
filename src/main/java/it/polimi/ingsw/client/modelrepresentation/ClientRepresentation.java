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

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "RepresentationType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientFaithPathEventRepresentation.class, name = "ClientFaithPathEventRepresentation"),
    @JsonSubTypes.Type(value = ClientFaithPathRepresentation.class, name = "ClientFaithPathRepresentation"),
    @JsonSubTypes.Type(value = ClientFaithPathSinglePlayerRepresentation.class, name = "ClientFaithPathSinglePlayerRepresentation"),
    @JsonSubTypes.Type(value = ClientVaticanReportSectionRepresentation.class, name = "ClientVaticanReportSectionRepresentation"),
    @JsonSubTypes.Type(value = ClientMarketRepresentation.class, name = "ClientMarketRepresentation"),
    @JsonSubTypes.Type(value = ClientPlayerContextRepresentation.class, name = "ClientPlayerContextRepresentation"),
    @JsonSubTypes.Type(value = ClientGameContextRepresentation.class, name = "ClientGameContextRepresentation"),
    @JsonSubTypes.Type(value = ClientGameActionRepresentation.class, name = "ClientGameActionRepresentation"),
    @JsonSubTypes.Type(value = ClientGameHistoryRepresentation.class, name = "ClientGameHistoryRepresentation"),
    @JsonSubTypes.Type(value = ClientCardDeckRepresentation.class, name = "ClientCardDeckRepresentation"),
    @JsonSubTypes.Type(value = ClientRegisteredIdentifiableItemRepresentation.class, name = "ClientRegisteredIdentifiableItemRepresentation"),
    @JsonSubTypes.Type(value = ClientDevelopmentCardsTableRepresentation.class, name = "ClientDevelopmentCardsTableRepresentation"),
    @JsonSubTypes.Type(value = ClientResourceStorageRuleRepresentation.class, name = "ClientResourceStorageRuleRepresentation"),
    @JsonSubTypes.Type(value = ClientFaithPathLastPositionReachedActionRepresentation.class, name = "ClientFaithPathLastPositionReachedActionRepresentation"),
    @JsonSubTypes.Type(value = ClientFaithPathMoveActionRepresentation.class, name = "ClientFaithPathMoveActionRepresentation"),
    @JsonSubTypes.Type(value = ClientFaithPathVaticanReportActionRepresentation.class, name = "ClientFaithPathVaticanReportActionRepresentation"),
    @JsonSubTypes.Type(value = ClientActivateLeaderCardsActionRepresentation.class, name = "ClientActivateLeaderCardsActionRepresentation"),
    @JsonSubTypes.Type(value = ClientDevelopmentActionRepresentation.class, name = "ClientDevelopmentActionRepresentation"),
    @JsonSubTypes.Type(value = ClientDiscardedResourcesMarketActionRepresentation.class, name = "ClientDiscardedResourcesMarketActionRepresentation"),
    @JsonSubTypes.Type(value = ClientDiscardLeaderCardsActionRepresentation.class, name = "ClientDiscardLeaderCardsActionRepresentation"),
    @JsonSubTypes.Type(value = ClientMainTurnInitialActionRepresentation.class, name = "ClientMainTurnInitialActionRepresentation"),
    @JsonSubTypes.Type(value = ClientObtainedMarblesMarketActionRepresentation.class, name = "ClientObtainedMarblesMarketActionRepresentation"),
    @JsonSubTypes.Type(value = ClientObtainedResourcesMarketActionRepresentation.class, name = "ClientObtainedResourcesMarketActionRepresentation"),
    @JsonSubTypes.Type(value = ClientPostTurnFinalActionRepresentation.class, name = "ClientPostTurnFinalActionRepresentation"),
    @JsonSubTypes.Type(value = ClientProductionActionRepresentation.class, name = "ClientProductionActionRepresentation"),
    @JsonSubTypes.Type(value = ClientSetupChoiceActionRepresentation.class, name = "ClientSetupChoiceActionRepresentation"),
    @JsonSubTypes.Type(value = ClientSetupStartedActionRepresentation.class, name = "ClientSetupStartedActionRepresentation"),
    @JsonSubTypes.Type(value = ClientPlayerOwnedDevelopmentCardDeckRepresentation.class, name = "ClientPlayerOwnedDevelopmentCardDeckRepresentation"),
    @JsonSubTypes.Type(value = ClientDevelopmentCardColourAndLevelRequirementRepresentation.class, name = "ClientDevelopmentCardColourAndLevelRequirementRepresentation"),
    @JsonSubTypes.Type(value = ClientDevelopmentCardColourRequirementRepresentation.class, name = "ClientDevelopmentCardColourRequirementRepresentation"),
    @JsonSubTypes.Type(value = ClientResourceNumberRequirementRepresentation.class, name = "ClientResourceNumberRequirementRepresentation"),
    @JsonSubTypes.Type(value = ClientCardDeckRepresentation.class, name = "ClientCardDeckRepresentation"),
    @JsonSubTypes.Type(value = ClientCoveredCardsDeckRepresentation.class, name = "ClientCoveredCardsDeckRepresentation"),
    @JsonSubTypes.Type(value = ClientDevelopmentCardRepresentation.class, name = "ClientDevelopmentCardRepresentation"),
    @JsonSubTypes.Type(value = ClientLeaderCardRepresentation.class, name = "ClientLeaderCardRepresentation"),
    @JsonSubTypes.Type(value = ClientDevelopmentCardCostDiscountRepresentation.class, name = "ClientDevelopmentCardCostDiscountRepresentation"),
    @JsonSubTypes.Type(value = ClientMarbleColourRepresentation.class, name = "ClientMarbleColourRepresentation"),
    @JsonSubTypes.Type(value = ClientProductionRepresentation.class, name = "ClientProductionRepresentation"),
    @JsonSubTypes.Type(value = ClientWhiteMarbleSubstitutionRepresentation.class, name = "ClientWhiteMarbleSubstitutionRepresentation"),
    @JsonSubTypes.Type(value = ClientResourceStorageRepresentation.class, name = "ClientResourceStorageRepresentation"),
    @JsonSubTypes.Type(value = ClientPlayerRepresentation.class, name = "ClientPlayerRepresentation"),
    @JsonSubTypes.Type(value = ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation.class, name = "ClientDifferentResourceTypesInDifferentStoragesRuleRepresentation"),
    @JsonSubTypes.Type(value = ClientMaxResourceNumberRuleRepresentation.class, name = "ClientMaxResourceNumberRuleRepresentation"),
    @JsonSubTypes.Type(value = ClientSameResourceTypeRuleRepresentation.class, name = "ClientSameResourceTypeRuleRepresentation"),
    @JsonSubTypes.Type(value = ClientSpecificResourceTypeRuleRepresentation.class, name = "ClientSpecificResourceTypeRuleRepresentation"),
})
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
