package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.utils.FileManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Localization.getLocalizationInstance().setLocalizationLanguage("it");
        Parent parent = new FXMLLoader().load(FileManager.getFileManagerInstance().loadFileFXML("PlayerRegistration.fxml"));
        Scene scene = new Scene(new StackPane(parent), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}