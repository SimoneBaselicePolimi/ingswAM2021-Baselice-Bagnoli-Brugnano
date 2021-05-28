package it.polimi.ingsw.client.cli.view.grid;

import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;

class LineBorderStyle extends BorderStyle {

    @Override
    protected FormattedCharsBuffer createEmptyBufferWithBorders(GridView gridView) {
        FormattedCharsBuffer gridBuffer = new FormattedCharsBuffer(gridView.getViewRowSize(), gridView.getViewColumnSize());
        return gridBuffer;
    }

}
