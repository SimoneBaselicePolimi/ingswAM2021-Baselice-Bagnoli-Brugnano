package it.polimi.ingsw.client.modelrepresentation;

import it.polimi.ingsw.client.view.View;

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
//    @JsonSubTypes.Type(value = ClientGameHistoryRepresentation.class, name = "GameHistoryRepresentation"),
//    @JsonSubTypes.Type(value = ClientCardDeckRepresentation.class, name = "CardDeckRepresentation"),
//    @JsonSubTypes.Type(value = ClientRegisteredIdentifiableItemRepresentation.class, name = "RegisteredIdentifiableItemRepresentation"),
//    @JsonSubTypes.Type(value = ClientDevelopmentCardsTableRepresentation.class, name = "DevelopmentCardsTableRepresentation"),
//    @JsonSubTypes.Type(value = ClientResourceStorageRuleRepresentation.class, name = "ResourceStorageRuleRepresentation"),
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
//    @JsonSubTypes.Type(value = Player.class, name = "PlayerRepresentation"),
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
