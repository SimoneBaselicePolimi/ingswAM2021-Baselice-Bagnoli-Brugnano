package it.polimi.ingsw.client.gui.fxcontrollers.components;

import it.polimi.ingsw.client.gui.GuiClientManager;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientMaxResourceNumberRuleRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dashboard extends AnchorPane {

    public final Player dashboardPlayer;
    public final ClientPlayerContextRepresentation playerContext;
    final GuiClientManager clientManager;

    @FXML
    VBox storagesContainer;

    @FXML
    VBox baseProductionsContainer;

    @FXML
    HBox devCardsContainer;

    @FXML
    HBox leaderCardsContainer;

    @FXML
    Label baseProductionsLabel;

    Map<ClientResourceStorageRepresentation, Storage> storageRepresentationToComp;
    List<LeaderCard> playerLeaderCards = new ArrayList<>();
    List<Production> baseProductions = new ArrayList<>();
    List<DevelopmentCardDashboardDeck> decCardDecksList = new ArrayList<>();

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

//        playerContext.getTempStorage().setResources(new HashMap<>(Map.of(ResourceType.COINS, 5)));
//
//        playerContext.getDevelopmentCardDecks().get(0).getCardDeck().add(
//            clientManager.getGameContextRepresentation().getDevelopmentCardsTable().getDeck(
//                DevelopmentCardLevel.FIRST_LEVEL,
//                DevelopmentCardColour.BLUE
//            ).getCardOnTop()
//        );
//        playerContext.getDevelopmentCardDecks().get(0).getCardDeck().add(
//            clientManager.getGameContextRepresentation().getDevelopmentCardsTable().getDeck(
//                DevelopmentCardLevel.SECOND_LEVEL,
//                DevelopmentCardColour.BLUE
//            ).getCardOnTop()
//        );
//        playerContext.getDevelopmentCardDecks().get(0).setCardDeck(playerContext.getDevelopmentCardDecks().get(0).getCardDeck());
//

    }

    @FXML
    private void initialize() {

        baseProductionsLabel.setText(Localization.getLocalizationInstance().getString(
            "client.gui.dashboard.baseProductions"
        ));
        baseProductionsLabel.setFont(new Font(14));

        storageRepresentationToComp = new HashMap<>();

        playerContext.getBaseProductions().forEach(p -> {
            Production baseProd = new Production(p);
            baseProductionsContainer.getChildren().add(baseProd);
            baseProductions.add(baseProd);
        });

        AtomicInteger index = new AtomicInteger(1);
        playerContext.getShelves().stream()
            .sorted(
                Comparator.comparing(
                    s -> s.getRules().stream()
                        .filter(r -> r instanceof ClientMaxResourceNumberRuleRepresentation)
                        .findAny(),
                    Comparator.comparingInt(r ->
                        r.isPresent() ? ((ClientMaxResourceNumberRuleRepresentation) r.get()).getMaxResources() : 0
                    )
                )
            ).forEach(storageRep -> {

                Storage storageComp = new Storage(
                    Localization.getLocalizationInstance().getString("dashboard.shelf")
                        + " " + index.getAndIncrement(),
                    storageRep
                );
                storagesContainer.getChildren().add(storageComp);
                storageRepresentationToComp.put(storageRep, storageComp);
                if(enableRepositioning)
                    storageComp.enableResourceRepositioningMode(playerContext.getTempStorage());
            });

        Storage infiniteChestComp = new Storage(
            Localization.getLocalizationInstance().getString("dashboard.infiniteChest"),
            playerContext.getInfiniteChest()
        );
        storageRepresentationToComp.put(playerContext.getInfiniteChest(), infiniteChestComp);
        storagesContainer.getChildren().add(infiniteChestComp);

        playerContext.getLeaderCardsPlayerOwns().forEach(lc -> {
            LeaderCard leaderCardComp = new LeaderCard(lc);
            playerLeaderCards.add(leaderCardComp);
            leaderCardsContainer.getChildren().add(leaderCardComp);

            if(lc.getState().equals(LeaderCardState.ACTIVE))
                leaderCardComp.getStoragesComp().forEach(s -> {
                    storageRepresentationToComp.put(s.getStorageRepresentation(), s);
                    if (enableRepositioning)
                        s.enableResourceRepositioningMode(playerContext.getTempStorage());
                });

        });

        playerContext.getDevelopmentCardDecks().forEach(d -> {
            DevelopmentCardDashboardDeck deck = new DevelopmentCardDashboardDeck(d);
            devCardsContainer.getChildren().add(deck);
            decCardDecksList.add(deck);
        });

    }

    public List<DevelopmentCardDashboardDeck> getDevelopmentCardDeckComponents() {
        return decCardDecksList;
    }

    public Set<Storage> getAllActiveStoragesComp() {
        return new HashSet<>(storageRepresentationToComp.values());
    }

    public Set<Production> getAllProductionsComp() {
        return Stream.concat(
            Stream.concat(
                decCardDecksList.stream()
                    .map(DevelopmentCardDashboardDeck::getLeaderCardOnTopComp)
                    .filter(Optional::isPresent)
                    .map(cOpt -> cOpt.get().getProductionComp()),
                playerLeaderCards.stream()
                    .filter(l -> l.getLeaderCardRepresentation().getState().equals(LeaderCardState.ACTIVE))
                    .flatMap(l -> l.getProductionsComp().stream())
            ),
            baseProductions.stream()
        ).collect(Collectors.toSet());
    }

    public List<LeaderCard> getPlayerLeaderCards() {
        return playerLeaderCards;
    }
}
