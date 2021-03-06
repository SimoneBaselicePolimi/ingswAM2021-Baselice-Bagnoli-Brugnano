package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.client.gui.fxcontrollers.components.DevCardPlacementInDashboard;
import it.polimi.ingsw.client.gui.fxcontrollers.components.DevelopmentCardTableDeck;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.utils.Colour;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class Table extends GameScene implements View {

    static final Map<DevelopmentCardLevel, Integer> levelToRow = Map.of(
        DevelopmentCardLevel.FIRST_LEVEL, 1,
        DevelopmentCardLevel.SECOND_LEVEL, 2,
        DevelopmentCardLevel.THIRD_LEVEL, 3
    );

    static final Map<DevelopmentCardColour, Integer> colourToColumn = Map.of(
        DevelopmentCardColour.GREEN, 1,
        DevelopmentCardColour.BLUE, 2,
        DevelopmentCardColour.YELLOW, 3,
        DevelopmentCardColour.PURPLE, 4
    );

    @FXML
    public Label greenLabel;

    @FXML
    public Label blueLabel;

    @FXML
    public Label yellowLabel;

    @FXML
    public Label purpleLabel;

    @FXML
    public Label l1Label;

    @FXML
    public Label l2Label;

    @FXML
    public Label l3Label;

    @FXML
    GridPane tableContainer;

    @FXML
    public Button btnEnterPurchaseMode;

    @FXML
    public Button btnExitPurchaseMode;

    ClientDevelopmentCardsTableRepresentation table;

    BooleanProperty isCardPurchaseModeEnabled =  new SimpleBooleanProperty(false);

    SetProperty<ClientDevelopmentCardRepresentation> purchasableCards = new SimpleSetProperty<>(
        FXCollections.observableSet(new HashSet<>())
    );

    Map<ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>, DevelopmentCardTableDeck> deckRepresentationToComponent = new HashMap<>();

    int nRows;

    int nColumns;

    public Table() {
        super(2);

        table =  clientManager.getGameContextRepresentation().getDevelopmentCardsTable();
//        table.setPurchasableCards(Set.of(
//            table.getDeck(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE).getCardOnTop(),
//            table.getDeck(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.BLUE).getCardOnTop()
//        ));
        this.nRows = DevelopmentCardLevel.values().length;
        this.nColumns = DevelopmentCardColour.values().length;

    }

    @FXML
    @Override
    protected void initialize(){

        super.initialize();

        greenLabel.setText(Localization.getLocalizationInstance().getString("client.gui.table.colours.green"));
        blueLabel.setText(Localization.getLocalizationInstance().getString("client.gui.table.colours.blue"));
        yellowLabel.setText(Localization.getLocalizationInstance().getString("client.gui.table.colours.yellow"));
        purpleLabel.setText(Localization.getLocalizationInstance().getString("client.gui.table.colours.purple"));
        l1Label.setText(Localization.getLocalizationInstance().getString("client.gui.table.levels.first"));
        l2Label.setText(Localization.getLocalizationInstance().getString("client.gui.table.levels.second"));
        l3Label.setText(Localization.getLocalizationInstance().getString("client.gui.table.levels.third"));
        btnEnterPurchaseMode.setText(Localization.getLocalizationInstance().getString("client.gui.table.buttons.btnEnterPurchaseMode"));
        btnExitPurchaseMode.setText(Localization.getLocalizationInstance().getString("client.gui.table.buttons.btnExitPurchaseMode"));

        table.subscribe(this);
        clientManager.getGameContextRepresentation().subscribe(this);

        btnEnterPurchaseMode.visibleProperty().bind(isCardPurchaseModeEnabled.not().and(canMyPlayerDoMainAction));
        btnEnterPurchaseMode.setOnMouseClicked(e -> isCardPurchaseModeEnabled.setValue(true));

        btnExitPurchaseMode.visibleProperty().bind(isCardPurchaseModeEnabled);
        btnExitPurchaseMode.setOnMouseClicked(e -> isCardPurchaseModeEnabled.setValue(false));

        for(DevelopmentCardLevel level : DevelopmentCardLevel.values())
            for(DevelopmentCardColour colour : DevelopmentCardColour.values()) {
                ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> deck = table.getDeck(level, colour);
                DevelopmentCardTableDeck deckComp = new DevelopmentCardTableDeck(
                    deck,
                    (cardRepresentation) -> {
                        if(isCardPurchaseModeEnabled.get() && purchasableCards.get().contains(cardRepresentation)) {
                            clientManager.loadScene(new DevCardPlacementInDashboard(
                                cardRepresentation,
                                deckIndex -> {
                                    clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                                        new DevelopmentActionClientRequest(
                                            clientManager.getGameContextRepresentation().getActivePlayer(),
                                            cardRepresentation,
                                            deckIndex
                                        )
                                    )).thenCompose(serverMessage ->
                                        ServerMessageUtils.ifMessageTypeCompute(
                                            serverMessage,
                                            GameUpdateServerMessage.class,
                                            message -> {
                                                clientManager.setGameState(GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION);
                                                clientManager.handleGameUpdates(message.gameUpdates);
                                                clientManager.loadScene("PlayerDashboard.fxml");
                                                return CompletableFuture.<Void>completedFuture(null);
                                            }
                                        ).elseIfMessageTypeCompute(
                                            InvalidRequestServerMessage.class,
                                            message -> CompletableFuture.completedFuture(null)
                                        ).elseCompute(message -> CompletableFuture.completedFuture(null))
                                            .apply()
                                    );
                                }
                            ));
                        }
                    }
                );
                deckRepresentationToComponent.put(deck, deckComp);
                GridPane.setRowIndex(deckComp, levelToRow.get(level));
                GridPane.setColumnIndex(deckComp, colourToColumn.get(colour));
                tableContainer.getChildren().add(deckComp);
            }

        purchasableCards.addListener( (InvalidationListener) obv -> updateCardsColour());
        isCardPurchaseModeEnabled.addListener( obv -> updateCardsColour() );

        updateView();

    }

    void updateCardsColour() {
        deckRepresentationToComponent.forEach( (deckRepresentation, deckComp) -> {
            if(isCardPurchaseModeEnabled.get()) {
                if (table.isCardPurchasableByMyPlayer(deckRepresentation.getCardOnTop()))
                    deckComp.setCardBordersColour(Colour.YELLOW);
                else
                    deckComp.setCardBordersColour(Colour.GREY);
            } else {
                deckComp.setDefaultBordersColour();
            }
        });
    }

    @Override
    public void updateView() {
        super.updateView();
        Platform.runLater(() -> {
            canMyPlayerDoMainAction.setValue(clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION));
            purchasableCards.get().clear();
            purchasableCards.get().addAll(table.getAllPurchasableCardsForMyPlayer());
        });
    }

    @Override
    public void destroyView() {
        super.destroyView();
        table.unsubscribe(this);
        clientManager.getGameContextRepresentation().unsubscribe(this);
    }
}
