package it.polimi.ingsw.client.cli.graphicutils;

import it.polimi.ingsw.client.ConsoleWriter;

import java.util.ArrayList;
import java.util.List;

public class ColouredCharsDisplayBuffer {

    protected static class FormattedCharBlock {
        public final int blockStartIndex;
        public final FormattedChar formattedChar;

        FormattedCharBlock(int blockStartIndex, FormattedChar formattedChar) {
            this.blockStartIndex = blockStartIndex;
            this.formattedChar = formattedChar;
        }

    }

    protected static class FormattedCharBlockInBlockList {
        public final FormattedCharBlock block;
        public final int blockPositionInBlockList;

        public FormattedCharBlockInBlockList(FormattedCharBlock block, int blockPositionInBlockList) {
            this.block = block;
            this.blockPositionInBlockList = blockPositionInBlockList;
        }

    }

    final int rowSize;
    final int columnSize;

    protected FormattedChar defaultFormattedChar;

    protected List<List<FormattedCharBlock>> rows;

    public ColouredCharsDisplayBuffer(int rowSize, int columnSize) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;

        defaultFormattedChar = new FormattedChar(
            ' ',
            ConsoleBGColour.BLACK,
            ConsoleFGColour.WHITE,
            false,
            false
        );

        rows = new ArrayList<>(rowSize);
        for (int i = 0; i < rowSize; i++) {
            rows.add(new ArrayList<>());
        }

        resetDisplay();

    }

    public FormattedChar getFormattedCharAtPosition(int rowIndex, int colIndex) {
        checkIndexesBounds(rowIndex, colIndex);
        List<FormattedCharBlock> row = rows.get(rowIndex);
        return getBlockForIndex(row, colIndex, 0, row.size()).block.formattedChar;
    }

    public void setFormattedCharAtPosition(int rowIndex, int colIndex, FormattedChar newFormattedChar) {
        checkIndexesBounds(rowIndex, colIndex);
        if(!getFormattedCharAtPosition(rowIndex, colIndex).equals(newFormattedChar)) {
            List<FormattedCharBlock> row = rows.get(rowIndex);
            if (colIndex != columnSize - 1) {
                FormattedCharBlockInBlockList blockForIndex = getBlockForIndex(row, colIndex, 0, row.size());

                int blockAfterNewOnePosition;
                if (blockForIndex.block.blockStartIndex == colIndex) {
                    row.set(blockForIndex.blockPositionInBlockList, new FormattedCharBlock(colIndex, newFormattedChar));
                    blockAfterNewOnePosition = blockForIndex.blockPositionInBlockList + 1;
                } else {
                    row.add(
                        blockForIndex.blockPositionInBlockList + 1,
                        new FormattedCharBlock(colIndex, newFormattedChar)
                    );
                    blockAfterNewOnePosition = blockForIndex.blockPositionInBlockList + 2;
                }

                if (
                    blockAfterNewOnePosition == row.size() ||
                    row.get(blockAfterNewOnePosition).blockStartIndex != colIndex + 1
                ) {
                    row.add(
                        blockAfterNewOnePosition,
                        new FormattedCharBlock(colIndex+1, blockForIndex.block.formattedChar)
                    );
                }

            } else {
                if (row.get(row.size()-1).blockStartIndex == columnSize-1)
                    row.set(row.size()-1, new FormattedCharBlock(columnSize-1, newFormattedChar));
                else
                    row.add(new FormattedCharBlock(columnSize-1, newFormattedChar));
            }
        }
    }

    /**
     *
     * @param rowIndex
     * @param colStartIndex line initial position (included)
     * @param colEndIndex line final position in display matrix (included)
     * @param newFormattedChar
     */
    public void drawHorizontalLine(int rowIndex, int colStartIndex, int colEndIndex, FormattedChar newFormattedChar) {
        checkIndexesBounds(rowIndex, colStartIndex);
        checkIndexesBounds(rowIndex, colEndIndex);
        List<FormattedCharBlock> row = rows.get(rowIndex);
        FormattedCharBlockInBlockList blockForStartIndex = getBlockForIndex(row, colStartIndex, 0, row.size());
        FormattedCharBlockInBlockList blockForEndIndex = getBlockForIndex(row, colEndIndex, 0, row.size());
        for(int i = blockForStartIndex.blockPositionInBlockList; i < blockForEndIndex.blockPositionInBlockList; i++)
            row.remove(blockForStartIndex.blockPositionInBlockList+1);
        int blockAfterBlockForEndIndexPosition = blockForEndIndex.blockPositionInBlockList + 1;
        if(!blockForStartIndex.block.formattedChar.equals(newFormattedChar)) {
            if (blockForStartIndex.block.blockStartIndex == colStartIndex)
                row.set(
                    blockForStartIndex.blockPositionInBlockList,
                    new FormattedCharBlock(colStartIndex, newFormattedChar)
                );
            else {
                row.add(
                    blockForStartIndex.blockPositionInBlockList + 1,
                    new FormattedCharBlock(colStartIndex, newFormattedChar)
                );
                blockAfterBlockForEndIndexPosition = blockForEndIndex.blockPositionInBlockList + 2;
            }
        }
        if(colEndIndex != columnSize-1) {
            if(
                blockAfterBlockForEndIndexPosition == row.size() ||
                row.get(blockAfterBlockForEndIndexPosition).blockStartIndex != colEndIndex + 1
            ) {
                row.add(
                    blockAfterBlockForEndIndexPosition,
                    new FormattedCharBlock(colEndIndex+1, blockForEndIndex.block.formattedChar)
                );
            }
        }

    }

    public void drawVerticalLine(int rowStartIndex, int rowEndIndex, int colIndex, FormattedChar newFormattedChar) {
        for (int r = rowStartIndex; r <= rowEndIndex; r++) {
            setFormattedCharAtPosition(r, colIndex, newFormattedChar);
        }
    }

    public void print(ConsoleWriter consoleWriter, int rowStartIndex, int rowEndIndex, int colStartIndex, int colEndIndex) {

    }

    /**
     * Set the formatted char to use as default for the whole display.
     * Calling this method will reset the display.
     * @param defaultFormattedChar see {@link FormattedChar}
     */
    public void setDefaultFormattedChar(FormattedChar defaultFormattedChar) {
        this.defaultFormattedChar = defaultFormattedChar;
        resetDisplay();
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    protected void resetDisplay() {
        for (int i = 0; i < rowSize; i++) {
            rows.get(i).clear();
            rows.get(i).add(new FormattedCharBlock(0, defaultFormattedChar));
        }
    }

    protected void checkIndexesBounds(int rowIndex, int colIndex) {
        if(rowIndex < 0 || rowIndex >= rowSize)
            throw new IndexOutOfBoundsException();
        if(colIndex < 0 || colIndex >= columnSize)
            throw new IndexOutOfBoundsException();
    }

    protected static FormattedCharBlockInBlockList getBlockForIndex(List<FormattedCharBlock> row, int index, int rangeBegin, int rangeEnd) {
        int middleRange = (rangeBegin+rangeEnd)/2;
        if(rangeBegin == rangeEnd - 1)
            return new FormattedCharBlockInBlockList(row.get(rangeBegin), rangeBegin);
        if(index < row.get(middleRange).blockStartIndex)
            return getBlockForIndex(row, index, rangeBegin, middleRange);
        else
            return getBlockForIndex(row, index, middleRange, rangeEnd);
    }

}
