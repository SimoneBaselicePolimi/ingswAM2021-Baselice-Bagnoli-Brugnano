package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientMaxResourceNumberRuleRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractPlayerDashboardView extends CliView{

    public final static int SPACE_BETWEEN_DECKS = 2;

    protected List<ClientResourceStorageRepresentation> shelves;
    protected ClientResourceStorageRepresentation infiniteChest;
    protected List<ClientResourceStorageRepresentation> leaderStoragesFromActiveCards = new ArrayList<>();
    protected List<ClientProductionRepresentation> baseProductions;

    protected Player dashboardPlayer, activePlayer;
    protected ClientPlayerContextRepresentation dashboardPlayerContext;
    protected List<ClientPlayerOwnedDevelopmentCardDeckRepresentation> playerDecks;

    protected GridView storagesAndBaseProdGrid, devCardDecksGrid;
    protected List<DevCardDashboardDeckView> devCardDashboardDeckViewList;
    protected List<GridView> baseProductionBorderLists;

    protected GameView gameView;

    public AbstractPlayerDashboardView(
        Player dashboardPlayer,
        CliClientManager clientManager,
        GameView gameView,
        int rowSize,
        int columnSize
    ) {
        super(clientManager, rowSize, columnSize);
        this.dashboardPlayer = dashboardPlayer;
        this.gameView = gameView;

        activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
        dashboardPlayerContext = clientManager.getGameContextRepresentation().getPlayerContext(dashboardPlayer);
        playerDecks = dashboardPlayerContext.getDevelopmentCardDecks();

        shelves = dashboardPlayerContext.getShelves().stream()
            .sorted(
                Comparator.comparing(
                    s -> s.getRules().stream()
                        .filter(r -> r instanceof ClientMaxResourceNumberRuleRepresentation)
                        .findAny(),
                    Comparator.comparingInt(r ->
                        r.isPresent() ? ((ClientMaxResourceNumberRuleRepresentation) r.get()).getMaxResources() : 0
                    )
                )
            ).collect(Collectors.toList());

        infiniteChest = dashboardPlayerContext.getInfiniteChest();
        baseProductions = new ArrayList<>(dashboardPlayerContext.getBaseProductions());

        devCardDashboardDeckViewList = new ArrayList<>();
        for (int i = 0; i < playerDecks.size(); i++) {
            devCardDashboardDeckViewList.add(
                new DevCardDashboardDeckView(dashboardPlayer, i, clientManager)
            );
        }

        int r=0;
        baseProductionBorderLists = new ArrayList<>();
        for(ClientProductionRepresentation baseProduction : baseProductions) {
            GridView baseProductionBorder = new GridView(clientManager, 1, 1, 1);
            baseProductionBorderLists.add(baseProductionBorder);
            baseProductionBorder.setBorderStyle(new LineBorderStyle());

            LabelView baseProductionLabel = new LabelView(
                FormattedChar.convertStringToFormattedCharList(
                    Localization.getLocalizationInstance().getString("dashboard.baseProductions")
                        + " " + (r+1) + "\n" + baseProduction.getDescription()
                ),
                clientManager
            );
            baseProductionBorder.setView(0, 0, baseProductionLabel);
            r++;
        }

        shelves.forEach(this::subscribeToRepresentation);
        subscribeToRepresentation(infiniteChest);
    }

    public AbstractPlayerDashboardView(
        Player dashboardPlayer,
        CliClientManager clientManager,
        GameView gameView
    ) {
        this(dashboardPlayer, clientManager, gameView, 0, 0);
    }

    void buildChildGrids() {

        children.clear();

        int colSizeDevCardDeckGrid = SPACE_BETWEEN_DECKS +
            playerDecks.size()*(SPACE_BETWEEN_DECKS + DevCardDashboardDeckView.DEV_CARD_DECK_COL_SIZE);
        int rowSizeDevCardDeckGrid = 2*SPACE_BETWEEN_DECKS + DevCardDashboardDeckView.DEV_CARD_DECK_ROW_SIZE;
        int colSizeStoragesAndBaseProdGrid = columnSize - colSizeDevCardDeckGrid;

        addChildView(devCardDecksGrid, 0, 0);
        devCardDecksGrid.setRowSize(rowSizeDevCardDeckGrid);
        devCardDecksGrid.setColumnSize(colSizeDevCardDeckGrid);

        addChildView(storagesAndBaseProdGrid, 0, colSizeDevCardDeckGrid);
        storagesAndBaseProdGrid.setRowSize(rowSize);
        storagesAndBaseProdGrid.setColumnSize(colSizeStoragesAndBaseProdGrid);

    }

    @Override
    public void setRowSize(int rowSize) {
        super.setRowSize(rowSize);
    }

    @Override
    public void setColumnSize(int columnSize) {
        super.setColumnSize(columnSize);
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        leaderStoragesFromActiveCards = dashboardPlayerContext.getActiveLeaderCards().stream()
            .flatMap(leaderCard -> leaderCard.getResourceStorages().stream())
            .collect(Collectors.toList());
        leaderStoragesFromActiveCards.forEach(this::subscribeToRepresentation);

        devCardDecksGrid = new GridView(clientManager, 1, playerDecks.size(), SPACE_BETWEEN_DECKS);
        storagesAndBaseProdGrid = new GridView(clientManager,2, 1, 0);
        storagesAndBaseProdGrid.setRowWeight(0, 2);

        for (int i = 0; i < playerDecks.size(); i++) {
            devCardDecksGrid.setView(0, i, devCardDashboardDeckViewList.get(i));
        }

        GridView storagesGrid = new GridView(
            clientManager,
            shelves.size() + leaderStoragesFromActiveCards.size() + 1,
            1,
            1
        );
        storagesGrid.setBorderStyle(new LineBorderStyle());
        storagesAndBaseProdGrid.setView(0, 0, storagesGrid);

        for(ClientResourceStorageRepresentation shelf : shelves) {
            LabelView shelfLabel = new LabelView(
                FormattedChar.convertStringToFormattedCharList(
                    Localization.getLocalizationInstance().getString("dashboard.shelf")
                        + " " + (shelves.indexOf(shelf)+1) + ": "
                        + LocalizationUtils.getResourcesListAsCompactString(shelf.getResources())
                ),
                clientManager
            );
            storagesGrid.setView(shelves.indexOf(shelf), 0, shelfLabel);
        }

        LabelView infiniteChestLabel = new LabelView(
            FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString("dashboard.infiniteChest")
                    + ": " + LocalizationUtils.getResourcesListAsCompactString(infiniteChest.getResources())
            ),
            clientManager
        );
        storagesGrid.setView(shelves.size(), 0, infiniteChestLabel);

        for(ClientResourceStorageRepresentation leaderStorage : leaderStoragesFromActiveCards) {
            LabelView leaderLabel = new LabelView(
                FormattedChar.convertStringToFormattedCharList(
                    Localization.getLocalizationInstance().getString("dashboard.leaderStorages")
                        + " " + (leaderStoragesFromActiveCards.indexOf(leaderStorage)+1) + ": "
                        + LocalizationUtils.getResourcesListAsCompactString(leaderStorage.getResources())
                ),
                clientManager
            );
            storagesGrid.setView(
                shelves.size() + (leaderStoragesFromActiveCards.indexOf(leaderStorage)+1),
                0,
                leaderLabel
            );
        }

        GridView baseProductionGrid = new GridView(
            clientManager,
            baseProductions.size(),
            1,
            0
        );
        storagesAndBaseProdGrid.setView(1, 0, baseProductionGrid);

        for(int r = 0; r< baseProductionBorderLists.size(); r++) {
            baseProductionGrid.setView(r, 0, baseProductionBorderLists.get(r));
        }

        buildChildGrids();

        return super.getContentAsFormattedCharsBuffer();
    }

}
