package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.*;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.utils.FileManager;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableIntegerValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import javax.tools.Tool;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Storage extends AnchorPane implements View {

    final ClientResourceStorageRepresentation storage;

    final List<ResourceType> resOrder;
    final Map<ResourceType, IntegerProperty> numOfResourcesByType;
    final Map<ResourceType, BooleanProperty> isSectionEnabledMap, isSectionVisibleMap;
    final Map<ResourceType, BooleanProperty> isIncrementButtonEnabledMap, isDecrementButtonEnabledMap;
    final Map<ResourceType, BooleanProperty> isIncrementButtonVisibleMap, isDecrementButtonVisibleMap;
    final IntegerProperty totalResourcesInStorage;

    BooleanProperty isResourceRepositioningModeEnabled;
    ObjectProperty<ClientResourceStorageRepresentation> targetStorageProp = new SimpleObjectProperty<>();

    final String title;

    @FXML
    HBox resourcesContainer;

    @FXML
    Label maxNumberRuleLabel;

    @FXML
    Label titleLabel;

    @FXML
    Tooltip rulesInfo;

    @FXML
    StackPane rulesInfoContainer;

    public Storage(String title, ClientResourceStorageRepresentation storage) {
        this.title = title;
        this.storage = storage;

        isResourceRepositioningModeEnabled = new SimpleBooleanProperty(false);

        resOrder = new ArrayList<>();
        numOfResourcesByType = new HashMap<>();
        isSectionEnabledMap = new HashMap<>();
        isSectionVisibleMap = new HashMap<>();
        isIncrementButtonEnabledMap = new HashMap<>();
        isDecrementButtonEnabledMap = new HashMap<>();
        isIncrementButtonVisibleMap = new HashMap<>();
        isDecrementButtonVisibleMap = new HashMap<>();

        for(ResourceType resourceType : ResourceType.values()) {
            resOrder.add(resourceType);
            numOfResourcesByType.put(resourceType, new SimpleIntegerProperty());
            isSectionEnabledMap.put(resourceType, new SimpleBooleanProperty());
            isSectionVisibleMap.put(resourceType, new SimpleBooleanProperty());
            isIncrementButtonEnabledMap.put(resourceType, new SimpleBooleanProperty());
            isDecrementButtonEnabledMap.put(resourceType, new SimpleBooleanProperty());
            isIncrementButtonVisibleMap.put(resourceType, new SimpleBooleanProperty());
            isDecrementButtonVisibleMap.put(resourceType, new SimpleBooleanProperty());
        }
        totalResourcesInStorage = new SimpleIntegerProperty();

        for(ResourceType resourceType : ResourceType.values()) {

            BooleanProperty isSectionEnabled = isSectionEnabledMap.get(resourceType);
            isSectionEnabled.bind(Bindings.createBooleanBinding(
                () -> {
                    if(isResourceRepositioningModeEnabled.get()) {
                        if(
                            storage.getRules().stream()
                                .anyMatch(r -> r instanceof ClientSameResourceTypeRuleRepresentation)
                        ) {
                            Optional<ResourceType> nonZeroResType = numOfResourcesByType.entrySet().stream()
                                .filter(e -> e.getValue().get() > 0)
                                .map(Map.Entry::getKey)
                                .findAny();
                            if(nonZeroResType.isPresent())
                                return nonZeroResType.get().equals(resourceType);
                            else
                                return true;
                        } else {
                            return true;
                        }

                    } else {
                        return true;
                    }
                },
                Stream.concat(
                    Stream.of(isResourceRepositioningModeEnabled),
                    numOfResourcesByType.values().stream()
                ).toArray(Observable[]::new)
            ));

            BooleanProperty isSectionVisible = isSectionVisibleMap.get(resourceType);
            isSectionVisible.bind(Bindings.createBooleanBinding(
                () -> {
                    if(isResourceRepositioningModeEnabled.get()) {

                        Optional<ResourceType> storageWithSpecificType = storage.getRules().stream()
                            .filter(r -> r instanceof ClientSpecificResourceTypeRuleRepresentation)
                            .map(r -> ((ClientSpecificResourceTypeRuleRepresentation)r).getResourceType())
                            .findAny();
                        if(storageWithSpecificType.isPresent()) {
                            if(resourceType.equals(storageWithSpecificType.get()))
                                return true;
                            else
                                return false;
                        } else {
                           return true;
                        }

                    } else {
                        if(numOfResourcesByType.get(resourceType).get() > 0)
                            return true;
                        else
                            return false;
                    }
                },
                isResourceRepositioningModeEnabled,
                numOfResourcesByType.get(resourceType)
            ));

            BooleanProperty isIncrementButtonVisible = isIncrementButtonVisibleMap.get(resourceType);
            isIncrementButtonVisible.bind(isSectionVisible.and(isResourceRepositioningModeEnabled));

            BooleanProperty isIncrementButtonEnabled = isIncrementButtonEnabledMap.get(resourceType);
            isIncrementButtonEnabled.bind(Bindings.createBooleanBinding(
                () -> {
                    if(!isSectionEnabled.get() || !isSectionVisible.get() || !isResourceRepositioningModeEnabled.get())
                        return false;
                    Optional<Integer> maxResources = storage.getRules().stream()
                        .filter(r -> r instanceof ClientMaxResourceNumberRuleRepresentation)
                        .map(r -> ((ClientMaxResourceNumberRuleRepresentation)r).getMaxResources())
                        .findAny();
                    if(maxResources.isPresent() && totalResourcesInStorage.get() == maxResources.get())
                        return false;
                    if(targetStorageProp.get().getResources().containsKey(resourceType) && targetStorageProp.get().getResources().get(resourceType) >= 1)
                        return true;
                    else
                        return false;
                },
                isSectionEnabled,
                totalResourcesInStorage,
                targetStorageProp
            ));

            BooleanProperty isDecrementButtonVisible = isDecrementButtonVisibleMap.get(resourceType);
            isDecrementButtonVisible.bind(isSectionVisible.and(isResourceRepositioningModeEnabled));

            BooleanProperty isDecrementButtonEnabled = isDecrementButtonEnabledMap.get(resourceType);
            isDecrementButtonEnabled.bind(numOfResourcesByType.get(resourceType).isNotEqualTo(0));
        }

        totalResourcesInStorage.bind(Bindings.createIntegerBinding(
            () -> numOfResourcesByType.values().stream().mapToInt(ObservableIntegerValue::get).sum(),
            numOfResourcesByType.values().toArray(IntegerProperty[]::new)
        ));

        updateResources();

        storage.subscribe(this);

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/Storage.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    protected void updateResources() {
        numOfResourcesByType.keySet().forEach(r ->
            numOfResourcesByType.get(r).setValue(storage.getResources().getOrDefault(r, 0))
        );
    }

    public void enableResourceRepositioningMode(ClientResourceStorageRepresentation targetStorage) {
        if(targetStorage == null)
            throw new IllegalArgumentException();
        targetStorage.subscribe(this);
        this.targetStorageProp.setValue(targetStorage);
        isResourceRepositioningModeEnabled.setValue(true);
    }

    public void disableResourceRepositioningMode() {
        targetStorageProp.get().unsubscribe(this);
        isResourceRepositioningModeEnabled.setValue(false);
    }

    @FXML
    private void initialize() {

        titleLabel.setText(title);

        rulesInfo.setText(storage.getRulesDescription());
        if(storage.getRulesDescription().isEmpty())
            rulesInfoContainer.setVisible(false);

        Optional<ClientMaxResourceNumberRuleRepresentation> maxNumberRule = storage.getRules().stream()
            .filter(r -> r instanceof ClientMaxResourceNumberRuleRepresentation)
            .map(r -> (ClientMaxResourceNumberRuleRepresentation)r).findAny();
        if(maxNumberRule.isPresent())
            maxNumberRuleLabel.setText(Localization.getLocalizationInstance().getString(
                "client.gui.repositioningInStorages.rules.info.maxNumber",
                maxNumberRule.get().getMaxResources()
            ));
        else
            maxNumberRuleLabel.setVisible(false);

        for(ResourceType resourceType : resOrder) {

            IntegerProperty resNumProp = numOfResourcesByType.get(resourceType);

            GridPane sectionGridContainer = new GridPane();
            sectionGridContainer.setPrefWidth(50);
            sectionGridContainer.setPrefHeight(100);
            sectionGridContainer.setVgap(3);

            ColumnConstraints c = new ColumnConstraints();
            c.setHalignment(HPos.CENTER);
            sectionGridContainer.getColumnConstraints().add(c);

            sectionGridContainer.getRowConstraints().addAll(
                new RowConstraints(25),
                new RowConstraints(30),
                new RowConstraints(25)
            );

            HBox innerSectionContainer = new HBox(5);
            innerSectionContainer.setPrefHeight(30);
            innerSectionContainer.setAlignment(Pos.CENTER);
            innerSectionContainer.disableProperty().bind(isSectionEnabledMap.get(resourceType).not());
            innerSectionContainer.visibleProperty().bind(isSectionVisibleMap.get(resourceType));


            Label valLabel = new Label();
            valLabel.setFont(new Font(14));
            valLabel.textProperty().bind(
                Bindings.createStringBinding(
                    () -> String.valueOf(resNumProp.get()),
                    resNumProp
                )
            );

            ImageView img = new ImageView();
            img.setImage(new Image(FileManager.getFileManagerInstance().loadFXImage(
                resourceType.getIconPathForResourceType()
            )));
            img.setFitHeight(20);
            img.setPreserveRatio(true);
            img.setSmooth(true);
            img.setCache(true);

            innerSectionContainer.getChildren().addAll(valLabel, img);

            Button incrementButton = new Button();
            incrementButton.setPrefHeight(15);
            incrementButton.setPrefWidth(25);
            incrementButton.setStyle("-fx-shape: \"M -1 0 L 0 -1 L 1 0 z\"");
            incrementButton.disableProperty().bind(isIncrementButtonEnabledMap.get(resourceType).not());
            incrementButton.visibleProperty().bind(isIncrementButtonVisibleMap.get(resourceType));
            incrementButton.setOnMouseClicked(e -> {
                targetStorageProp.get().setResources(
                    ResourceUtils.difference(targetStorageProp.get().getResources(), Map.of(resourceType, 1))
                );
                storage.setResources(
                    ResourceUtils.sum(storage.getResources(), Map.of(resourceType, 1))
                );
            });

            Button decrementButton = new Button();
            decrementButton.setPrefHeight(15);
            decrementButton.setPrefWidth(25);
            decrementButton.setStyle("-fx-shape: \"M -1 0 L 0 1 L 1 0 z\"");
            decrementButton.disableProperty().bind(isDecrementButtonEnabledMap.get(resourceType).not());
            decrementButton.visibleProperty().bind(isDecrementButtonVisibleMap.get(resourceType));
            decrementButton.setOnMouseClicked(e -> {
                targetStorageProp.get().setResources(
                    ResourceUtils.sum(targetStorageProp.get().getResources(), Map.of(resourceType, 1))
                );
                storage.setResources(
                    ResourceUtils.difference(storage.getResources(), Map.of(resourceType, 1))
                );
            });

            GridPane.setConstraints(incrementButton, 0, 0);
            GridPane.setConstraints(innerSectionContainer, 0, 1);
            GridPane.setConstraints(decrementButton, 0, 2);

            sectionGridContainer.getChildren().addAll(incrementButton, innerSectionContainer, decrementButton);

            resourcesContainer.getChildren().add(sectionGridContainer);

        }

    }

    @Override
    public void updateView() {
        updateResources();
        if(isResourceRepositioningModeEnabled.get())
            targetStorageProp.setValue(targetStorageProp.get());

    }

    @Override
    public void destroyView() {
        storage.unsubscribe(this);
    }

}
