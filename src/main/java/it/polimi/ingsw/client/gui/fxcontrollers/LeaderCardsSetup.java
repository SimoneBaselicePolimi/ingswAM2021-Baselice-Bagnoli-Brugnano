package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.gui.fxcontrollers.components.LeaderCard;
import it.polimi.ingsw.client.gui.fxcontrollers.components.Storage;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientMaxResourceNumberRuleRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.client.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaderCardsSetup extends AbstractController{

    @FXML
    public HBox cardsContainer;

    @FXML
    Button confirmButton;

    @FXML
    Label cardsSelection;

    List<LeaderCard> selectedCards = new ArrayList<>();

    @FXML
    void initialize() {

        confirmButton.setVisible(false);
        InitialChoicesServerMessage message =
            (InitialChoicesServerMessage) clientManager.getEntryInContextInfoMap("initialChoicesServerMessage");

        cardsSelection.setText(Localization.getLocalizationInstance().getString(
            "client.gui.leaderCardsSetup.selection",
            message.numberOfLeaderCardsToKeep
        ));

        confirmButton.setText(Localization.getLocalizationInstance().getString("client.gui.leaderCardsSetup.confirmButton"));

        List<LeaderCard> cardsComp = message.leaderCardsGivenToThePlayer.stream()
            .map(LeaderCard::new)
            .collect(Collectors.toList());

        cardsComp.forEach(c -> cardsContainer.getChildren().add(c));

        cardsComp.forEach(c -> c.setOnMouseClicked(
            e -> {
                if(selectedCards.contains(c)){
                    c.deselect();
                    selectedCards.remove(c);
                }
                else{
                    c.select();
                    selectedCards.add(c);
                }
                if(selectedCards.size() == message.numberOfLeaderCardsToKeep)
                    confirmButton.setVisible(true);
                else
                    confirmButton.setVisible(false);
            }
        ));

    }

    @FXML
    void onConfirmButtonPressed(){

        clientManager.addEntryToContextInfoMap("selectedLeaderCards", selectedCards);
        clientManager.loadScene("ResourcesChoiceSetup.fxml");
        Platform.runLater(() -> {
            ClientResourceStorageRepresentation storage = clientManager.getGameContextRepresentation().getPlayerContext(clientManager.getMyPlayer()).getShelves()
                .stream().filter(s -> s.getRules().stream().filter(r -> r instanceof ClientMaxResourceNumberRuleRepresentation).anyMatch(r -> ((ClientMaxResourceNumberRuleRepresentation)r).getMaxResources()==3))
                .findAny().get();
            Storage storageComp = new Storage("MAGAZZINO 3", storage);
            System.out.println(storage.getDescription());
            storage.setResources(Map.of(ResourceType.STONES, 3));

            ClientResourceStorageRepresentation tempStorage = clientManager.getGameContextRepresentation().getPlayerContext(clientManager.getMyPlayer()).getTempStorage();
            Storage tempStorageComp = new Storage("MAGAZZINO TEMPORANEO", tempStorage);

            storageComp.enableResourceRepositioningMode(tempStorage);
            Scene scene = new Scene(new VBox(20, storageComp, tempStorageComp), 500, 500);
            clientManager.getMainStage().setScene(scene);
            clientManager.getMainStage().show();
        });

    }

}
