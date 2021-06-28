package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.fxcontrollers.GuiCompUtils;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Production extends AnchorPane {

    @FXML
    VBox costsContainer;

    @FXML
    VBox rewardsContainer;

    final ClientProductionRepresentation production;

    public Production(ClientProductionRepresentation production) {
        this.production = production;

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/Production.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    private void initialize() {

        double containerHeight = 90;
        double spacing = 4;

        int maxNumOfResTypes = Math.max(
            production.getResourceCost().size() +
                (production.getStarResourceCost() > 0 ? 1 : 0),
            production.getResourceReward().size() +
                (production.getStarResourceReward() > 0 ? 1 : 0) +
                (production.getFaithReward() > 0 ? 1 : 0)
        );

        double resBoxHeight = Math.min(
            (containerHeight - spacing*(maxNumOfResTypes-1)) / maxNumOfResTypes,
            22
        );

        production.getResourceCost().forEach( (resourceType, numOfResources) ->
            costsContainer.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
                numOfResources,
                resourceType.getIconPathForResourceType(),
                resBoxHeight,
                spacing
            ))
        );

        production.getResourceReward().forEach( (resourceType, numOfResources) ->
            rewardsContainer.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
                numOfResources,
                resourceType.getIconPathForResourceType(),
                resBoxHeight,
                spacing
            ))
        );

        if(production.getStarResourceCost() > 0)
            costsContainer.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
                production.getStarResourceCost(),
                "starResource.png",
                resBoxHeight,
                spacing
            ));

        if(production.getStarResourceReward() > 0)
            rewardsContainer.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
                production.getStarResourceReward(),
                "starResource.png",
                resBoxHeight,
                spacing
            ));

        if(production.getFaithReward() > 0)
            rewardsContainer.getChildren().add(GuiCompUtils.createResourceLabelAndIcon(
                production.getFaithReward(),
                "faith.png",
                resBoxHeight,
                spacing
            ));

    }


}
