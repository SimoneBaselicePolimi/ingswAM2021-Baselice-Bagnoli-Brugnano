package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.CliColour;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;

public class LeaderCardView extends CliView {

    public static int LEADER_CARD_ROW_SIZE = 8;
    public static int LEADER_CARD_COL_SIZE = 18;

    protected boolean isSelected = false;

    protected ClientLeaderCardRepresentation card;

    public LeaderCardView(CliClientManager clientManager, ClientLeaderCardRepresentation card) {
        super(clientManager, LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        this.card = card;
    }

    public LeaderCardView(CliClientManager clientManager, ClientLeaderCardRepresentation card, boolean isSelected) {
        super(clientManager, LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        this.card = card;
        this.isSelected = isSelected;
    }

    @Override
    protected FormattedCharsBuffer buildMyBuffer() {
        FormattedCharsBuffer buffer = new FormattedCharsBuffer(LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        if(isSelected)
            buffer.setDefaultFormattedChar(new FormattedChar(' ', CliColour.WHITE, CliColour.GREEN));
        else
            buffer.setDefaultFormattedChar(new FormattedChar(' ', CliColour.WHITE, CliColour.GREY));
        return buffer;
    }

    public void selectCard(int cardNumber) {
        isSelected = true;
        updateView();
    }

    public void deselectCard(int cardNumber) {
        isSelected = false;
        updateView();
    }

}
