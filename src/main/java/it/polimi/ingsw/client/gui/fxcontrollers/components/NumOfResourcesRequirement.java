package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.fxcontrollers.GuiCompUtils;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientResourceNumberRequirementRepresentation;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class NumOfResourcesRequirement extends AnchorPane {

    @FXML
    HBox numOfResourcesReq;

    final ClientResourceNumberRequirementRepresentation resourceNumberRequirement;

    public NumOfResourcesRequirement(ClientResourceNumberRequirementRepresentation resourceNumberRequirement) {
        this.resourceNumberRequirement = resourceNumberRequirement;

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/NumOfResourcesRequirement.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        numOfResourcesReq.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
            resourceNumberRequirement.getResourceNumber(),
            resourceNumberRequirement.getResourceType().getIconPathForResourceType(),
            24,
            2
            )
        );
    }

}
