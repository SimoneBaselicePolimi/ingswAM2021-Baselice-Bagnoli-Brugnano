package it.polimi.ingsw.client.gui.fxcontrollers;

import com.sun.prism.paint.Color;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientFaithPathRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientVaticanReportSectionRepresentation;
import it.polimi.ingsw.configfile.FaithPathConfig;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.List;

public class FaithPath extends GameScene {

    @FXML
    GridPane faithPathContainer;

    final ClientFaithPathRepresentation faithPath;

    final int faithPathLength;

    final List<ClientVaticanReportSectionRepresentation> vaticanReportSections;

    final int[] victoryPointsByPosition;

    public FaithPath() {

        super(1);
        faithPath = clientManager.getGameContextRepresentation().getFaithPath();
        this.faithPathLength = faithPath.getFaithPathLength();
        this.vaticanReportSections = faithPath.getVaticanReportSections();
        this.victoryPointsByPosition = faithPath.getVictoryPointsByPosition();

    }

    @FXML
    private void initialize() {

        for (int c = 0; c < faithPathLength; c++) {

            Pane cell = new Pane();
            cell.setStyle("-fx-border-color: black");
            cell.setPrefSize(50, 100);
            faithPathContainer.addColumn(c, cell);

            Label numberPosition = new Label();
            numberPosition.setFont(new Font(12));
            numberPosition.setText(String.valueOf(c));

//            int victoryPoints = victoryPointsByPosition[c];
//            Label victoryPointsByPosition = new Label();
//            victoryPointsByPosition.setFont(new Font(10));
//            victoryPointsByPosition.setText(String.valueOf(victoryPoints));
//            victoryPointsByPosition.setAlignment(Pos.TOP_RIGHT);

            cell.getChildren().addAll(numberPosition);

            for(ClientVaticanReportSectionRepresentation vaticanReportSection : vaticanReportSections){
                if (c >= vaticanReportSection.getSectionInitialPos() && c <= vaticanReportSection.getPopeSpacePos()){
                    cell.setBackground(new Background(new BackgroundFill(Paint.valueOf("orange"), null, null)));
                }


            }
        }

    }
}
