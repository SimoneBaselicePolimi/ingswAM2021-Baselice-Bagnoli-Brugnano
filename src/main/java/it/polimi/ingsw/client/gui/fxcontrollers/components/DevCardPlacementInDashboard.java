package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.GuiClientManager;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class DevCardPlacementInDashboard extends AnchorPane {

    public final Consumer<Integer> onPlacementDone;
    public final ClientDevelopmentCardRepresentation developmentCard;

    @FXML
    Label placementTitle;

    @FXML
    GridPane dashboardContainer;

    GuiClientManager clientManager;
    ClientPlayerContextRepresentation playerContext;

    public DevCardPlacementInDashboard(
        ClientDevelopmentCardRepresentation developmentCard,
        Consumer<Integer> onPlacementDone
    ) {
        this.developmentCard = developmentCard;
        this.onPlacementDone = onPlacementDone;

        clientManager = GuiClientManager.getInstance();
        playerContext = clientManager.getGameContextRepresentation().getPlayerContext(clientManager.getMyPlayer());

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/DevCardPlacementInDashboard.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        Dashboard dashboard = new Dashboard(clientManager.getMyPlayer(), false);
        GridPane.setRowIndex(dashboard, 1);
        GridPane.setColumnIndex(dashboard, 0);
        dashboardContainer.getChildren().add(dashboard);

        List<Integer> validDeckIDs = playerContext.getPlayerDashboardDecksForCard(developmentCard);
        for (int deckIndex = 0; deckIndex < dashboard.getDevelopmentCardDeckComponents().size(); deckIndex++) {
            if (validDeckIDs.contains(deckIndex)) {
                dashboard.getDevelopmentCardDeckComponents().get(deckIndex).setCardBordersColour(Colour.YELLOW);
                int finalDeckIndex = deckIndex;
                dashboard.getDevelopmentCardDeckComponents().get(deckIndex).setOnMouseClicked(obv ->
                    onPlacementDone.accept(finalDeckIndex)
                );
            } else {
                dashboard.getDevelopmentCardDeckComponents().get(deckIndex).setCardBordersColour(Colour.GREY);
            }
        }
    }

}
