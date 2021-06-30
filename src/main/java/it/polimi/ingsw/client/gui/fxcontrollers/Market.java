package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.client.clientrequest.MarketActionFetchColumnClientRequest;
import it.polimi.ingsw.client.clientrequest.MarketActionFetchRowClientRequest;
import it.polimi.ingsw.client.gui.fxcontrollers.components.Marble;
import it.polimi.ingsw.client.gui.fxcontrollers.components.ResourcesChoice;
import it.polimi.ingsw.client.gui.fxcontrollers.components.ResourcesRepositioning;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientWhiteMarbleSubstitutionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.client.servermessage.ServerMessage;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Market extends GameScene implements View {

    @FXML
    public Button btnEnterPurchaseMode;

    @FXML
    public Button btnExitPurchaseMode;

    @FXML
    public Label legendLabel;

    @FXML
    public GridPane marketContainer;

    @FXML
    public GridPane outMarbleContainer;

    @FXML
    public GridPane legendContainer;

    ClientMarketRepresentation marketRepresentation;

    int marketRows;
    int marketColumns;

    BooleanProperty isBuyFromMarketModeEnabled =  new SimpleBooleanProperty(false);
    BooleanProperty canMyPlayerDoMainAction = new SimpleBooleanProperty(false);
    Set<ClientMarbleColourRepresentation> differentMarbleColours;

    public Market() {
        super(0);
        marketRepresentation = clientManager.getGameContextRepresentation().getMarket();
        this.differentMarbleColours = new HashSet<>(
            clientManager.getGameItemsManager().getAllItemsOfType(ClientMarbleColourRepresentation.class)
        );
        this.marketRows = marketRepresentation.getNumberOfRows();
        this.marketColumns = marketRepresentation.getNumberOfColumns();
    }

    @FXML
    @Override
    protected void initialize() {
        super.initialize();

        legendLabel.setText(Localization.getLocalizationInstance().getString("client.gui.market.legend"));

        marketRepresentation.subscribe(this);
        clientManager.getGameContextRepresentation().subscribe(this);

        btnEnterPurchaseMode.visibleProperty().bind(isBuyFromMarketModeEnabled.not().and(canMyPlayerDoMainAction));
        btnEnterPurchaseMode.setOnMouseClicked(e -> isBuyFromMarketModeEnabled.setValue(true));

        btnExitPurchaseMode.visibleProperty().bind(isBuyFromMarketModeEnabled);
        btnExitPurchaseMode.setOnMouseClicked(e -> isBuyFromMarketModeEnabled.setValue(false));

        marketContainer.getColumnConstraints().clear();
        marketContainer.getRowConstraints().clear();

        for (int col = 0; col < marketColumns; col++)
            marketContainer.getColumnConstraints().add(
                new ColumnConstraints(130)
            );
        marketContainer.getColumnConstraints().add(
            new ColumnConstraints(50)
        );

        for (int row = 0; row < marketRows; row++)
            marketContainer.getRowConstraints().add(
                new RowConstraints(130)
            );
        marketContainer.getRowConstraints().add(
            new RowConstraints(50)
        );

        for (int col = 0; col < marketColumns; col++) {

            Button columnSelection = new Button("↑");
            columnSelection.setPrefWidth(40);
            columnSelection.setPrefHeight(30);
            int finalCol = col;
            columnSelection.setOnMouseClicked(e -> selectMarketColumn(finalCol));

            GridPane.setConstraints(columnSelection, col, marketRows);
            GridPane.setHalignment(columnSelection, HPos.CENTER);
            marketContainer.getChildren().add(columnSelection);

        }

        for (int row = 0; row < marketRows; row++) {

            Button rowSelection = new Button("←");
            rowSelection.setPrefWidth(40);
            rowSelection.setPrefHeight(30);
            int finalRow = row;
            rowSelection.setOnMouseClicked(e -> selectMarketRow(finalRow));

            GridPane.setConstraints(rowSelection, marketColumns, row);
            GridPane.setHalignment(rowSelection, HPos.CENTER);
            marketContainer.getChildren().add(rowSelection);
        }

        for(int row=0; row<marketRows; row++) {
            for (int column = 0; column < marketColumns; column++) {
                Marble marble = new Marble(marketRepresentation.getMatrix()[row][column]);
                GridPane.setConstraints(marble, column, row);
                GridPane.setHalignment(marble, HPos.CENTER);
                marketContainer.getChildren().add(marble);
            }
        }

        Marble outMarble = new Marble(marketRepresentation.getOutMarble());
        GridPane.setConstraints(outMarble, 0, 0);
        GridPane.setHalignment(outMarble, HPos.CENTER);
        outMarbleContainer.getChildren().add(outMarble);

        marketContainer.setGridLinesVisible(true);

        legendContainer.getRowConstraints().clear();
        legendContainer.getColumnConstraints().clear();
        legendContainer.setGridLinesVisible(true);
        ColumnConstraints marbleColumn = new ColumnConstraints(105);
        marbleColumn.setHalignment(HPos.CENTER);
        legendContainer.getColumnConstraints().add(marbleColumn);
        ColumnConstraints descriptionColumn = new ColumnConstraints(155);
        descriptionColumn.setHalignment(HPos.CENTER);
        legendContainer.getColumnConstraints().add(descriptionColumn);

        int numOfRow=0;
        for(ClientMarbleColourRepresentation marble : differentMarbleColours){

            RowConstraints descriptionRow = new RowConstraints(105);
            descriptionRow.setValignment(VPos.CENTER);
            legendContainer.getRowConstraints().add(descriptionRow);

            Marble marbleTest = new Marble(marble);
            GridPane.setConstraints(marbleTest, 0, numOfRow);
            legendContainer.getChildren().add(marbleTest);

            VBox marbleDescription = new VBox();
            marbleDescription.setAlignment(Pos.CENTER);

            if (marble.getResourceType().isPresent()) {
                marbleDescription.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
                    1,
                    marble.getResourceType().get().getIconPathForResourceType(),
                    35,
                    2
                ));
            }

            if(marble.getFaithPoints() > 0) {
                marbleDescription.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
                    1,
                    "faith.png",
                    35,
                    2
                ));
            }

            if(marble.isSpecialMarble()){
                marbleDescription.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
                    1,
                    "starResource.png",
                    35,
                    2
                ));
            }

            GridPane.setConstraints(marbleDescription, 1, numOfRow);
            legendContainer.getChildren().add(marbleDescription);

            numOfRow++;
        }
        updateView();
    }

    private void selectMarketRow(int selectedRow) {
        clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
            new MarketActionFetchRowClientRequest(
                clientManager.getGameContextRepresentation().getActivePlayer(),
                selectedRow
            )
        )).thenCompose(message -> {
            handleMarbleFetchServerAnswer(message);
            return CompletableFuture.completedFuture(null);
        });
    }

    private void selectMarketColumn(int selectedColumn) {
        clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
            new MarketActionFetchColumnClientRequest(
                clientManager.getGameContextRepresentation().getActivePlayer(),
                selectedColumn
            )
        )).thenCompose(message -> {
            handleMarbleFetchServerAnswer(message);
            return CompletableFuture.completedFuture(null);
        });
    }

    private void handleMarbleFetchServerAnswer(ServerMessage serverMessage) {

        ClientPlayerContextRepresentation playerContext = clientManager.getGameContextRepresentation()
            .getPlayerContext(clientManager.getMyPlayer());

        ServerMessageUtils.ifMessageTypeCompute(
            serverMessage,
            GameUpdateServerMessage.class,
            message -> {
                clientManager.setGameState(GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION);
                clientManager.handleGameUpdates(message.gameUpdates);
                Set<ResourceType> possibleSubstitutions = playerContext.getActiveLeaderCards().stream()
                    .flatMap(c -> c.getWhiteMarbleSubstitutions().stream())
                    .map(ClientWhiteMarbleSubstitutionRepresentation::getResourceTypeToSubstitute)
                    .collect(Collectors.toSet());
                int numOfTempStarResources = playerContext.getTempStarResources();
                if(possibleSubstitutions.size() > 1 && numOfTempStarResources > 0) {
                    clientManager.loadScene(new ResourcesChoice(
                        numOfTempStarResources,
                        new ArrayList<>(possibleSubstitutions),
                        resourcesChosen -> {
                            playerContext.getTempStorage().setResources(
                                ResourceUtils.sum(resourcesChosen, playerContext.getTempStorage().getResources())
                            );
                            clientManager.loadScene(new ResourcesRepositioning(
                                clientManager.getMyPlayer(),
                                true,
                                this::sendResourceRepositioningServerMessage
                            ));
                        }
                    ));
                } else {
                    Map<ResourceType, Integer> resourcesChosen;
                    if(possibleSubstitutions.size() == 1) {
                        resourcesChosen = Map.of(possibleSubstitutions.iterator().next(), numOfTempStarResources);
                    } else {
                        resourcesChosen = new HashMap<>();
                    }
                    playerContext.getTempStorage().setResources(
                        ResourceUtils.sum(resourcesChosen, playerContext.getTempStorage().getResources())
                    );
                    clientManager.loadScene(new ResourcesRepositioning(
                        clientManager.getMyPlayer(),
                        true,
                        this::sendResourceRepositioningServerMessage
                    ));
                }
                return null;
            }
        ).elseIfMessageTypeCompute(
            InvalidRequestServerMessage.class,
            message -> {
                //TODO
                //clientManager.tellUser(message.errorMessage);
                clientManager.loadScene("FaithPath.fxml");
                return null;
            }
        ).elseCompute(message -> {
            clientManager.loadScene("FaithPath.fxml");
            return null;
        }).apply();
    }

    private void sendResourceRepositioningServerMessage(
        Set<ClientResourceStorageRepresentation> modifiedStorages,
        Map<ResourceType, Integer> resourcesLeftInTemporaryStorage
    ) {
        Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> resourcesToAddByStorage =
            modifiedStorages.stream()
                .collect(Collectors.toMap(s -> s, ClientResourceStorageRepresentation::getResources));

        clientManager.sendMessageAndGetAnswer(
            new PlayerRequestClientMessage(new ManageResourcesFromMarketClientRequest(
                clientManager.getMyPlayer(),
                resourcesToAddByStorage,
                resourcesLeftInTemporaryStorage
            ))
        ).thenCompose(serverMessage ->
            ServerMessageUtils.ifMessageTypeCompute(
                serverMessage,
                GameUpdateServerMessage.class,
                message -> {
                    clientManager.handleGameUpdates(message.gameUpdates);
                    clientManager.loadScene("Market.fxml");
                    return CompletableFuture.completedFuture(null);
                }
            ).elseIfMessageTypeCompute(
                InvalidRequestServerMessage.class,
                message -> {
                    //TODO
                    //clientManager.tellUser(message.errorMessage);
                    clientManager.loadScene("FaithPath.fxml");
                    return null;
                }
            ).elseCompute(message -> {
                clientManager.loadScene("FaithPath.fxml");
                return null;
            }).apply()
        );
    }

    @Override
    public void updateView() {
        super.updateView();
        canMyPlayerDoMainAction.setValue(clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION));
    }

    @Override
    public void destroyView() {
        super.destroyView();
        marketRepresentation.unsubscribe(this);
        clientManager.getGameContextRepresentation().unsubscribe(this);
    }

}
