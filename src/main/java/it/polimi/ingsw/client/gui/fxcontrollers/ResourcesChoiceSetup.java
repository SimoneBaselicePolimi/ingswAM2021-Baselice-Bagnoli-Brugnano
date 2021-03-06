package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.InitialChoicesClientRequest;
import it.polimi.ingsw.client.gui.fxcontrollers.components.ResourcesChoice;
import it.polimi.ingsw.client.gui.fxcontrollers.components.ResourcesRepositioning;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.client.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ResourcesChoiceSetup extends AbstractController{

    @FXML
    public AnchorPane container;

    public ResourcesChoiceSetup() {
    }

    @FXML
    public void initialize() {

        InitialChoicesServerMessage message =
            (InitialChoicesServerMessage) clientManager.getEntryInContextInfoMap("initialChoicesServerMessage");


        ClientPlayerContextRepresentation playerContext =
            clientManager.getGameContextRepresentation().getPlayerContext(clientManager.getMyPlayer());

        container.getChildren().add(
            new ResourcesChoice(
                message.numberOfStarResources,
                Arrays.asList(ResourceType.values()),
                resChosen -> {
                    playerContext.getTempStorage().setResources(resChosen);
                    List<ClientLeaderCardRepresentation> selectedCards =
                        (List<ClientLeaderCardRepresentation>) clientManager.getEntryInContextInfoMap("selectedLeaderCards");
                    clientManager.loadScene(
                        new ResourcesRepositioning(
                            clientManager.getMyPlayer(),
                            false,
                            (storagesModified, resourcesLeftInTempStorage) ->
                                sendInitialChoicesToServer(
                                    new HashSet<>(selectedCards),
                                    storagesModified.stream()
                                        .collect(Collectors.toMap(s -> s, ClientResourceStorageRepresentation::getResources))
                                )
                        )
                    );
                }
            )
        );

    }

    CompletableFuture<Void> sendInitialChoicesToServer(
        Set<ClientLeaderCardRepresentation> leaderCardsChosenByThePlayer,
        Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> chosenResourcesToAddByStorage
    ){
        return clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
            new InitialChoicesClientRequest(
                clientManager.getMyPlayer(),
                leaderCardsChosenByThePlayer,
                chosenResourcesToAddByStorage
            )
        )).thenCompose(serverMessage ->
            ServerMessageUtils.ifMessageTypeCompute(
                serverMessage,
                GameUpdateServerMessage.class,
                message -> {
                    if(clientManager.getMyPlayer().equals(clientManager.getGameContextRepresentation().getActivePlayer()))
                        clientManager.setGameState(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION);
                    else
                        clientManager.setGameState(GameState.ANOTHER_PLAYER_TURN);
                    clientManager.handleGameUpdates(message.gameUpdates);
                    clientManager.loadScene("FaithPath.fxml");
                    return CompletableFuture.<Void>completedFuture(null);
                }
            ).elseIfMessageTypeCompute(
                InvalidRequestServerMessage.class,
                message -> {
                    //clientManager.tellUser(message.errorMessage);
                    clientManager.loadScene("LeaderCardsSetup.fxml");
                    return CompletableFuture.completedFuture(null);
                }
            ).elseCompute(message -> {
                clientManager.loadScene("LeaderCardsSetup.fxml");
                return CompletableFuture.completedFuture(null);
            }).apply()
        );
    }



}
