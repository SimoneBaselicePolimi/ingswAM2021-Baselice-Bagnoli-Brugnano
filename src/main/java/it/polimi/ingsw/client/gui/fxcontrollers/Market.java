package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import javafx.fxml.FXML;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.localization.Localization;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


public class Market extends GameScene implements View {

    @FXML
    public Button btnEnterPurchaseMode;

    @FXML
    public Button btnExitPurchaseMode;

    @FXML
    public Label legendLabel;

    @FXML
    public GridPane marketContainer;

    ClientMarketRepresentation marketRepresentation;

    int nRows;
    int nColumns;

    BooleanProperty isBuyFromMarketModeEnabled =  new SimpleBooleanProperty(false);
    BooleanProperty canMyPlayerDoMainAction = new SimpleBooleanProperty(false);

    public Market() {
        super(0);
        marketRepresentation = clientManager.getGameContextRepresentation().getMarket();
        this.nRows = marketRepresentation.getNumberOfRows();
        this.nColumns = marketRepresentation.getNumberOfColumns();
    }

    @FXML
    @Override
    protected void initialize() {
        super.initialize();

        legendLabel.setText(Localization.getLocalizationInstance().getString("client.gui.table.colours.green"));

        marketRepresentation.subscribe(this);
        clientManager.getGameContextRepresentation().subscribe(this);

        btnEnterPurchaseMode.visibleProperty().bind(isBuyFromMarketModeEnabled.not().and(canMyPlayerDoMainAction));
        btnEnterPurchaseMode.setOnMouseClicked(e -> isBuyFromMarketModeEnabled.setValue(true));

        btnExitPurchaseMode.visibleProperty().bind(isBuyFromMarketModeEnabled);
        btnExitPurchaseMode.setOnMouseClicked(e -> isBuyFromMarketModeEnabled.setValue(false));

        for (int col = 0; col < nColumns; col++) {

            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / nColumns);
            marketContainer.getColumnConstraints().add(colConst);

            Button columnSelection = new Button("↓");
            columnSelection.setPrefWidth(50);
            columnSelection.setPrefHeight(30);
            columnSelection.setOnMouseClicked(e -> selectMarketLine());

            GridPane.setConstraints(columnSelection, 0, col);
            marketContainer.getChildren().add(columnSelection);

        }

        for (int row = 0; row < nRows; row++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / nRows);
            marketContainer.getRowConstraints().add(rowConst);

            Button rowSelection = new Button("→");
            rowSelection.setPrefWidth(50);
            rowSelection.setPrefHeight(30);

            GridPane.setConstraints(rowSelection, row, 0);
            marketContainer.getChildren().add(rowSelection);
        }

        marketContainer.setGridLinesVisible(true);

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
