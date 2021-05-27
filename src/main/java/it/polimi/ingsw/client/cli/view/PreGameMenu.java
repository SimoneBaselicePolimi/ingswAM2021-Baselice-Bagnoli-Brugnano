package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.NewCliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;

public class PreGameMenu extends CliView {

    public PreGameMenu(NewCliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    @Override
    protected FormattedCharsBuffer buildMyBuffer() {
        return null;
    }

}
