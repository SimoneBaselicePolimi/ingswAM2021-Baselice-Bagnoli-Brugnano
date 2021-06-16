package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.gui.fxcontrollers.components.LeaderCard;
import it.polimi.ingsw.client.servermessage.InitialChoicesServerMessage;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.stream.Collectors;

public class LeaderCardsSetup extends AbstractController{

    @FXML
    public HBox cardsContainer;


    @FXML
    void initialize() {
        InitialChoicesServerMessage message =
            (InitialChoicesServerMessage) clientManager.getEntryInContextInfoMap("initialChoicesMessage");

        List<LeaderCard> cardsComp = message.leaderCardsGivenToThePlayer.stream()
            .map(LeaderCard::new)
            .collect(Collectors.toList());

        cardsComp.forEach(c -> cardsContainer.getChildren().add(c));

        cardsComp.forEach(c -> c.setOnMouseClicked(e -> c.select()));

    }

}
