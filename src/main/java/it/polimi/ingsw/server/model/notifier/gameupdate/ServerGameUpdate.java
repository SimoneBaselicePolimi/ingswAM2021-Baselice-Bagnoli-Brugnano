package it.polimi.ingsw.server.model.notifier.gameupdate;

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
    @JsonSubTypes.Type(value = ServerResourceStorageUpdate.class, name = "ResourceStorageUpdate"),
    @JsonSubTypes.Type(value = ServerResourcesUpdate.class, name = "ResourcesUpdate"),
    @JsonSubTypes.Type(value = ServerShuffledDevelopmentCardDeckOnTableUpdate.class, name = "ShuffledDevelopmentCardDeckOnTableUpdate")
})
public class ServerGameUpdate {

}
