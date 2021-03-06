package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.client.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.client.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.client.clientrequest.ProductionActionClientRequest;
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
import it.polimi.ingsw.localization.Localization;
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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PlayerDashboard extends GameScene implements View {

    //prod selection header
    @FXML
    HBox prodSelectionHeader;

    @FXML
    Label resLeftLabel;

    @FXML
    HBox resourcesLeftToThePlayerComp;

    @FXML
    Label starResLabel;

    @FXML
    HBox starResourcesChosenComp;


    //dashboard
    @FXML
    AnchorPane dashboardContainer;


    //buttons in bottom row
    @FXML
    Button activateProductionsButton;

    @FXML
    Button activateSelectedProductionsButton;

    @FXML
    Button cancelButton;

    @FXML
    Button activateLeaderCard;

    @FXML
    Button discardLeaderCard;

    @FXML
    Button cancelActivationLeaderCard;

    @FXML
    Button endTurn;

    Dashboard dashboard;
    ClientPlayerContextRepresentation playerContext;

    BooleanProperty isAnyActionModeEnabled =  new SimpleBooleanProperty(false);

    BooleanProperty isActivateLeaderCardModeEnabled =  new SimpleBooleanProperty(false);
    SetProperty<ClientLeaderCardRepresentation> leaderCardsThePlayerCanActivate = new SimpleSetProperty<>(
        FXCollections.observableSet(new HashSet<>())
    );
    BooleanProperty isDiscardLeaderCardModeEnabled =  new SimpleBooleanProperty(false);

    BooleanProperty isProductionsActivationModeEnabled =  new SimpleBooleanProperty(false);
    SetProperty<Production> selectedProds = new SimpleSetProperty<>(FXCollections.observableSet(new HashSet<>()));
    MapProperty<ResourceType, Integer> resourcesLeftToThePlayer = new SimpleMapProperty<>(FXCollections.observableMap(new HashMap<>()));
    MapProperty<ResourceType, Integer> starResourcesCostChosen = new SimpleMapProperty<>(FXCollections.observableMap(new HashMap<>()));
    MapProperty<ResourceType, Integer> starResourcesRewardChosen = new SimpleMapProperty<>(FXCollections.observableMap(new HashMap<>()));

    BooleanProperty canMyPlayerEndTurn = new SimpleBooleanProperty(false);

    public PlayerDashboard() {
        super(4);
        playerContext = clientManager.getGameContextRepresentation().getPlayerContext(clientManager.getMyPlayer());
    }

    @FXML
    @Override
    protected void initialize() {

        resLeftLabel.setText(Localization.getLocalizationInstance().getString(
            "client.gui.dashboard.resourcesLeft"
        ));
        resLeftLabel.setFont(new Font(14));
        starResLabel.setText(Localization.getLocalizationInstance().getString(
            "client.gui.dashboard.starResourcesChosen"
        ));
        starResLabel.setFont(new Font(14));
        activateProductionsButton.setText(Localization.getLocalizationInstance().getString(
            "client.gui.dashboard.activateProductionsButton"
        ));
        cancelButton.setText(Localization.getLocalizationInstance().getString(
            "client.gui.dashboard.cancelButton"
        ));
        activateSelectedProductionsButton.setText(Localization.getLocalizationInstance().getString(
            "client.gui.dashboard.activateSelectedProductionsButton"
        ));
        activateLeaderCard.setText(Localization.getLocalizationInstance().getString(
            "client.gui.dashboard.activateLeaderCard"
        ));
        discardLeaderCard.setText(Localization.getLocalizationInstance().getString(
            "client.gui.dashboard.discardLeaderCard"
        ));
        cancelActivationLeaderCard.setText(Localization.getLocalizationInstance().getString(
            "client.gui.dashboard.cancelActivationLeaderCard"
        ));
        endTurn.setText(Localization.getLocalizationInstance().getString(
            "client.gui.dashboard.endTurn"
        ));

        dashboard = new Dashboard(clientManager.getMyPlayer(), false);

        dashboard.getPlayerLeaderCards().forEach(comp -> comp.getLeaderCardRepresentation().subscribe(this));

        dashboardContainer.getChildren().add(dashboard);

        activateProductionsButton.visibleProperty().bind(isAnyActionModeEnabled.not().and(canMyPlayerDoMainAction));
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
            clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                new ProductionActionClientRequest(
                    clientManager.getMyPlayer(),
                    selectedProds.stream().map(Production::getProductionRepresentation).collect(Collectors.toSet()),
                    starResourcesCostChosen.get(),
                    starResourcesRewardChosen.get()
                )
            )).thenCompose(serverMessage ->
                ServerMessageUtils.ifMessageTypeCompute(
                    serverMessage,
                    GameUpdateServerMessage.class,
                    message -> {
                        clientManager.setGameState(GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION);
                        clientManager.handleGameUpdates(message.gameUpdates);
                        clientManager.loadScene("PlayerDashboard.fxml");
                        return CompletableFuture.<Void>completedFuture(null);
                    }
                ).elseIfMessageTypeCompute(
                    InvalidRequestServerMessage.class,
                    message -> {
                        clientManager.loadScene("FaithPath.fxml");
                        return CompletableFuture.completedFuture(null);
                    }
                ).elseCompute(message -> {
                    clientManager.loadScene("FaithPath.fxml");
                    return CompletableFuture.completedFuture(null);
                }).apply()
            );
        });

        isAnyActionModeEnabled.bind(
            isActivateLeaderCardModeEnabled.or(isDiscardLeaderCardModeEnabled).or(isProductionsActivationModeEnabled)
        );

        isProductionsActivationModeEnabled.addListener( (obv, oldVal, newVal) -> {
            if(!oldVal && newVal) {
                resourcesLeftToThePlayer.clear();
                resourcesLeftToThePlayer.putAll(playerContext.getTotalResourcesOwnedByThePlayer());
                starResourcesCostChosen.clear();
                starResourcesRewardChosen.clear();
            }
            updateProductions();
        });
        selectedProds.addListener( (InvalidationListener) obv -> updateProductions());

        resourcesLeftToThePlayer.addListener( (InvalidationListener) obv -> updateResourcesLeftToThePlayerComp());
        resourcesLeftToThePlayerComp.visibleProperty().bind(isProductionsActivationModeEnabled);
        prodSelectionHeader.visibleProperty().bind(isProductionsActivationModeEnabled);

        starResLabel.visibleProperty().bind(starResourcesRewardChosen.emptyProperty().not());
        starResourcesChosenComp.visibleProperty().bind(starResourcesRewardChosen.emptyProperty().not());
        starResourcesRewardChosen.addListener( (InvalidationListener) obv -> updateStarResourcesChosenComp());

        activateLeaderCard.visibleProperty().bind(isAnyActionModeEnabled.not().and(isMyPlayerTurn));
        activateLeaderCard.setOnMouseClicked(e -> {
            isActivateLeaderCardModeEnabled.setValue(true);
            updateLeaderCards();
        });

        cancelActivationLeaderCard.visibleProperty().bind(isActivateLeaderCardModeEnabled.or(isDiscardLeaderCardModeEnabled));
        cancelActivationLeaderCard.setOnMouseClicked(e -> {
            if(isActivateLeaderCardModeEnabled.get())
                isActivateLeaderCardModeEnabled.setValue(false);
            else
                isDiscardLeaderCardModeEnabled.setValue(false);
        });

        isActivateLeaderCardModeEnabled.addListener( obv -> updateLeaderCards());
        leaderCardsThePlayerCanActivate.addListener( (InvalidationListener) obv -> updateLeaderCards());

        discardLeaderCard.visibleProperty().bind(isAnyActionModeEnabled.not().and(isMyPlayerTurn));
        discardLeaderCard.setOnMouseClicked(e -> {
            isDiscardLeaderCardModeEnabled.setValue(true);
            updateLeaderCards();
        });


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

    protected void discardLeaderCardAndSendMessageToServer(ClientLeaderCardRepresentation cardToDiscard) {
        clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
            new DiscardLeaderCardClientRequest(
                clientManager.getMyPlayer(),
                cardToDiscard
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

    void updateStarResourcesChosenComp() {
        starResourcesChosenComp.getChildren().clear();

        starResourcesRewardChosen.forEach( (resType, numOFRes) ->
            starResourcesChosenComp.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
                numOFRes,
                resType.getIconPathForResourceType(),
                25,
                4
            ))
        );
    }

    void updateProductions() {
        dashboard.getAllProductionsComp().forEach(pComp -> {
            if (isProductionsActivationModeEnabled.get()) {
                ClientProductionRepresentation prodRepresentation = pComp.getProductionRepresentation();
                if (selectedProds.contains(pComp)) {
                    pComp.setBorderColour(Colour.GREEN);
                    pComp.setOnMouseClicked(obv -> {});
                } else if (checkIfThePlayerHasNecessaryResourcesToActivateProd(prodRepresentation)) {
                    pComp.setBorderColour(Colour.YELLOW);
                    pComp.setOnMouseClicked(obv -> {
                        Map<ResourceType, Integer> resLeft = ResourceUtils.difference(
                            resourcesLeftToThePlayer.get(),
                            prodRepresentation.getResourceCost()
                        );
                        resourcesLeftToThePlayer.clear();
                        resourcesLeftToThePlayer.putAll(resLeft);
                        if(prodRepresentation.getStarResourceCost() > 0) {
                            chooseStarResourcesCost(prodRepresentation).thenAccept(n -> {
                                if (prodRepresentation.getStarResourceReward() > 0)
                                    chooseStarResourcesReward(prodRepresentation);
                            });
                        } else if(prodRepresentation.getStarResourceReward() > 0) {
                            chooseStarResourcesReward(prodRepresentation);
                        }
                        selectedProds.add(pComp);
                    });
                } else {
                    pComp.setBorderColour(Colour.GREY);
                    pComp.setOnMouseClicked(obv -> {});
                }
            } else {
                pComp.setDefaultBorderColour();
                pComp.setOnMouseClicked(obv -> {});
            }
        });
    }

    protected CompletableFuture<Void> chooseStarResourcesCost(ClientProductionRepresentation prodRepresentation) {
        CompletableFuture<Void> resChosen = new CompletableFuture<>();
        Platform.runLater(() -> {
            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            popUpStage.initOwner(clientManager.getMainStage());

            VBox container = new VBox(15);
            container.prefWidth(500);
            container.setAlignment(Pos.CENTER);

            Label popUpTitle = new Label(Localization.getLocalizationInstance().getString(
                "client.gui.dashboard.chooseStarCosts"
            ));
            popUpTitle.setFont(new Font(26));
            container.getChildren().add(popUpTitle);
            container.getChildren().add(new ResourcesChoice(
                prodRepresentation.getStarResourceCost(),
                resourcesLeftToThePlayer.entrySet().stream()
                    .filter(e -> e.getValue() > 0)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList()),
                resourcesChosen -> {
                    if (ResourceUtils.areResourcesAContainedInB(resourcesChosen, resourcesLeftToThePlayer.get())) {
                        Map<ResourceType, Integer> newTotalStarCost =
                            ResourceUtils.sum(resourcesChosen, starResourcesCostChosen.get());
                        starResourcesCostChosen.clear();
                        starResourcesCostChosen.putAll(newTotalStarCost);
                        popUpStage.close();
                        Map<ResourceType, Integer> resLeft = ResourceUtils.difference(
                            resourcesLeftToThePlayer.get(),
                            resourcesChosen
                        );
                        resourcesLeftToThePlayer.clear();
                        resourcesLeftToThePlayer.putAll(resLeft);
                        resChosen.complete(null);
                    } else {
                        popUpStage.close();
                        Stage errorStage = new Stage();
                        errorStage.initModality(Modality.APPLICATION_MODAL);
                        errorStage.initOwner(clientManager.getMainStage());
                        VBox errContainer = new VBox(10);
                        errContainer.prefWidth(300);
                        errContainer.setAlignment(Pos.CENTER);
                        errContainer.getChildren().add(new Label(Localization.getLocalizationInstance().getString(
                            "client.gui.dashboard.notEnoughResourcesErrorMessage"
                        )));
                        Button okBtn = new Button("OK");
                        okBtn.setOnMouseClicked(obv -> {
                            errorStage.close();
                            chooseStarResourcesCost(prodRepresentation)
                                .thenAccept( n -> resChosen.complete(null));
                        });
                        errContainer.getChildren().add(okBtn);
                        errorStage.setScene(new Scene(errContainer, 300, 150));
                        errorStage.show();
                    }
                }
            ));
            popUpStage.setScene(new Scene(container, 500, 400));
            popUpStage.show();
        });
        return resChosen;
    }

    protected void chooseStarResourcesReward(ClientProductionRepresentation prodRepresentation) {
        Platform.runLater(() -> {
            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            popUpStage.initOwner(clientManager.getMainStage());

            VBox container = new VBox(15);
            container.prefWidth(500);
            container.setAlignment(Pos.CENTER);

            Label popUpTitle = new Label(Localization.getLocalizationInstance().getString(
                "client.gui.dashboard.chooseStarRewards"
            ));
            popUpTitle.setFont(new Font(26));
            container.getChildren().add(popUpTitle);
            container.getChildren().add(new ResourcesChoice(
                prodRepresentation.getStarResourceReward(),
                Arrays.stream(ResourceType.values()).collect(Collectors.toList()),
                resourcesChosen -> {
                    popUpStage.close();
                    Map<ResourceType, Integer> newTotalStarResObtained = ResourceUtils.sum(
                        starResourcesRewardChosen.get(),
                        resourcesChosen
                    );
                    starResourcesRewardChosen.clear();
                    starResourcesRewardChosen.putAll(newTotalStarResObtained);
                }
            ));
            popUpStage.setScene(new Scene(container, 500, 400));
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

    protected void updateLeaderCards() {
        dashboard.getPlayerLeaderCards().forEach( leaderCard -> {
            if(
                isActivateLeaderCardModeEnabled.get() &&
                !leaderCard.getLeaderCardRepresentation().getState().equals(LeaderCardState.ACTIVE)
            ) {
                if (leaderCardsThePlayerCanActivate.contains(leaderCard.getLeaderCardRepresentation())) {
                    leaderCard.setBorderColour(Colour.YELLOW);
                    leaderCard.setOnMouseClicked(e -> activateLeaderCardAndSendMessageToServer(
                        leaderCard.getLeaderCardRepresentation()
                    ));
                } else {
                    leaderCard.setBorderColour(Colour.GREY);
                    leaderCard.setOnMouseClicked(e -> {});
                }
            } else if(isDiscardLeaderCardModeEnabled.get() &&
                leaderCard.getLeaderCardRepresentation().getState().equals(LeaderCardState.HIDDEN)
            ) {
                leaderCard.setBorderColour(Colour.YELLOW);
                leaderCard.setOnMouseClicked(e -> discardLeaderCardAndSendMessageToServer(
                    leaderCard.getLeaderCardRepresentation()
                ));
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
