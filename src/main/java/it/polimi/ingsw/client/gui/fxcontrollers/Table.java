package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.gameupdate.ClientShuffledDevelopmentCardDeckOnTableUpdate;
import it.polimi.ingsw.client.gui.fxcontrollers.components.DevelopmentCard;
import it.polimi.ingsw.client.gui.fxcontrollers.components.DevelopmentCardTableDeck;
import it.polimi.ingsw.client.gui.fxcontrollers.components.LeaderCard;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.utils.Colour;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


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
    BooleanProperty isMyPlayerTheActivePlayer = new SimpleBooleanProperty(false);

    SetProperty<ClientDevelopmentCardRepresentation> purchasableCards = new SimpleSetProperty<>();

    Map<ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>, DevelopmentCardTableDeck> deckRepresentationToComponent = new HashMap<>();

    int nRows;

    int nColumns;

    public Table() {
        super(2);
        table =  clientManager.getGameContextRepresentation().getDevelopmentCardsTable();
        table.setPurchasableCards(Set.of(
            table.getDeck(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE).getCardOnTop(),
            table.getDeck(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE).getCardOnTop()
        ));
        this.nRows = DevelopmentCardLevel.values().length;
        this.nColumns = DevelopmentCardColour.values().length;

        isMyPlayerTheActivePlayer.setValue(clientManager.getGameContextRepresentation().getActivePlayer().equals(clientManager.getMyPlayer()));


//        new Thread(() -> {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            table.setPurchasableCards(Set.of(
//                table.getDeck(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE).getCardOnTop(),
//                table.getDeck(DevelopmentCardLevel.SECOND_LEVEL, DevelopmentCardColour.YELLOW).getCardOnTop()
//            ));
//        }).start();

    }

    @FXML
    public void initialize(){

        table.subscribe(this);
        clientManager.getGameContextRepresentation().subscribe(this);

        btnEnterPurchaseMode.visibleProperty().bind(isCardPurchaseModeEnabled.not().and(isMyPlayerTheActivePlayer));
        btnEnterPurchaseMode.setOnMouseClicked(e -> isCardPurchaseModeEnabled.setValue(true));

        btnExitPurchaseMode.visibleProperty().bind(isCardPurchaseModeEnabled);
        btnExitPurchaseMode.setOnMouseClicked(e -> isCardPurchaseModeEnabled.setValue(false));

        tableContainer.setGridLinesVisible(true);

        for(DevelopmentCardLevel level : DevelopmentCardLevel.values())
            for(DevelopmentCardColour colour : DevelopmentCardColour.values()) {
                ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> deck = table.getDeck(level, colour);
                DevelopmentCardTableDeck deckComp = new DevelopmentCardTableDeck(
                    deck,
                    (cardRepresentation) -> {
                        if(isCardPurchaseModeEnabled.get() && purchasableCards.get().contains(cardRepresentation)) {
                            //TODO send clientRequest
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
                if (table.isCardPurchasable(deckRepresentation.getCardOnTop()))
                    deckComp.setCardBordersColour(Colour.GREEN);
                else
                    deckComp.setCardBordersColour(Colour.GREY);
            } else {
                deckComp.removeCardBorders();
            }
        });
    }

    @Override
    public void updateView() {
        isMyPlayerTheActivePlayer.setValue(clientManager.getGameContextRepresentation().getActivePlayer().equals(clientManager.getMyPlayer()));
        purchasableCards.get().clear();
        purchasableCards.get().addAll(table.getPurchasableCards());
    }

    @Override
    public void destroyView() {
        table.unsubscribe(this);
        clientManager.getGameContextRepresentation().unsubscribe(this);
    }
}
