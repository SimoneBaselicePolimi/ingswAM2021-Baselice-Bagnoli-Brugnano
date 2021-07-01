package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.client.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.client.gui.fxcontrollers.components.Dashboard;
import it.polimi.ingsw.client.gui.fxcontrollers.components.Production;
import it.polimi.ingsw.client.gui.fxcontrollers.components.ResourcesChoice;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.servermessage.EndTurnServerMessage;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.Colour;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PlayerDashboard extends GameScene implements View {

    @FXML
    Button activateLeaderCard;

    @FXML
    Button cancelActivationLeaderCard;

    @FXML
    Button endTurn;

    @FXML
    AnchorPane dashboardContainer;

    @FXML
    Button activateProductionsButton;

    @FXML
    Button cancelButton;

    @FXML
    Button activateSelectedProductionsButton;

    @FXML
    HBox resourcesLeftToThePlayerComp;

    Dashboard dashboard;
    ClientPlayerContextRepresentation playerContext;

    BooleanProperty isActivateLeaderCardModeEnabled =  new SimpleBooleanProperty(false);
    SetProperty<ClientLeaderCardRepresentation> leaderCardsThePlayerCanActivate = new SimpleSetProperty<>(
        FXCollections.observableSet(new HashSet<>())
    );

    BooleanProperty isProductionsActivationModeEnabled =  new SimpleBooleanProperty(false);
    SetProperty<Production> selectedProds = new SimpleSetProperty<>(FXCollections.observableSet(new HashSet<>()));
    MapProperty<ResourceType, Integer> resourcesLeftToThePlayer = new SimpleMapProperty<>(FXCollections.observableMap(new HashMap<>()));

    BooleanProperty canMyPlayerEndTurn = new SimpleBooleanProperty(false);

    public PlayerDashboard() {
        super(4);
        playerContext = clientManager.getGameContextRepresentation().getPlayerContext(clientManager.getMyPlayer());
    }

    @FXML
    @Override
    protected void initialize() {

        dashboard = new Dashboard(clientManager.getMyPlayer(), false);

        dashboard.getPlayerLeaderCards().forEach(comp -> comp.getLeaderCardRepresentation().subscribe(this));

        dashboardContainer.getChildren().add(dashboard);

        activateProductionsButton.visibleProperty().bind(isProductionsActivationModeEnabled.not().and(canMyPlayerDoMainAction));
        activateProductionsButton.setOnMouseClicked(e -> isProductionsActivationModeEnabled.setValue(true));

        cancelButton.visibleProperty().bind(isProductionsActivationModeEnabled);
        cancelButton.setOnMouseClicked(e -> {
            isProductionsActivationModeEnabled.setValue(false);
            selectedProds.clear();
        });

        activateSelectedProductionsButton.visibleProperty().bind(
            isProductionsActivationModeEnabled.and(selectedProds.sizeProperty().greaterThan(0))
        );
        activateSelectedProductionsButton.setOnMouseClicked(e -> {
            //TODO
        });

        isProductionsActivationModeEnabled.addListener( (obv, oldVal, newVal) -> {
            if(!oldVal && newVal) {
                resourcesLeftToThePlayer.clear();
                resourcesLeftToThePlayer.putAll(playerContext.getTotalResourcesOwnedByThePlayer());
            }
            updateProductions();
        });
        selectedProds.addListener( (InvalidationListener) obv -> updateProductions());

        resourcesLeftToThePlayer.addListener( (InvalidationListener) obv -> updateResourcesLeftToThePlayerComp());
        resourcesLeftToThePlayerComp.visibleProperty().bind(isProductionsActivationModeEnabled);

        activateLeaderCard.visibleProperty().bind(isActivateLeaderCardModeEnabled.not().and(isMyPlayerTurn));
        activateLeaderCard.setOnMouseClicked(e -> {
            isActivateLeaderCardModeEnabled.setValue(true);
            updateLeaderCards();
        });

        cancelActivationLeaderCard.visibleProperty().bind(isActivateLeaderCardModeEnabled);
        cancelActivationLeaderCard.setOnMouseClicked(e -> isActivateLeaderCardModeEnabled.setValue(false));

        leaderCardsThePlayerCanActivate.addListener( (InvalidationListener) obv -> updateLeaderCards());

        endTurn.visibleProperty().bind(canMyPlayerEndTurn);
        endTurn.setOnMouseClicked(e -> endMyTurn());

        super.initialize();
        updateView();
    }

    protected void activateLeaderCardAndSendMessageToServer(ClientLeaderCardRepresentation cardToActivate) {
        clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
            new ActivateLeaderCardClientRequest(
                clientManager.getMyPlayer(),
                cardToActivate
            )
        )).thenCompose(serverMessage ->
            ServerMessageUtils.ifMessageTypeCompute(
                serverMessage,
                GameUpdateServerMessage.class,
                message -> {
                    clientManager.handleGameUpdates(message.gameUpdates);
                    clientManager.loadScene("PlayerDashboard.fxml");
                    return CompletableFuture.completedFuture(null);
                }
            ).elseCompute(message -> {
                clientManager.loadScene("Market.fxml");
                return CompletableFuture.completedFuture(null);
            }).apply()
        );
    }

    protected void endMyTurn() {
        clientManager.sendMessageAndGetAnswer(
            new PlayerRequestClientMessage(new EndTurnClientRequest(clientManager.getMyPlayer()))
        ).thenCompose(serverMessage ->
            ServerMessageUtils.ifMessageTypeCompute(
                serverMessage,
                EndTurnServerMessage.class,
                message -> {
                    clientManager.handleGameUpdates(message.gameUpdates);
                    clientManager.loadScene("PlayerDashboard.fxml");
                    return CompletableFuture.completedFuture(null);
                }
            ).elseIfMessageTypeCompute(
                InvalidRequestServerMessage.class,
                message -> {
                    clientManager.loadScene("Market.fxml");
                    return CompletableFuture.completedFuture(null);
                }
            ).elseCompute(message -> {
                clientManager.loadScene("Market.fxml");
                return CompletableFuture.completedFuture(null);
            }).apply()
        );
    }

    void updateResourcesLeftToThePlayerComp() {
        resourcesLeftToThePlayerComp.getChildren().clear();

        resourcesLeftToThePlayer.forEach( (resType, numOFRes) ->
            resourcesLeftToThePlayerComp.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
                numOFRes,
                resType.getIconPathForResourceType(),
                25,
                4
            ))
        );
    }

    void updateProductions() {
        dashboard.getAllProductionsComp().forEach(p -> {
            if (isProductionsActivationModeEnabled.get()) {
                ClientProductionRepresentation prodRepresentation = p.getProductionRepresentation();
                if (checkIfThePlayerHasNecessaryResourcesToActivateProd(prodRepresentation)) {
                    p.setBorderColour(Colour.YELLOW);
                    p.setOnMouseClicked(obv -> {
                        Map<ResourceType, Integer> resLeft = ResourceUtils.difference(
                            resourcesLeftToThePlayer.get(),
                            prodRepresentation.getResourceCost()
                        );
                        resourcesLeftToThePlayer.clear();
                        resourcesLeftToThePlayer.putAll(resLeft);
                        if(prodRepresentation.getStarResourceCost() > 0)
                            chooseStartCostProd(prodRepresentation);
                       //TODO rem res star res add card
                    });
                } else {
                    p.setBorderColour(Colour.GREY);
                    p.setOnMouseClicked(obv -> {});
                }
            } else {
                p.setDefaultBorderColour();
                p.setOnMouseClicked(obv -> {});
            }
        });
    }

    protected void chooseStartCostProd(ClientProductionRepresentation prodRepresentation) {
        Platform.runLater(() -> {
            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            popUpStage.initOwner(clientManager.getMainStage());

            VBox container = new VBox(10);

            container.getChildren().add(new Label("PScegli costo star"));
            container.getChildren().add(new ResourcesChoice(
                prodRepresentation.getStarResourceCost(),
                resourcesLeftToThePlayer.entrySet().stream()
                    .filter(e -> e.getValue() > 0)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList()),
                resourcesChosen -> {
                    if (ResourceUtils.areResourcesAContainedInB(resourcesChosen, resourcesLeftToThePlayer.get())) {
                        Map<ResourceType, Integer> resLeft = ResourceUtils.difference(
                            resourcesLeftToThePlayer.get(),
                            resourcesChosen
                        );
                        resourcesLeftToThePlayer.clear();
                        resourcesLeftToThePlayer.putAll(resLeft);
                    } else {
                        popUpStage.close();
                        Stage errorStage = new Stage();
                        errorStage.initModality(Modality.APPLICATION_MODAL);
                        errorStage.initOwner(clientManager.getMainStage());
                        VBox errContainer = new VBox(10);
                        errContainer.prefWidth(300);
                        errContainer.setAlignment(Pos.CENTER);
                        errContainer.getChildren().add(new Label("PNon hai abbastanza risorse!"));
                        Button okBtn = new Button("OK");
                        okBtn.setOnMouseClicked(obv -> {
                            errorStage.close();
                            chooseStartCostProd(prodRepresentation);
                        });
                        errContainer.getChildren().add(okBtn);
                        errorStage.setScene(new Scene(errContainer, 300, 150));
                        errorStage.show();
                    }
                }
            ));
            popUpStage.setScene(new Scene(container, 500, 600));
            popUpStage.show();
        });
    }

    protected boolean checkIfThePlayerHasNecessaryResourcesToActivateProd(ClientProductionRepresentation production) {
        int numOfStarResources = production.getStarResourceCost();
        int totalResourcesCost = production.getResourceCost().values().stream().reduce(0, Integer::sum);
        return
            (ResourceUtils.areResourcesAContainedInB(
                production.getResourceCost(),
                resourcesLeftToThePlayer.get()
            )) && (
                totalResourcesCost + numOfStarResources
                    <=
                resourcesLeftToThePlayer.get().values().stream().reduce(0, Integer::sum)
            );
    }

    void updateLeaderCards() {
        dashboard.getPlayerLeaderCards().forEach( leaderCard -> {
            if(
                isActivateLeaderCardModeEnabled.get() &&
                !leaderCard.getLeaderCardRepresentation().getState().equals(LeaderCardState.ACTIVE)
            ) {
                if (leaderCardsThePlayerCanActivate.contains(leaderCard.getLeaderCardRepresentation())) {
                    leaderCard.setBorderColour(Colour.YELLOW);
                    leaderCard.setOnMouseClicked(e -> activateLeaderCardAndSendMessageToServer(
                        leaderCard.getLeaderCardRepresentation())
                    );
                } else {
                    leaderCard.setBorderColour(Colour.GREY);
                    leaderCard.setOnMouseClicked(e -> {});
                }
            } else {
                leaderCard.setDefaultBorderColour();
                leaderCard.setOnMouseClicked(e -> {});
            }
        });
    }

    @Override
    public void updateView() {
        super.updateView();
        canMyPlayerEndTurn.setValue(clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION));
        leaderCardsThePlayerCanActivate.get().clear();
        leaderCardsThePlayerCanActivate.get().addAll(
            dashboard.playerContext.getLeaderCardsPlayerOwns().stream()
                .filter(c -> c.canBeActivated() && c.getState().equals(LeaderCardState.HIDDEN))
                .collect(Collectors.toList())
        );
    }

    @Override
    public void destroyView() {
        super.destroyView();
        dashboard.getPlayerLeaderCards().forEach(comp -> comp.getLeaderCardRepresentation().unsubscribe(this));
        clientManager.getGameContextRepresentation().unsubscribe(this);
    }

}
