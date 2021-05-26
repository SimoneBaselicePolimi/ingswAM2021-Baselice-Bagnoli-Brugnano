package it.polimi.ingsw.client.cli.graphicutils;

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
            rows.add(createRow());
        }

    }

    protected List<FormattedCharBlock> createRow() {
        List<FormattedCharBlock> row = new ArrayList<>();
        row.add(new FormattedCharBlock(0, defaultFormattedChar));
        return row;
    }

    public FormattedChar getFormattedCharAtPosition(int rowIndex, int colIndex) {
        List<FormattedCharBlock> row = rows.get(rowIndex);
        return getBlockForIndex(row, colIndex, 0, columnSize).block.formattedChar;
    }

    public void setFormattedCharAtPosition(int rowIndex, int colIndex, FormattedChar formattedChar) {
        if(!getFormattedCharAtPosition(rowIndex, colIndex).equals(formattedChar)) {
            List<FormattedCharBlock> row = rows.get(rowIndex);
            if (colIndex != columnSize - 1) {
                FormattedCharBlockInBlockList blockInRow = getBlockForIndex(row, colIndex, 0, columnSize);
                FormattedChar nextFormattedChar = getFormattedCharAtPosition(rowIndex, colIndex + 1);
                if (blockInRow.block.blockStartIndex == colIndex)
                    row.set(blockInRow.blockPositionInBlockList, new FormattedCharBlock(colIndex, formattedChar));
                if (!formattedChar.equals(nextFormattedChar))
                    if (blockInRow.blockPositionInBlockList + 1 < row.size()
                            && row.get(blockInRow.blockPositionInBlockList + 1).blockStartIndex == colIndex + 1)
                        row.set(
                            blockInRow.blockPositionInBlockList + 1, new FormattedCharBlock(colIndex+1, nextFormattedChar)
                        );
                    else
                        row.add(colIndex, new FormattedCharBlock(colIndex+1, nextFormattedChar));
            } else {
                if (row.get(row.size()-1).blockStartIndex == colIndex-1)
                    row.set(row.size()-1, new FormattedCharBlock(colIndex, formattedChar));
                else
                    row.add(new FormattedCharBlock(colIndex, formattedChar));
            }
        }
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

    public FormattedChar getDefaultFormattedChar() {
        return defaultFormattedChar;
    }

    public void setDefaultFormattedChar(FormattedChar defaultFormattedChar) {
        this.defaultFormattedChar = defaultFormattedChar;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

}
