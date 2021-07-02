package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.fxcontrollers.GuiCompUtils;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientDevelopmentCardCostDiscountRepresentation;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class DevCardCostDiscount extends AnchorPane {

    @FXML
    HBox discountContainer;

    final ClientDevelopmentCardCostDiscountRepresentation costDiscount;

    public DevCardCostDiscount(ClientDevelopmentCardCostDiscountRepresentation costDiscount) {
        this.costDiscount = costDiscount;

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/DevCardCostDiscount.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    private void initialize() {
        discountContainer.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
            - costDiscount.getAmountToDiscount(),
            costDiscount.getResourceType().getIconPathForResourceType(),
            40,
            2
            )
        );
    }

}
