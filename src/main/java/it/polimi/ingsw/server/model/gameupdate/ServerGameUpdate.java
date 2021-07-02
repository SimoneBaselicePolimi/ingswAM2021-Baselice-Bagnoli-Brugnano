package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "gameUpdateType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ServerActivePlayerUpdate.class, name = "ActivePlayerUpdate"),
    @JsonSubTypes.Type(value = ServerBlackCrossFaithUpdate.class, name = "BlackCrossFaithUpdate"),
    @JsonSubTypes.Type(value = ServerFaithUpdate.class, name = "FaithUpdate"),
    @JsonSubTypes.Type(value = ServerGameHistoryUpdate.class, name = "GameHistoryUpdate"),
    @JsonSubTypes.Type(value = ServerLeaderCardCanBeActivatedUpdate.class, name = "LeaderCardCanBeActivatedUpdate"),
    @JsonSubTypes.Type(value = ServerLeaderCardStateUpdate.class, name = "LeaderCardStateUpdate"),
    @JsonSubTypes.Type(value = ServerLeaderCardsThePlayerOwnsUpdate.class, name = "LeaderCardsThePlayerOwnsUpdate"),
    @JsonSubTypes.Type(value = ServerMarketUpdate.class, name = "MarketUpdate"),
    @JsonSubTypes.Type(value = ServerPlayerOwnedDevelopmentCardDeckUpdate.class, name = "PlayerOwnedDevelopmentCardDeckUpdate"),
    @JsonSubTypes.Type(value = ServerPopeCardsUpdate.class, name = "PopeCardsUpdate"),
    @JsonSubTypes.Type(value = ServerPurchasableDevelopmentCardsUpdate.class, name = "PurchasableDevelopmentCardsUpdate"),
    @JsonSubTypes.Type(value = ServerResourceStorageUpdate.class, name = "ResourceStorageUpdate"),
    @JsonSubTypes.Type(value = ServerTotalResourcesUpdate.class, name = "TotalResourcesUpdate"),
    @JsonSubTypes.Type(value = ServerShuffledDevelopmentCardDeckOnTableUpdate.class, name = "ShuffledDevelopmentCardDeckOnTableUpdate"),
    @JsonSubTypes.Type(value = ServerTempStarResourcesUpdate.class, name = "TempStarResourcesUpdate")
})
public class ServerGameUpdate {

}
