package it.polimi.ingsw.client.cli.graphicutils;

import it.polimi.ingsw.client.cli.ConsoleWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormattedCharsBuffer {

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

    public FormattedCharsBuffer(int rowSize, int columnSize) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;

        defaultFormattedChar = new FormattedChar(' ');

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
        int blockAfterBlockForEndIndexPosition = blockForEndIndex.blockPositionInBlockList + 1;
        for(int i = blockForStartIndex.blockPositionInBlockList; i < blockForEndIndex.blockPositionInBlockList; i++) {
            row.remove(blockForStartIndex.blockPositionInBlockList + 1);
            blockAfterBlockForEndIndexPosition--;
        }
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
                blockAfterBlockForEndIndexPosition++;
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

    public void print(ConsoleWriter consoleWriter) {
        print(consoleWriter, 0, 0, rowSize-1, columnSize-1);
    }

    /**
     * @param consoleWriter
     * @param rowStartIndex (included)
     * @param colStartIndex (included)
     * @param rowEndIndex (included)
     * @param colEndIndex (included)
     */
    public void print(
        ConsoleWriter consoleWriter,
        int rowStartIndex,
        int colStartIndex,
        int rowEndIndex,
        int colEndIndex
    ) {
        checkIndexesBounds(rowStartIndex, colStartIndex);
        checkIndexesBounds(rowEndIndex, colEndIndex);
        StringBuilder out = new StringBuilder(rowSize * columnSize * 2);
        for (int rowIndex = rowStartIndex; rowIndex <= rowEndIndex; rowIndex++) {
            List<FormattedCharBlock> row = rows.get(rowIndex);
            FormattedCharBlockInBlockList oldBlock = null;
            FormattedCharBlockInBlockList currBlock = getBlockForIndex(row, colStartIndex, 0, row.size());
            int colIndex = colStartIndex;
            while (colIndex <= colEndIndex) {
                boolean hasNextBlock = currBlock.blockPositionInBlockList < row.size() - 1;
                if(hasNextBlock) {
                    FormattedCharBlockInBlockList nextBlock = new FormattedCharBlockInBlockList(
                        row.get(currBlock.blockPositionInBlockList+1),
                        currBlock.blockPositionInBlockList+1
                    );
                    if(oldBlock==null)
                        out.append(currBlock.block.formattedChar.getCompleteFormattingCmd());
                    else
                        out.append(currBlock.block.formattedChar.getDeltaFormattingCmd(oldBlock.block.formattedChar));
                    for(; colIndex < nextBlock.block.blockStartIndex && colIndex <= colEndIndex; colIndex++)
                        out.append(currBlock.block.formattedChar.character);
                    oldBlock = currBlock;
                    currBlock = nextBlock;
                } else {
                    if(oldBlock==null)
                        out.append(currBlock.block.formattedChar.getCompleteFormattingCmd());
                    else
                        out.append(currBlock.block.formattedChar.getDeltaFormattingCmd(oldBlock.block.formattedChar));
                    for(; colIndex <= colEndIndex; colIndex++)
                        out.append(currBlock.block.formattedChar.character);
                    oldBlock = currBlock;
                }

            }
            out.append(FormattedChar.getResetFormatCmd());
            out.append("\n");
        }
        consoleWriter.writeNewLineToConsole(out.toString());
    }

    /**
     *
     * @param rowStartIndex (included)
     * @param colStartIndex (included)
     * @param rowEndIndex (included)
     * @param colEndIndex (included)
     * @return
     */
    public FormattedCharsBuffer crop(int rowStartIndex, int colStartIndex, int rowEndIndex, int colEndIndex) {
        FormattedCharsBuffer croppedBuffer = new FormattedCharsBuffer(
            rowEndIndex - rowStartIndex + 1,
            colEndIndex - colStartIndex + 1
        );
        for (int r = 0; r < rowEndIndex - rowStartIndex + 1; r++) {
            List<FormattedCharBlock> row = rows.get(r + rowStartIndex);
            FormattedCharBlockInBlockList startBlock = getBlockForIndex(row, colStartIndex, 0, row.size());
            List<FormattedCharBlock> rowInCroppedBuffer = croppedBuffer.rows.get(r);
            rowInCroppedBuffer.clear();
            rowInCroppedBuffer.add(new FormattedCharBlock(0, startBlock.block.formattedChar));
            int blockIndex = startBlock.blockPositionInBlockList + 1;
            while (blockIndex < row.size() && row.get(blockIndex).blockStartIndex < colEndIndex - colStartIndex + 1) {
                rowInCroppedBuffer.add(new FormattedCharBlock(
                    row.get(blockIndex).blockStartIndex-colStartIndex,
                    row.get(blockIndex).formattedChar
                ));
                blockIndex++;
            }
        }
        return croppedBuffer;
    }

    public void drawOnTop(int rowIndex, int colIndex, FormattedCharsBuffer bufferToDraw) {
        checkIndexesBounds(rowIndex + bufferToDraw.rowSize - 1, colIndex+bufferToDraw.columnSize - 1);
        for(int r = rowIndex; r < rowIndex + bufferToDraw.rowSize; r++) {
            List<FormattedCharBlock> row = rows.get(r);
            FormattedCharBlockInBlockList blockStart = getBlockForIndex(row, colIndex, 0, row.size());
            FormattedCharBlockInBlockList blockEnd =
                getBlockForIndex(row, colIndex+bufferToDraw.columnSize-1, 0, row.size());
            row.removeIf(b ->
                b.blockStartIndex > blockStart.block.blockStartIndex &&
                b.blockStartIndex <= blockEnd.block.blockStartIndex
            );
            row.add(
                blockStart.blockPositionInBlockList+1,
                new FormattedCharBlock(colIndex + bufferToDraw.columnSize, blockEnd.block.formattedChar)
            );
            row.addAll(
                blockStart.blockPositionInBlockList+1,
                bufferToDraw.rows.get(r - rowIndex).stream()
                    .map(b -> new FormattedCharBlock(b.blockStartIndex + colIndex, b.formattedChar))
                    .collect(Collectors.toList())
            );
        }
    };

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

    /**
     *
     * @param row
     * @param index
     * @param rangeBegin (included)
     * @param rangeEnd (excluded)
     * @return
     */
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
