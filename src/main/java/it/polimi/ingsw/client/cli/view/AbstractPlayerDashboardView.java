package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractPlayerDashboardView extends CliView{

    public final static int SPACE_BETWEEN_DECKS = 2;

    Player dashboardPlayer, activePlayer;
    ClientPlayerContextRepresentation dashboardPlayerContext;
    List<ClientPlayerOwnedDevelopmentCardDeckRepresentation> playerDecks;

    GridView storagesAndBaseProdGrid, devCardDecksGrid;

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

        devCardDecksGrid = new GridView(clientManager, 1, playerDecks.size(), SPACE_BETWEEN_DECKS);
        storagesAndBaseProdGrid = new GridView(clientManager,2, 1, 0);
        storagesAndBaseProdGrid.setRowWeight(0, 2);

        for (int i = 0; i < playerDecks.size(); i++) {
            devCardDecksGrid.setView(0, i, new DevCardDashboardDeckView(dashboardPlayer, i, clientManager));
        }

        Set<ClientResourceStorageRepresentation> shelves = dashboardPlayerContext.getShelves();
        ClientResourceStorageRepresentation infiniteChest = dashboardPlayerContext.getInfiniteChest();
        Set<ClientResourceStorageRepresentation> leaderStoragesFromActiveCards =
            dashboardPlayerContext.getActiveLeaderCards().stream()
                .flatMap(leaderCard -> leaderCard.getResourceStorages().stream())
                .collect(Collectors.toSet());

        GridView storagesGrid = new GridView(
            clientManager,
            shelves.size() + leaderStoragesFromActiveCards.size() + 1,
            1,
            1
        );
        storagesGrid.setBorderStyle(new LineBorderStyle());
        storagesAndBaseProdGrid.setView(0, 0, storagesGrid);

        int s = 0;
        for(ClientResourceStorageRepresentation shelf : shelves) {
            LabelView shelfLabel = new LabelView(
                FormattedChar.convertStringToFormattedCharList(
                    Localization.getLocalizationInstance().getString("dashboard.shelf")
                        + " " + (s+1) + ": "
                        + LocalizationUtils.getResourcesListAsCompactString(shelf.getResources())
                ),
                clientManager
            );
            storagesGrid.setView(s, 0, shelfLabel);
            s++;
        }

        LabelView infiniteChestLabel = new LabelView(
            FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString("dashboard.infiniteChest")
                    + ": " + LocalizationUtils.getResourcesListAsCompactString(infiniteChest.getResources())
            ),
            clientManager
        );
        storagesGrid.setView(shelves.size(), 0, infiniteChestLabel);

        int l = 1;
        for(ClientResourceStorageRepresentation leaderStorage : leaderStoragesFromActiveCards) {
            LabelView leaderLabel = new LabelView(
                FormattedChar.convertStringToFormattedCharList(
                    Localization.getLocalizationInstance().getString("dashboard.leaderStorages")
                        + l + ": " + LocalizationUtils.getResourcesListAsCompactString(leaderStorage.getResources())
                ),
                clientManager
            );
            storagesGrid.setView(shelves.size() + l, 0, leaderLabel);
            l++;
        }

        GridView baseProductionGrid = new GridView(
            clientManager,
            dashboardPlayerContext.getBaseProductions().size(),
            1,
            1
        );
        baseProductionGrid.setBorderStyle(new LineBorderStyle());
        storagesAndBaseProdGrid.setView(1, 0, baseProductionGrid);

        int r = 0;
        for(ClientProductionRepresentation baseProduction : dashboardPlayerContext.getBaseProductions()) {
            LabelView baseProductionLabel = new LabelView(
                FormattedChar.convertStringToFormattedCharList(
                    Localization.getLocalizationInstance().getString("dashboard.baseProductions")
                    + " " + (r+1) + "\n" + baseProduction.getDescription()
                ),
                clientManager
            );
            baseProductionGrid.setView(r, 0, baseProductionLabel);
            r++;
        }

        buildChildGrids();

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
        buildChildGrids();
    }

    @Override
    public void setColumnSize(int columnSize) {
        super.setColumnSize(columnSize);
        buildChildGrids();
    }

}
