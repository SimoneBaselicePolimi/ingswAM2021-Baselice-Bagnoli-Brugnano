package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
import javafx.fxml.FXML;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.localization.Localization;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;


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

        for (int col = 0; col < marketColumns - 1; col++)
            marketContainer.getColumnConstraints().add(
                new ColumnConstraints(100)
            );
        marketContainer.getColumnConstraints().add(
            new ColumnConstraints(50)
        );

        for (int row = 0; row < marketRows - 1; row++)
            marketContainer.getRowConstraints().add(
                new RowConstraints(100)
            );
        marketContainer.getRowConstraints().add(
            new RowConstraints(50)
        );

        for (int col = 0; col < marketColumns; col++) {

            Button columnSelection = new Button("←");
            columnSelection.setPrefWidth(40);
            columnSelection.setPrefHeight(30);
            columnSelection.setOnMouseClicked(e -> selectMarketLine());

            GridPane.setConstraints(columnSelection, marketRows, col);
            GridPane.setHalignment(columnSelection, HPos.CENTER);
            marketContainer.getChildren().add(columnSelection);

        }

        for (int row = 0; row < marketRows; row++) {

            Button rowSelection = new Button("↑");
            rowSelection.setPrefWidth(40);
            rowSelection.setPrefHeight(30);
            rowSelection.setOnMouseClicked(e -> selectMarketLine());

            GridPane.setConstraints(rowSelection, row, marketColumns);
            GridPane.setHalignment(rowSelection, HPos.CENTER);
            marketContainer.getChildren().add(rowSelection);
        }

        marketContainer.setGridLinesVisible(true);

        int numberOfLineForMarbleColour = -1;
        for(ClientMarbleColourRepresentation marble : differentMarbleColours){

            numberOfLineForMarbleColour++;

            legendContainer.getRowConstraints().clear();

            legendContainer.getRowConstraints().add(
                new RowConstraints(50)
            );

            VBox textContainer = new VBox();

            legendContainer.getChildren().add(1, textContainer);

            if (marble.getResourceType().isPresent()) {
                Label marbleResource = new Label(
                    Localization.getLocalizationInstance().getString
                        ("client.gui.market.marbleDescription.marbleResource", marble.getResourceType().get())
                );
                marbleResource.setFont(new Font(15));
                textContainer.getChildren().add(marbleResource);
            }

            if(marble.getFaithPoints() > 0) {
                if (marble.getFaithPoints() == 1) {
                    Label marbleFaithPoints = new Label(
                        Localization.getLocalizationInstance().getString
                            ("client.gui.market.marbleDescription.marbleFaithPoints.singular")
                    );
                    marbleFaithPoints.setFont(new Font(15));
                    textContainer.getChildren().add(marbleFaithPoints);
                } else {
                    Label marbleFaithPoints = new Label(
                        Localization.getLocalizationInstance().getString
                            ("client.gui.market.marbleDescription.marbleFaithPoints.plural", marble.getFaithPoints())
                    );
                    marbleFaithPoints.setFont(new Font(15));
                    textContainer.getChildren().add(marbleFaithPoints);
                }
            }


            if(marble.isSpecialMarble()){
                Label specialMarble = new Label(
                    Localization.getLocalizationInstance().getString
                        ("client.gui.market.marbleDescription.specialMarble")
                );
                specialMarble.setFont(new Font(15));
                textContainer.getChildren().add(specialMarble);
            }
        }

        updateView();
    }

    void selectMarketLine() {
    }

    @Override
    public void updateView() {
        canMyPlayerDoMainAction.setValue(clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION));
    }

    @Override
    public void destroyView() {
        marketRepresentation.unsubscribe(this);
        clientManager.getGameContextRepresentation().unsubscribe(this);
    }
}
