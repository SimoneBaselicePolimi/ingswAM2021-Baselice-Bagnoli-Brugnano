package it.polimi.ingsw.server.controller.servermessage;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.client.servermessage.PlayerDisconnectedServerMessage;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "serverMessageType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = DevelopmentActionServerMessage.class, name = "DevelopmentActionServerMessage"),
    @JsonSubTypes.Type(value = EndGameServerMessage.class, name = "EndGameServerMessage"),
    @JsonSubTypes.Type(value = EndTurnServerMessage.class, name = "EndTurnServerMessage"),
    @JsonSubTypes.Type(value = GameInitializationStartedServerMessage.class, name = "GameInitializationStartedServerMessage"),
    @JsonSubTypes.Type(value = GameUpdateServerMessage.class, name = "GameUpdateServerMessage"),
    @JsonSubTypes.Type(value = InitialChoicesServerMessage.class, name = "InitialChoicesServerMessage"),
    @JsonSubTypes.Type(value = InvalidClientMessageServerMessage.class, name = "InvalidClientMessageServerMessage"),
    @JsonSubTypes.Type(value = InvalidRequestServerMessage.class, name = "InvalidRequestServerMessage"),
    @JsonSubTypes.Type(value = LeaderActionServerMessage.class, name = "LeaderActionServerMessage"),
    @JsonSubTypes.Type(value = MarketActionServerMessage.class, name = "MarketActionServerMessage"),
    @JsonSubTypes.Type(value = NewPlayerEnteredNewGameLobbyServerMessage.class, name = "NewPlayerEnteredNewGameLobbyServerMessage"),
    @JsonSubTypes.Type(value = PlayerCanCreateNewLobbyServerMessage.class, name = "PlayerCanCreateNewLobbyServerMessage"),
    @JsonSubTypes.Type(value = PlayerNameAlreadyExistsServerMessage.class, name = "PlayerNameAlreadyExistsServerMessage"),
    @JsonSubTypes.Type(value = PostGameSetupServerMessage.class, name = "PostGameSetupServerMessage"),
    @JsonSubTypes.Type(value = ProductionActionServerMessage.class, name = "ProductionActionServerMessage"),
    @JsonSubTypes.Type(value = GameInitialRepresentationServerMessage.class, name = "GameInitialRepresentationServerMessage"),
    @JsonSubTypes.Type(value = PlayerDisconnectedServerMessage.class, name = "PlayerDisconnectedServerMessage")
})
public class ServerMessage {

}
