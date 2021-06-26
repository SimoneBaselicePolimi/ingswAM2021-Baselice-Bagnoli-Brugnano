package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.localization.Localization;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;


public class Market extends GameScene implements View {

    //TODO change in FXML
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

    BooleanProperty isCardPurchaseModeEnabled =  new SimpleBooleanProperty(false);
    BooleanProperty canMyPlayerDoMainAction = new SimpleBooleanProperty(false);

    public Market() {
        super(0);
        marketRepresentation = clientManager.getGameContextRepresentation().getMarket();
        this.nRows = marketRepresentation.getNumberOfRows();
        this.nColumns = marketRepresentation.getNumberOfColumns();
    }

    @FXML
    public void initialize(){

        legendLabel.setText(Localization.getLocalizationInstance().getString("client.gui.table.colours.green"));

        marketRepresentation.subscribe(this);
        clientManager.getGameContextRepresentation().subscribe(this);

        btnEnterPurchaseMode.visibleProperty().bind(isCardPurchaseModeEnabled.not().and(canMyPlayerDoMainAction));
        btnEnterPurchaseMode.setOnMouseClicked(e -> isCardPurchaseModeEnabled.setValue(true));

        btnExitPurchaseMode.visibleProperty().bind(isCardPurchaseModeEnabled);
        btnExitPurchaseMode.setOnMouseClicked(e -> isCardPurchaseModeEnabled.setValue(false));

        for (int i = 0; i < nColumns; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / nColumns);
            marketContainer.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < nRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / nRows);
            marketContainer.getRowConstraints().add(rowConst);
        }

        marketContainer.setGridLinesVisible(true);

        updateView();
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
