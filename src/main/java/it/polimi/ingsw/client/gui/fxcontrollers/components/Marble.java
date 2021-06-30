package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class Marble extends AnchorPane {

    public Marble() {

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/Dashboard.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    private void initialize() {

        ImageView img = new ImageView();
        img.setImage(new Image(FileManager.getFileManagerInstance().loadFXImage("whiteMarble.png")));
        img.setFitHeight(100);
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);

        Lighting lighting = new Lighting();
        lighting.setDiffuseConstant(1.0);
        lighting.setSpecularConstant(0.0);
        lighting.setSpecularExponent(0.0);
        lighting.setSurfaceScale(0.0);
        lighting.setLight(new Light.Distant(
            45,
            45,
            Color.color(Colour.GREEN.r, Colour.GREEN.g, Colour.GREEN.b)
        ));

        img.setEffect(lighting);
        this.getChildren().add(img);
    }

}
