package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameActionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameHistoryRepresentation;
import it.polimi.ingsw.client.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.List;

public class GameHistory extends GameScene implements View {

    @FXML
    Label historyText;

    final ClientGameHistoryRepresentation gameHistory;

    public GameHistory() {
        super(3);
        gameHistory = clientManager.getGameHistoryRepresentation();
    }

    @FXML
    @Override
    protected void initialize() {
        super.initialize();
        gameHistory.subscribe(this);
        updateView();
    }

    @Override
    public void updateView() {
        super.updateView();
        Platform.runLater(() -> {
            List<ClientGameActionRepresentation> gameActions = clientManager.getGameHistoryRepresentation().getGameActions();
            historyText.setText(
                gameActions.stream().map(ClientGameActionRepresentation::getActionMessage)
                    .reduce("", (a, t) -> a + "- " + t + "\n")
            );
            historyText.setFont(new Font(17));
            historyText.setWrapText(true);
        });
    }

    @Override
    public void destroyView() {
        super.destroyView();
        gameHistory.unsubscribe(this);
    }

}
