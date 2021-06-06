package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.server.model.Player;

import java.util.*;

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

        storagesAndBaseProdGrid = null; //TODO
        devCardDecksGrid = new GridView(clientManager, 1, playerDecks.size(), SPACE_BETWEEN_DECKS);

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
