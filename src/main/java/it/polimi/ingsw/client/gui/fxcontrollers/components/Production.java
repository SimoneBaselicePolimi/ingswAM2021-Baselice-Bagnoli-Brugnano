package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;
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

        int maxNumOfRes = Math.max(
            production.getResourceCost().size() + production.getStarResourceCost(),
            production.getResourceReward().size() + production.getStarResourceReward()
        );

        double resBoxHeight = Math.min(
            (containerHeight - spacing*(maxNumOfRes-1)) / maxNumOfRes,
            22
        );

        production.getResourceCost().forEach( (resourceType, numOfResources) ->
            costsContainer.getChildren().add(createResourceLabelAndIcon(
                numOfResources,
                resourceType.getIconPathForResourceType(),
                resBoxHeight,
                spacing
            ))
        );

        production.getResourceReward().forEach( (resourceType, numOfResources) ->
            rewardsContainer.getChildren().add(createResourceLabelAndIcon(
                numOfResources,
                resourceType.getIconPathForResourceType(),
                resBoxHeight,
                spacing
            ))
        );

        if(production.getStarResourceCost() > 0)
            costsContainer.getChildren().add(createResourceLabelAndIcon(
                production.getStarResourceCost(),
                "starResource.png",
                resBoxHeight,
                spacing
            ));

        if(production.getStarResourceReward() > 0)
            rewardsContainer.getChildren().add(createResourceLabelAndIcon(
                production.getStarResourceReward(),
                "starResource.png",
                resBoxHeight,
                spacing
            ));

    }

    HBox createResourceLabelAndIcon(int numOfResources, String iconPath, double height, double spacing) {

        HBox container = new HBox(spacing);
        container.setPrefHeight(height);
        container.setAlignment(Pos.CENTER);

        Label valLabel = new Label();
        valLabel.setMaxHeight(height);
        valLabel.setFont(new Font(height-4));
        valLabel.textProperty().setValue(String.valueOf(numOfResources));

        ImageView img = new ImageView();
        img.setImage(new Image(FileManager.getFileManagerInstance().loadFXImage(iconPath)));
        img.setFitHeight(height);
        System.out.println("h" + height);
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);

        container.getChildren().addAll(valLabel, img);

        return container;
    }

}
