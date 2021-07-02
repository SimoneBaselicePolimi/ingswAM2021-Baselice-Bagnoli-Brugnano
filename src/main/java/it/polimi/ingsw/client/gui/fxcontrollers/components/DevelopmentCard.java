package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.fxcontrollers.GuiCompUtils;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.IOException;

public class DevelopmentCard extends AnchorPane implements View {

    @FXML
    GridPane container;

    @FXML
    Label titleLabel;

    @FXML
    Label victoryPointsLabel;

    @FXML
    HBox purchaseCostContainer;

    @FXML
    Label purchaseCostLabel;

    @FXML
    Label levelColourLabel;

    @FXML
    Production cardProdComp;

    ClientDevelopmentCardRepresentation card;

    public DevelopmentCard(ClientDevelopmentCardRepresentation card) {
        this.card = card;
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/DevelopmentCard.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void setBordersColour(Colour colour){
        container.setStyle(String.format(
            "-fx-border-color:#%02x%02x%02x; -fx-border-width: 2; -fx-padding: 6px; -fx-border-radius: 8;",
            colour.r,
            colour.g,
            colour.b
        ));
        container.toFront();
    }

    public void setDefaultBordersColour(){
        container.setStyle(
            "-fx-border-color: black; -fx-border-width: 2; -fx-padding: 6px; -fx-border-radius: 8;"
        );
    }

    public ClientDevelopmentCardRepresentation getLeaderCardRepresentation() {
        return card;
    }

    @FXML
    private void initialize() {

        titleLabel.setText(Localization.getLocalizationInstance().getString("developmentCards.name"));
        victoryPointsLabel.setText("+ " + card.getVictoryPoints());
        victoryPointsLabel.setFont(new Font(15));
        purchaseCostLabel.setText(Localization.getLocalizationInstance().getString("developmentCards.cost"));

        cardProdComp = new Production(card.getProduction());
        GridPane.setColumnIndex(cardProdComp, 0);
        GridPane.setRowIndex(cardProdComp, 2);
        container.getChildren().add(cardProdComp);

        card.getPurchaseCost().forEach( (resType, numOfRes) -> purchaseCostContainer.getChildren().add(
            GuiCompUtils.createResourceLabelAndIcon(numOfRes, resType.getIconPathForResourceType(), 15, 4)
        ));

        levelColourLabel.setText(String.valueOf(card.getLevel().toValue()));
        levelColourLabel.setStyle(String.format(
            "-fx-background-color: \"#%02X%02X%02X\"",
            card.getColour().getUIColour().r,
            card.getColour().getUIColour().g,
            card.getColour().getUIColour().b
        ));

        card.subscribe(this);
    }

    @FXML
    public Production getProductionComp() {
        return cardProdComp;
    }

    @Override
    public void updateView() {
    }

    @Override
    public void destroyView() {
        card.unsubscribe(this);
    }
}
