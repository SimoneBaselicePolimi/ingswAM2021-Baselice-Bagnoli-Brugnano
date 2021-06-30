package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
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

    public final ClientMarbleColourRepresentation marble;

    public Marble(ClientMarbleColourRepresentation marble) {
        this.marble = marble;

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/Marble.fxml")
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
            Color.color(
                marble.getMarbleColour().get(0).r/255f,
                marble.getMarbleColour().get(0).g/255f,
                marble.getMarbleColour().get(0).b/255f
            )
        ));

        img.setEffect(lighting);
        this.getChildren().add(img);
    }

}
