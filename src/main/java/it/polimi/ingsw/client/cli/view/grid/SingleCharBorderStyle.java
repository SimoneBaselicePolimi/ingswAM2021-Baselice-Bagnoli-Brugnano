package it.polimi.ingsw.client.cli.view.grid;

import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;

class SingleCharBorderStyle extends BorderStyle {

    FormattedChar borderChar;

    public SingleCharBorderStyle(FormattedChar borderChar) {
        this.borderChar = borderChar;
    }

    @Override
    protected FormattedCharsBuffer createEmptyBufferWithBorders(GridView gridView) {
        FormattedCharsBuffer gridBuffer = new FormattedCharsBuffer(gridView.getViewRowSize(), gridView.getViewColumnSize());
        return gridBuffer;
    }

}
