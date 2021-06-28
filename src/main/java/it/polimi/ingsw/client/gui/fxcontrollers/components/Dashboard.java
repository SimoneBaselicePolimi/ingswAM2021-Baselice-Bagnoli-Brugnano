package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.GuiClientManager;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dashboard extends AnchorPane {

    final Player dashboardPlayer;
    final ClientPlayerContextRepresentation playerContext;
    final GuiClientManager clientManager;

    @FXML
    VBox storagesContainer;

    @FXML
    VBox baseProductionsContainer;

    @FXML
    HBox devCardsContainer;

    Map<ClientResourceStorageRepresentation, Storage> storageRepresentationToComp;
    public final boolean enableRepositioning;

    public Dashboard(Player dashboardPlayer, boolean enableRepositioning) {
        this.dashboardPlayer = dashboardPlayer;
        this.enableRepositioning = enableRepositioning;
        clientManager = GuiClientManager.getInstance();
        playerContext = clientManager.getGameContextRepresentation().getPlayerContext(dashboardPlayer);

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(
                FileManager.getFileManagerInstance().loadFileFXML("components/Dashboard.fxml")
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        playerContext.getTempStorage().setResources(new HashMap<>(Map.of(ResourceType.COINS, 5)));

        playerContext.getDevelopmentCardDecks().get(0).getCardDeck().add(
            clientManager.getGameContextRepresentation().getDevelopmentCardsTable().getDeck(
                DevelopmentCardLevel.FIRST_LEVEL,
                DevelopmentCardColour.BLUE
            ).getCardOnTop()
        );
        playerContext.getDevelopmentCardDecks().get(0).getCardDeck().add(
            clientManager.getGameContextRepresentation().getDevelopmentCardsTable().getDeck(
                DevelopmentCardLevel.SECOND_LEVEL,
                DevelopmentCardColour.BLUE
            ).getCardOnTop()
        );
        playerContext.getDevelopmentCardDecks().get(0).setCardDeck(playerContext.getDevelopmentCardDecks().get(0).getCardDeck());


    }

    @FXML
    private void initialize() {

        storageRepresentationToComp = new HashMap<>();

        playerContext.getBaseProductions().forEach(p -> baseProductionsContainer.getChildren().add(new Production(p)));

        playerContext.getShelves().forEach(storageRep -> {
            Storage storageComp = new Storage("test", storageRep);
            storagesContainer.getChildren().add(storageComp);
            storageRepresentationToComp.put(storageRep, storageComp);
            if(enableRepositioning)
                storageComp.enableResourceRepositioningMode(playerContext.getTempStorage());
        });

        Storage infiniteChestComp = new Storage("Infinite Chest", playerContext.getInfiniteChest());
        storageRepresentationToComp.put(playerContext.getInfiniteChest(), infiniteChestComp);
        storagesContainer.getChildren().add(infiniteChestComp);

        playerContext.getDevelopmentCardDecks().forEach(d -> devCardsContainer.getChildren().add(
            new DevelopmentCardDashboardDeck(d)
        ));

    }

    public Set<Storage> getAllStoragesComp() {
        return new HashSet<>(storageRepresentationToComp.values());
    }

}
