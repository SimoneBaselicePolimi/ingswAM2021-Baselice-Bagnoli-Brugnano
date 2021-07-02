package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.*;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.storage.MaxResourceNumberRule;
import it.polimi.ingsw.utils.FileManager;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
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

    MapProperty<ResourceType, Integer> targetStorageMap = new SimpleMapProperty<>(
        FXCollections.observableMap(new HashMap<>())
    );

    final String title;

    final boolean compactMode;

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
        this(title, storage, false);
    }

    public Storage(String title, ClientResourceStorageRepresentation storage, boolean compactMode) {
        this.title = title;
        this.storage = storage;
        this.compactMode = compactMode;

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
//                    Optional<Integer> maxResources = storage.getRules().stream()
//                        .filter(r -> r instanceof ClientMaxResourceNumberRuleRepresentation)
//                        .map(r -> ((ClientMaxResourceNumberRuleRepresentation)r).getMaxResources())
//                        .findAny();
//                    if(maxResources.isPresent() && totalResourcesInStorage.get() == maxResources.get())
//                        return false;
                    if(storage.getRuleErrorMessagesIfPresent(Map.of(resourceType, 1)).isPresent())
                        return false;
                    if(targetStorageMap.get().containsKey(resourceType) && targetStorageMap.get().get(resourceType) >= 1)
                        return true;
                    else
                        return false;
                },
                isSectionEnabled,
                isSectionVisible,
                totalResourcesInStorage,
                targetStorageMap
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
        targetStorageMap.clear();
        targetStorageMap.putAll(targetStorageProp.get().getResources());
        //targetStorageProp.get().getResources().forEach( (t, v) -> targetStorageMap.put(t, v));
        isResourceRepositioningModeEnabled.setValue(true);
    }

    public void disableResourceRepositioningMode() {
        targetStorageProp.get().unsubscribe(this);
        targetStorageMap.clear();
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
            sectionGridContainer.setVgap(3);

            ColumnConstraints c = new ColumnConstraints();
            c.setHalignment(HPos.CENTER);
            sectionGridContainer.getColumnConstraints().add(c);


            HBox innerSectionContainer = new HBox(5);
            innerSectionContainer.setPrefHeight(18);
            innerSectionContainer.setAlignment(Pos.CENTER);
            innerSectionContainer.disableProperty().bind(isSectionEnabledMap.get(resourceType).not());
            innerSectionContainer.visibleProperty().bind(isSectionVisibleMap.get(resourceType));

            Label valLabel = new Label();
            valLabel.setMaxHeight(18);
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
            img.setFitHeight(18);
            img.setPreserveRatio(true);
            img.setSmooth(true);
            img.setCache(true);

            innerSectionContainer.getChildren().addAll(valLabel, img);


            if(compactMode) {

                sectionGridContainer.setPrefHeight(26);

                sectionGridContainer.getRowConstraints().addAll(
                    new RowConstraints(2),
                    new RowConstraints(22),
                    new RowConstraints(2)
                );


                GridPane.setConstraints(innerSectionContainer, 0, 1);

                sectionGridContainer.getChildren().addAll(innerSectionContainer);

            } else {

                sectionGridContainer.setPrefHeight(50);

                sectionGridContainer.getRowConstraints().addAll(
                    new RowConstraints(3),
                    new RowConstraints(11),
                    new RowConstraints(22),
                    new RowConstraints(11),
                    new RowConstraints(3)
                );

                Button incrementButton = new Button();
                incrementButton.setStyle(
                    "-fx-shape: \"M -13 0 L 0 -11 L 13 0 z\"; " +
                        "-fx-scale-shape: false;"
                );
                incrementButton.setPrefHeight(11);
                incrementButton.setPrefWidth(26);
                incrementButton.disableProperty().bind(isIncrementButtonEnabledMap.get(resourceType).not());
                incrementButton.visibleProperty().bind(isIncrementButtonVisibleMap.get(resourceType));
                incrementButton.setOnMouseClicked(e -> {
                    storage.setResources(
                        ResourceUtils.sum(storage.getResources(), Map.of(resourceType, 1))
                    );
                    targetStorageProp.get().setResources(
                        ResourceUtils.difference(targetStorageProp.get().getResources(), Map.of(resourceType, 1))
                    );
                });

                Button decrementButton = new Button();
                decrementButton.setStyle(
                    "-fx-shape: \"M -13 0 L 0 11 L 13 0 z\"; " +
                        "-fx-scale-shape: false;"
                );
                decrementButton.setPrefHeight(11);
                decrementButton.setPrefWidth(26);
                decrementButton.disableProperty().bind(isDecrementButtonEnabledMap.get(resourceType).not());
                decrementButton.visibleProperty().bind(isDecrementButtonVisibleMap.get(resourceType));
                decrementButton.setOnMouseClicked(e -> {
                    storage.setResources(
                        ResourceUtils.difference(storage.getResources(), Map.of(resourceType, 1))
                    );
                    targetStorageProp.get().setResources(
                        ResourceUtils.sum(targetStorageProp.get().getResources(), Map.of(resourceType, 1))
                    );
                });

                GridPane.setConstraints(incrementButton, 0, 1);
                GridPane.setConstraints(innerSectionContainer, 0, 2);
                GridPane.setConstraints(decrementButton, 0, 3);

                sectionGridContainer.getChildren().addAll(incrementButton, innerSectionContainer, decrementButton);

            }

            resourcesContainer.getChildren().add(sectionGridContainer);

        }

    }

    @Override
    public void updateView() {
        Platform.runLater(() -> {updateResources();
            if(isResourceRepositioningModeEnabled.get()) {
                targetStorageProp.setValue(targetStorageProp.get());
                targetStorageMap.clear();
                targetStorageMap.putAll(targetStorageProp.get().getResources());
            }
        });
    }

    @Override
    public void destroyView() {
        storage.unsubscribe(this);
    }

    public ClientResourceStorageRepresentation getStorageRepresentation() {
        return storage;
    }

}
