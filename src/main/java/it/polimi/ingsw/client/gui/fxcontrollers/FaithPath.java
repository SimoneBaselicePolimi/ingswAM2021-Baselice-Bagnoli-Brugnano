package it.polimi.ingsw.client.gui.fxcontrollers;

import com.sun.prism.paint.Color;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientFaithPathRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientVaticanReportSectionRepresentation;
import it.polimi.ingsw.client.servermessage.PlayerNameAlreadyExistsServerMessage;
import it.polimi.ingsw.configfile.FaithPathConfig;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.Map;

public class FaithPath extends GameScene {

    @FXML
    HBox faithPathContainer;

    @FXML
    VBox popeFavorCardsContainer;

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

        //faithPath section
        for (int c = 0; c < faithPathLength; c++) {

            StackPane cell = new StackPane();
            cell.setStyle("-fx-border-color: black");
            cell.setPrefSize(200, 200);
            faithPathContainer.getChildren().add(cell);

            Label numberPosition = new Label();
            numberPosition.setFont(new Font(14));
            numberPosition.setText(String.valueOf(c));
            numberPosition.setAlignment(Pos.BOTTOM_RIGHT);
            cell.getChildren().add(numberPosition);
            StackPane.setAlignment(numberPosition, Pos.BOTTOM_CENTER);

            int victoryPoints = victoryPointsByPosition[c];
            Label victoryPointsByPosition = new Label();
            victoryPointsByPosition.setFont(new Font(12));
            victoryPointsByPosition.setText(String.valueOf(victoryPoints));
            cell.getChildren().add(victoryPointsByPosition);
            StackPane.setAlignment(victoryPointsByPosition, Pos.TOP_RIGHT);


            for (ClientVaticanReportSectionRepresentation vaticanReportSection : vaticanReportSections) {
                if (c >= vaticanReportSection.getSectionInitialPos() && c <= vaticanReportSection.getPopeSpacePos())
                    cell.setBackground(new Background(new BackgroundFill(Paint.valueOf("#7aa9ef"), null, null)));
            }
        }

        //popeCards section
        for (int p = 0; p < clientManager.getGameContextRepresentation().getPlayersOrder().size(); p++){

            HBox cardsBox = new HBox();

            Label playerLabel = new Label();
            playerLabel.setText(Localization.getLocalizationInstance().getString(
                "client.gui.faithPath.playersLabel",
                clientManager.getGameContextRepresentation().getPlayersOrder().get(p).getName()
            ));
            playerLabel.setFont(new Font(20));

            for(int i = 0; i < faithPath.getPopeFavorCards().get(p).size(); i++){

                StackPane card = new StackPane();
                card.setStyle("-fx-border-color: black");
                card.setPrefSize(100, 200);

                Label cardStateLabel = new Label(faithPath.getPopeFavorCards().get(p).get(i).getLocalizedName());
                cardStateLabel.setFont(new Font(12));
                card.getChildren().add(cardStateLabel);
                card.setAlignment(Pos.CENTER);

                cardsBox.getChildren().add(i, card);
            }

            cardsBox.getChildren().add(0, playerLabel);
            popeFavorCardsContainer.getChildren().add(p, cardsBox);
        }

    }
}
