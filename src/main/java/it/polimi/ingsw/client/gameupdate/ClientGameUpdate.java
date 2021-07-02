package it.polimi.ingsw.client.gameupdate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "gameUpdateType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientActivePlayerUpdate.class, name = "ActivePlayerUpdate"),
    @JsonSubTypes.Type(value = ClientBlackCrossFaithUpdate.class, name = "BlackCrossFaithUpdate"),
    @JsonSubTypes.Type(value = ClientFaithUpdate.class, name = "FaithUpdate"),
    @JsonSubTypes.Type(value = ClientGameHistoryUpdate.class, name = "GameHistoryUpdate"),
    @JsonSubTypes.Type(value = ClientLeaderCardCanBeActivatedUpdate.class, name = "LeaderCardCanBeActivatedUpdate"),
    @JsonSubTypes.Type(value = ClientLeaderCardStateUpdate.class, name = "LeaderCardStateUpdate"),
    @JsonSubTypes.Type(value = ClientLeaderCardsThePlayerOwnsUpdate.class, name = "LeaderCardsThePlayerOwnsUpdate"),
    @JsonSubTypes.Type(value = ClientMarketUpdate.class, name = "MarketUpdate"),
    @JsonSubTypes.Type(value = ClientPlayerOwnedDevelopmentCardDeckUpdate.class, name = "PlayerOwnedDevelopmentCardDeckUpdate"),
    @JsonSubTypes.Type(value = ClientPopeCardsUpdate.class, name = "PopeCardsUpdate"),
    @JsonSubTypes.Type(value = ClientPurchasableDevelopmentCardsUpdate.class, name = "PurchasableDevelopmentCardsUpdate"),
    @JsonSubTypes.Type(value = ClientResourceStorageUpdate.class, name = "ResourceStorageUpdate"),
    @JsonSubTypes.Type(value = ClientTotalResourcesUpdate.class, name = "TotalResourcesUpdate"),
    @JsonSubTypes.Type(value = ClientShuffledDevelopmentCardDeckOnTableUpdate.class, name = "ShuffledDevelopmentCardDeckOnTableUpdate"),
    @JsonSubTypes.Type(value = ClientTempStarResourcesUpdate.class, name = "TempStarResourcesUpdate")
})
public abstract class ClientGameUpdate {

    public abstract GameUpdateHandler getHandler();

}
