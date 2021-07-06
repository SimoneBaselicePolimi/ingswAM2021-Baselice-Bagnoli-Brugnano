package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.utils.Colour;

/**
 * View representing the leader cards of a player that are covered:
 * if a player does not activate his cards, the other players cannot see the ones not yet activated
 */
public class CoveredLeaderCardView extends AbstractLeaderCardView{

    protected GridView cardGrid;

    public CoveredLeaderCardView(CliClientManager clientManager) {
        super(clientManager);

        cardGrid = new GridView(clientManager, 1, 1, 1, LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        cardGrid.setBorderStyle(new LineBorderStyle());
        addChildView(cardGrid, 0, 0);
    }

    @Override
    public void setProductionColour(ClientProductionRepresentation production, Colour colour) {

    }

    @Override
    public void setBorderColour(Colour borderColour, boolean updateView) {

    }

    @Override
    public void setBorderColourBasedOnState(boolean borderColourBasedOnState, boolean updateView) {

    }

}
