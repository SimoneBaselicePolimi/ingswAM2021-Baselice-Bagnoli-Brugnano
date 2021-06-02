package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;

import java.util.ArrayList;
import java.util.List;

public class LeaderCardListView extends CliView {

    public static final int SPACE_BETWEEN_CARDS = 2;

    protected List<ClientLeaderCardRepresentation> cardsToView;
    protected List<List<ClientLeaderCardRepresentation>> pages;
    protected int currentPageIndex;
    protected boolean enumerateCards;

    public LeaderCardListView(
        List<ClientLeaderCardRepresentation> cardsToView,
        boolean enumerateCards,
        CliClientManager clientManager,
        int rowSize,
        int columnSize
    ) {
        this(cardsToView, enumerateCards, clientManager);
        this.rowSize = rowSize;
        this.columnSize = columnSize;
    }

    public LeaderCardListView(
        List<ClientLeaderCardRepresentation> cardsToView,
        boolean enumerateCards,
        CliClientManager clientManager
    ) {
        super(clientManager);
        this.cardsToView = cardsToView;
        this.enumerateCards = enumerateCards;

        currentPageIndex = 0;
    }

    public void setPage(int pageIndex) {
        currentPageIndex = pageIndex;
        computePages();
        List<ClientLeaderCardRepresentation> page = pages.get(pageIndex);
        int containerColSize =
            SPACE_BETWEEN_CARDS + page.size()*(LeaderCardView.LEADER_CARD_COL_SIZE + SPACE_BETWEEN_CARDS);
        int containerRowSize = 2*SPACE_BETWEEN_CARDS + LeaderCardView.LEADER_CARD_ROW_SIZE;
        GridView container = new GridView(
            clientManager,
            1,
            page.size(),
            SPACE_BETWEEN_CARDS,
            containerRowSize,
            containerColSize
        );
        for (int i = 0; i < page.size(); i++) {
            container.setView(0, i, new LeaderCardView(clientManager, page.get(i)));
        }
        setCardsContainer(container, (rowSize-containerRowSize)/2, 0);
    }

    protected void computePages() {
        int maxPageSize = (columnSize-SPACE_BETWEEN_CARDS)/(LeaderCardView.LEADER_CARD_COL_SIZE+SPACE_BETWEEN_CARDS);
        pages = new ArrayList<>();
        List<ClientLeaderCardRepresentation> currentPage = new ArrayList<>();
        for(ClientLeaderCardRepresentation card : cardsToView) {
            if (currentPage.size() >= maxPageSize) {
                pages.add(currentPage);
                currentPage = new ArrayList<>();
            }
            currentPage.add(card);
        }
        pages.add(currentPage);
    }

    public ClientLeaderCardRepresentation getLeaderCardByNumber(int enumerationNumber) {
        return cardsToView.get(enumerationNumber-1);
    }

    protected GridView getCardsContainer() {
        return children.size() > 0 ? (GridView) children.get(0).getView() : null;
    }

    protected void setCardsContainer(GridView container, int rowIndex, int colIndex) {
        if(getCardsContainer() != null) {
            getCardsContainer().destroyView();
            children.clear();
        }
        addChildView(container, rowIndex, colIndex);
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        setPage(currentPageIndex);
        return super.getContentAsFormattedCharsBuffer();
    }

}
