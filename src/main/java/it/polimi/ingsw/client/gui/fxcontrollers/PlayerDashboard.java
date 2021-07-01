package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.client.gui.fxcontrollers.components.Dashboard;
import it.polimi.ingsw.client.gui.fxcontrollers.components.Production;
import it.polimi.ingsw.client.gui.fxcontrollers.components.ResourcesChoice;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.servermessage.EndTurnServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
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
    public HBox prodSelectionHeader;

    @FXML
    public Label resLeftLabel;

    @FXML
    HBox resourcesLeftToThePlayerComp;

    @FXML
    public Label starResLabel;

    @FXML
    public HBox starResourcesChosenComp;


    //dashboard
    @FXML
    AnchorPane dashboardContainer;


    //buttons in bottom row
    @FXML
    Button activateLeaderCard;

    @FXML
    Button endTurn;

    @FXML
    Button activateProductionsButton;

    @FXML
    Button cancelButton;

    @FXML
    Button activateSelectedProductionsButton;



    Dashboard dashboard;

    BooleanProperty isActivateLeaderCardModeEnabled =  new SimpleBooleanProperty(false);
    SetProperty<ClientLeaderCardRepresentation> leaderCardsThePlayerCanActivate = new SimpleSetProperty<>(
        FXCollections.observableSet(new HashSet<>())
    );

    BooleanProperty isProductionsActivationModeEnabled =  new SimpleBooleanProperty(false);

    SetProperty<Production> selectedProds = new SimpleSetProperty<>(FXCollections.observableSet(new HashSet<>()));
    MapProperty<ResourceType, Integer> resourcesLeftToThePlayer = new SimpleMapProperty<>(FXCollections.observableMap(new HashMap<>()));
    MapProperty<ResourceType, Integer> starResourcesRewardChosen = new SimpleMapProperty<>(FXCollections.observableMap(new HashMap<>()));


    ClientPlayerContextRepresentation playerContext;

    BooleanProperty canMyPlayerEndTurn = new SimpleBooleanProperty(false);

    public PlayerDashboard() {
        super(4);
        playerContext = clientManager.getGameContextRepresentation().getPlayerContext(clientManager.getMyPlayer());
    }

    @FXML
    @Override
    protected void initialize() {
        super.initialize();

        dashboard = new Dashboard(clientManager.getMyPlayer(), false);
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
                starResourcesRewardChosen.clear();
            }
            updateProductions();
        });
        selectedProds.addListener( (InvalidationListener) obv -> updateProductions());

        resourcesLeftToThePlayer.addListener( (InvalidationListener) obv -> {
           updateResourcesLeftToThePlayerComp();
        });

        prodSelectionHeader.visibleProperty().bind(isProductionsActivationModeEnabled);

        starResLabel.visibleProperty().bind(starResourcesRewardChosen.emptyProperty().not());
        starResourcesChosenComp.visibleProperty().bind(starResourcesRewardChosen.emptyProperty().not());
        starResourcesRewardChosen.addListener( (InvalidationListener) obv -> updateStarResourcesChosenComp());

        endTurn.visibleProperty().bind(canMyPlayerEndTurn);
        endTurn.setOnMouseClicked(e -> endMyTurn());

        dashboard.getAllProductionsComp();

        leaderCardsThePlayerCanActivate.addListener( (InvalidationListener) obv -> updateCardsColour());

        updateView();
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
                    clientManager.loadScene("FaithPath.fxml");
                    return CompletableFuture.completedFuture(null);
                }
            ).elseIfMessageTypeCompute(
                InvalidRequestServerMessage.class,
                message -> {
                    clientManager.loadScene("PlayerDashboard.fxml");
                    return CompletableFuture.completedFuture(null);
                }
            ).elseCompute(message -> {
                clientManager.loadScene("PlayerDashboard.fxml");
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

            Label popUpTitle = new Label("PScegli costo star");
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
                        errContainer.getChildren().add(new Label("PNon hai abbastanza risorse!"));
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

            Label popUpTitle = new Label("PScegli risorse da ottenere");
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




    void updateCardsColour() {
//        .forEach( (deckRepresentation, deckComp) -> {
//            if(isCardPurchaseModeEnabled.get()) {
//                if (table.isCardPurchasableByMyPlayer(deckRepresentation.getCardOnTop()))
//                    deckComp.setCardBordersColour(Colour.YELLOW);
//                else
//                    deckComp.setCardBordersColour(Colour.GREY);
//            } else {
//                deckComp.setDefaultBordersColour();
//            }
//        });
    }

    @Override
    public void updateView() {
        super.updateView();
        Platform.runLater(() -> {
            canMyPlayerEndTurn.setValue(clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION));
            leaderCardsThePlayerCanActivate.get().clear();
            leaderCardsThePlayerCanActivate.get().addAll(
                dashboard.playerContext.getLeaderCardsPlayerOwns().stream()
                    .filter(ClientLeaderCardRepresentation::canBeActivated)
                    .collect(Collectors.toList())
            );
        });
    }

    @Override
    public void destroyView() {
        super.destroyView();
        clientManager.getGameContextRepresentation().unsubscribe(this);
    }

}
