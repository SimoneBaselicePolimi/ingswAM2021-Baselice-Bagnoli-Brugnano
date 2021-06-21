package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.FileManager;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ResourcesChoice extends AnchorPane {

    @FXML
    GridPane container;

    @FXML
    Label totalResourcesLabel;

    @FXML
    Label titleLabel;

    @FXML
    HBox btnLabelHBox;

    @FXML
    Button confirmButton;

    int numberOfTotalResourcesToChoose;
    final List<ResourceType> possibleChoices;
    final Consumer<Map<ResourceType, Integer>> onAllChoicesDone;

    List<IntegerProperty> selectedResourcesPropByResTypeIndex;
    IntegerProperty numOfResourcesAlreadyChosenProp, numOfResourcesLeftToChooseProp;

    public ResourcesChoice(
        int numOfResourcesToChoose,
        List<ResourceType> possibleChoices,
        Consumer<Map<ResourceType, Integer>> onAllChoicesDone
    ) {
        this.numberOfTotalResourcesToChoose = numOfResourcesToChoose;
        this.possibleChoices = possibleChoices;
        this.onAllChoicesDone = onAllChoicesDone;

        selectedResourcesPropByResTypeIndex = new ArrayList<>();
        possibleChoices.forEach(r -> selectedResourcesPropByResTypeIndex.add(new SimpleIntegerProperty(0)));

        this.numOfResourcesAlreadyChosenProp = new SimpleIntegerProperty();
        numOfResourcesAlreadyChosenProp.bind(Bindings.createIntegerBinding(
            () -> selectedResourcesPropByResTypeIndex.stream().mapToInt(ObservableIntegerValue::get).sum(),
            selectedResourcesPropByResTypeIndex.toArray(IntegerProperty[]::new)
        ));

        this.numOfResourcesLeftToChooseProp = new SimpleIntegerProperty();
        numOfResourcesLeftToChooseProp.bind(numOfResourcesAlreadyChosenProp.negate().add(numberOfTotalResourcesToChoose));

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/ResourcesChoice.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    private void initialize() {

        confirmButton.setText(Localization.getLocalizationInstance().getString(
            "client.gui.resourcesChoice.confirmButton"
        ));
        confirmButton.setOnMouseClicked(e -> {
            Map<ResourceType, Integer> selectedResources = new HashMap<>();
            for(int i = 0; i < possibleChoices.size(); i++) {
                int numOfResources = selectedResourcesPropByResTypeIndex.get(i).getValue();
                if(numOfResources > 0)
                    selectedResources.put(possibleChoices.get(i), numOfResources);
            }
            onAllChoicesDone.accept(selectedResources);
        });
        numOfResourcesLeftToChooseProp.addListener( (obv, oldVal, newVal) -> {
            if(!oldVal.equals(newVal)) {
                if (newVal.equals(0))
                    btnLabelHBox.getChildren().add(confirmButton);
                else
                    btnLabelHBox.getChildren().remove(confirmButton);
            }
        });

        if (numOfResourcesLeftToChooseProp.getValue() == 0 && !btnLabelHBox.getChildren().contains(confirmButton))
            btnLabelHBox.getChildren().add(confirmButton);
        else if(numOfResourcesLeftToChooseProp.getValue() > 0)
            btnLabelHBox.getChildren().remove(confirmButton);

        String title;
        if(numberOfTotalResourcesToChoose == 1)
            title = Localization.getLocalizationInstance().getString(
                "client.gui.resourcesChoice.titleLabel.singular"
            );
        else
            title = Localization.getLocalizationInstance().getString(
                "client.gui.resourcesChoice.titleLabel.plural",
                numberOfTotalResourcesToChoose
            );
        titleLabel.setText(title);

        totalResourcesLabel.textProperty().bind(Bindings.createStringBinding(
            () -> Localization.getLocalizationInstance().getString(
                "client.gui.resourcesChoice.selectedResources",
                numOfResourcesAlreadyChosenProp.get(),
                numberOfTotalResourcesToChoose
            ),
            numOfResourcesAlreadyChosenProp
        ));

        for(int r = 0; r < possibleChoices.size(); r++) {

            final IntegerProperty valProp = selectedResourcesPropByResTypeIndex.get(r);

            Button decrementBtn = new Button("<");
            decrementBtn.setPrefWidth(50);
            decrementBtn.setPrefHeight(30);
            decrementBtn.setOnMouseClicked(e -> valProp.setValue(valProp.get() - 1));
            decrementBtn.disableProperty().bind(valProp.isEqualTo(0));

            Button incrementBtn = new Button(">");
            incrementBtn.setPrefWidth(50);
            incrementBtn.setPrefHeight(30);
            incrementBtn.setOnMouseClicked(e -> valProp.setValue(valProp.get() + 1));
            incrementBtn.disableProperty().bind(numOfResourcesLeftToChooseProp.isEqualTo(0));

            HBox hBoxLabelImg = new HBox(10);
            hBoxLabelImg.setPrefHeight(30);
            hBoxLabelImg.setAlignment(Pos.CENTER);

            Label valLabel = new Label();
            valLabel.setFont(new Font(14));
            valLabel.textProperty().bind(
                Bindings.createStringBinding(
                    () -> String.valueOf(valProp.get()),
                    valProp
                )
            );

            ImageView img = new ImageView();
            img.setImage(new Image(FileManager.getFileManagerInstance().loadFXImage(
                possibleChoices.get(r).getIconPathForResourceType()
            )));
            img.setFitHeight(30);
            img.setPreserveRatio(true);
            img.setSmooth(true);
            img.setCache(true);

            hBoxLabelImg.getChildren().addAll(valLabel, img);

            GridPane.setConstraints(decrementBtn, 0, r);
            GridPane.setConstraints(hBoxLabelImg, 1, r);
            GridPane.setConstraints(incrementBtn, 2, r);

            if(r >= container.getRowConstraints().size())
                container.getRowConstraints().add(new RowConstraints(40));

            container.getChildren().addAll(decrementBtn, hBoxLabelImg, incrementBtn);

        }
    }
}
