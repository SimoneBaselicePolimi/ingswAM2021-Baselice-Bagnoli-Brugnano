package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.utils.FileManager;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class GuiCompUtils {

    public static HBox createResourceLabelAndIcon(int numOfResources, String iconPath, double height, double spacing) {

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
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);

        container.getChildren().addAll(valLabel, img);

        return container;
    }


}
