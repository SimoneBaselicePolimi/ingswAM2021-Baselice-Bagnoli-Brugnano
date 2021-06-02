package it.polimi.ingsw.client.cli.view.grid;

import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;

public class LineBorderStyle extends BorderStyle {

    FormattedChar horizontalChar = new FormattedChar('-');
    FormattedChar verticalChar = new FormattedChar('|');
    FormattedChar angularChar = new FormattedChar('+');

    FormattedCharsBuffer gridBuffer;

    @Override
    protected FormattedCharsBuffer createEmptyBufferWithBorders(GridView gridView) {

        gridBuffer = new FormattedCharsBuffer(gridView.getViewRowSize(), gridView.getViewColumnSize());

        // Draw all the columns with vertical chars
        SingleCharBorderStyle.drawVerticalBorders(gridView, gridBuffer, verticalChar);

        // Draw over and under each view with horizontal chars
        for (int r = -1; r < gridView.numOfRows; r++) {
            for (int b = 0; b < gridView.borderSize; b++) {
                for(int c = 0; c < gridView.numOfColumns; c++)
                gridBuffer.drawHorizontalLine(
                    b + (r == -1 ? 0 : (gridView.getRowEndIndex(r)+1)),
                    gridView.getColStartIndex(c),
                    gridView.getColEndIndex(c),
                    horizontalChar);
            }
        }

        // Draw and return empty buffer with single borders
        if(gridView.borderSize == 1)
            return drawSingleBorders(gridView);

        // In the internal grid, draw cells next to horizontal chars with angular chars
        for(int rowIndex = gridView.borderSize - 1; rowIndex <= gridView.getRowEndIndex(gridView.numOfRows - 1) + 1; rowIndex++) {
            for (int colIndex = gridView.borderSize - 1; colIndex <= gridView.getColEndIndex(gridView.numOfColumns - 1) + 1; colIndex++) {
                if(gridBuffer.getFormattedCharAtPosition(rowIndex, colIndex) == verticalChar ||
                    gridBuffer.getFormattedCharAtPosition(rowIndex, colIndex) == gridBuffer.getDefaultFormattedChar())
                    if(gridBuffer.getFormattedCharAtPosition(rowIndex, colIndex+1) == horizontalChar ||
                    gridBuffer.getFormattedCharAtPosition(rowIndex, colIndex-1) == horizontalChar) {
                    gridBuffer.setFormattedCharAtPosition(rowIndex, colIndex, angularChar);
                }
            }
        }

        // In the internal grid, draw cells between angular chars with angular chars
        for(int rowIndex = gridView.borderSize - 1; rowIndex <= gridView.getRowEndIndex(gridView.numOfRows - 1) + 1; rowIndex++) {
            for (int colIndex = gridView.getColEndIndex(0) + 1; colIndex <= gridView.getColStartIndex(gridView.numOfColumns - 1) - 1; colIndex++) {
                if (gridBuffer.getFormattedCharAtPosition(rowIndex, colIndex + 1) == angularChar &&
                    gridBuffer.getFormattedCharAtPosition(rowIndex, colIndex - 1) == angularChar) {
                    gridBuffer.setFormattedCharAtPosition(rowIndex, colIndex, angularChar);
                }
            }
        }

        // Draw the external upper rows with horizontal chars and angular chars at their ends
        // and copy them in the lower rows
        for(int b = 0; b < gridView.borderSize - 1; b++) {
            gridBuffer.setFormattedCharAtPosition(b, b, angularChar);
            gridBuffer.drawHorizontalLine(
                b,
                b+1,
                gridView.getColEndIndex(gridView.numOfColumns - 1) + gridView.borderSize - b - 1,
                horizontalChar
            );
            gridBuffer.setFormattedCharAtPosition(
                b,
                gridView.getColEndIndex(gridView.numOfColumns - 1) + gridView.borderSize - b,
                angularChar
            );
            for(int ch = 0; ch <= gridView.getColEndIndex(gridView.numOfColumns - 1) + gridView.borderSize; ch++) {
                gridBuffer.setFormattedCharAtPosition(
                    gridView.getRowEndIndex(gridView.numOfRows - 1) + gridView.borderSize - b,
                    ch,
                    gridBuffer.getFormattedCharAtPosition(b, ch)
                    );
            }
        }

        return gridBuffer;
    }

    private FormattedCharsBuffer drawSingleBorders(GridView gridView) {

        int colIndex;

        // Draw cells next to horizontal chars with angular chars
        // (last column non included)
        for(int rowIndex = 0; rowIndex <= gridView.getRowEndIndex(gridView.numOfRows - 1) + 1; rowIndex++) {
            for(colIndex = 0; colIndex < gridView.getColStartIndex(gridView.numOfColumns - 1); colIndex++) {
                if(gridBuffer.getFormattedCharAtPosition(rowIndex, colIndex) == verticalChar ||
                    gridBuffer.getFormattedCharAtPosition(rowIndex, colIndex) == gridBuffer.getDefaultFormattedChar())
                    if(gridBuffer.getFormattedCharAtPosition(rowIndex, colIndex+1) == horizontalChar) {
                        gridBuffer.setFormattedCharAtPosition(rowIndex, colIndex, angularChar);
                    }
            }
        }

        // In the last column, draw cells next to horizontal chars with angular chars
        colIndex = gridView.getColEndIndex(gridView.numOfColumns - 1) + 1;
        for(int rowIndex = 0; rowIndex <= gridView.getRowEndIndex(gridView.numOfRows - 1) + 1; rowIndex++) {
            if (gridBuffer.getFormattedCharAtPosition(rowIndex, colIndex - 1) == horizontalChar) {
                gridBuffer.setFormattedCharAtPosition(rowIndex, colIndex, angularChar);
            }
        }

        return gridBuffer;
    }

}
