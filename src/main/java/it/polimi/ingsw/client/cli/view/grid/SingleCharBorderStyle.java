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

        drawVerticalBorders(gridView, gridBuffer, borderChar);

        for (int r = -1; r < gridView.numOfRows; r++) {
            for (int b = 0; b < gridView.borderSize; b++) {
                gridBuffer.drawHorizontalLine(
                    b + (r == -1 ? 0 : (gridView.getRowEndIndex(r)+1)),
                    0,
                    gridView.getColEndIndex(gridView.numOfColumns - 1) + gridView.borderSize,
                    borderChar);
            }
        }

        return gridBuffer;
    }

    static void drawVerticalBorders(GridView gridView, FormattedCharsBuffer gridBuffer, FormattedChar borderChar) {
        for (int c = -1; c < gridView.numOfColumns; c++) {
            for (int b = 0; b < gridView.borderSize; b++) {
                gridBuffer.drawVerticalLine(
                    0,
                    gridView.getRowEndIndex(gridView.numOfRows - 1) + gridView.borderSize,
                    b + (c == -1 ? 0 : (gridView.getColEndIndex(c)+1)),
                    borderChar);
            }
        }
    }

}