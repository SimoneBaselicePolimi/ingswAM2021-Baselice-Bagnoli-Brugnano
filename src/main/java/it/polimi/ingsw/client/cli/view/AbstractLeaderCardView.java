package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.utils.Colour;

public abstract class AbstractLeaderCardView extends CliView {
    public static final int LEADER_CARD_ROW_SIZE = 16;
    public static final int LEADER_CARD_COL_SIZE = 30;

    public AbstractLeaderCardView(CliClientManager clientManager) {
        super(clientManager, LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
    }

    public abstract void setProductionColour(ClientProductionRepresentation production, Colour colour);

    public abstract void setBorderColour(Colour borderColour, boolean updateView);

    public abstract void setBorderColourBasedOnState(boolean borderColourBasedOnState, boolean updateView);

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        return super.getContentAsFormattedCharsBuffer();
    }
}
