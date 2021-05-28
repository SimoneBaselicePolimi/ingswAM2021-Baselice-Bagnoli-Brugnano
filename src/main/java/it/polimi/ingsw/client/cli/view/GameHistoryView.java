package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;

public class GameHistoryView extends CliView {


    public GameHistoryView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    public GameHistoryView(CliClientManager clientManager) {
        super(clientManager);
    }

    @Override
    protected FormattedCharsBuffer buildMyBuffer() {
        //clientManager.getGameHistoryRepresentation()
        return null;
    }

}
