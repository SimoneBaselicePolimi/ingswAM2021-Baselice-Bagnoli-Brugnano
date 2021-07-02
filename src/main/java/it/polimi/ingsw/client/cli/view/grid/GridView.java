package it.polimi.ingsw.client.cli.view.grid;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.CliView;

import java.util.HashMap;
import java.util.Map;

public class GridView extends CliView {

    final int numOfRows, numOfColumns;
    int borderSize;
    final int[] rowsWeight, colsWeight;
    int rowUnitWeight, columnUnitWeight;

    Map<Integer, Map<Integer, CliView>> viewsInGrid;

    BorderStyle borderStyle = new SingleCharBorderStyle(new FormattedChar(' '));

    public GridView(
        CliClientManager clientManager,
        int numOfRows,
        int numOfColumns,
        int borderSize
    ) {
        this(clientManager, numOfRows, numOfColumns, borderSize, 0, 0);
    }

    /**
     *
     * This constructor will create a default layout where each row and column has the same size. You can then call setRowSize()
     * and setColSize() to obtain a custom table layout.
     */
    public GridView(
        CliClientManager clientManager,
        int numOfRows,
        int numOfColumns,
        int borderSize,
        int gridRowSize,
        int gridColumnSize
    ) {
        super(clientManager, gridRowSize, gridColumnSize);

        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
        this.borderSize = borderSize;
        this.rowsWeight = new int[numOfRows];

        for (int i = 0; i < numOfRows; i++) {
            rowsWeight[i] = 1;
        }

        this.colsWeight = new int[numOfColumns];
        for (int i = 0; i < numOfColumns; i++) {
            colsWeight[i] = 1;
        }

        viewsInGrid = new HashMap<>();
        for (int i = 0; i < numOfRows; i++) {
            viewsInGrid.put(i, new HashMap<>());
        }

    }

    /**
     *
     * @param viewRowIndex
     * @return (included)
     */
    protected int getRowStartIndex(int viewRowIndex) {
        checkIndexesBounds(viewRowIndex, 0);
        int borderTotalSize = borderSize*(numOfRows+1);
        int viewTotalSize = rowSize - borderTotalSize;
        int sumWeights = 0;
        for(int row = 0; row < numOfRows; row++)
            sumWeights += rowsWeight[row];
        rowUnitWeight = viewTotalSize/sumWeights;
        int sumUpperRowsSize = 0;
        for(int r = 0; r < viewRowIndex; r++)
            sumUpperRowsSize += rowUnitWeight * rowsWeight[r];
        return (viewRowIndex+1)*borderSize + sumUpperRowsSize;
    }

    /**
     *
     * @param viewRowIndex
     * @return (included)
     */
    protected int getRowEndIndex(int viewRowIndex) {
        return getRowStartIndex(viewRowIndex) + rowUnitWeight*rowsWeight[viewRowIndex] - 1;
    }

    /**
     *
     * @param viewColIndex
     * @return (included)
     */
    protected int getColStartIndex(int viewColIndex) {
        checkIndexesBounds(0, viewColIndex);
        int borderTotalSize = borderSize*(numOfColumns+1);
        int viewTotalSize = columnSize - borderTotalSize;
        int sumWeights = 0;
        for(int column = 0; column < numOfColumns; column++)
            sumWeights += colsWeight[column];
        columnUnitWeight = viewTotalSize/sumWeights;
        int sumUpperColumnsSize = 0;
        for(int c = 0; c < viewColIndex; c++)
            sumUpperColumnsSize += columnUnitWeight * colsWeight[c];
        return (viewColIndex+1)*borderSize + sumUpperColumnsSize;
    }

    /**
     *
     * @param viewColIndex
     * @return (included)
     */
    protected int getColEndIndex(int viewColIndex) {
        return getColStartIndex(viewColIndex) + columnUnitWeight*colsWeight[viewColIndex] - 1;
    }

    protected void checkIndexesBounds(int rowIndex, int colIndex) {
        if(rowIndex < 0 || rowIndex >= numOfRows)
            throw new IndexOutOfBoundsException();
        if(colIndex < 0 || colIndex >= numOfColumns)
            throw new IndexOutOfBoundsException();
    }

    public void setView(int rowIndex, int colIndex, CliView view) {
        addChildView(view, 0, 0);
        viewsInGrid.get(rowIndex).put(colIndex, view);
    }

    public void setRowWeight(int rowIndex, int weight) {
        if(rowIndex < 0 || rowIndex >= numOfRows)
            throw new IndexOutOfBoundsException();
        rowsWeight[rowIndex] = weight;
    }

    public void setColWeight(int colIndex, int weight) {
        if(colIndex < 0 || colIndex >= numOfColumns)
            throw new IndexOutOfBoundsException();
        colsWeight[colIndex] = weight;
    }

    public void setBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    protected void computeViewsPositionsAndSizes() {
        for(int rowIndex = 0; rowIndex < numOfRows; rowIndex++) {
           for(int colIndex = 0; colIndex < numOfColumns; colIndex++) {
               if(viewsInGrid.get(rowIndex).containsKey(colIndex)) {

                   int viewRowStartIndex = getRowStartIndex(rowIndex);
                   int viewRowEndIndex = getRowEndIndex(rowIndex);
                   int viewColStartIndex = getColStartIndex(colIndex);
                   int viewColEndIndex = getColEndIndex(colIndex);

                   CliView view = viewsInGrid.get(rowIndex).get(colIndex);
                   view.setRowSize(viewRowEndIndex - viewRowStartIndex + 1);
                   view.setColumnSize(viewColEndIndex - viewColStartIndex + 1);

                   ChildCliView childCliView = children.stream().filter(c -> c.getView() == view).findAny().get();
                   children.set(
                       children.indexOf(childCliView),
                       new ChildCliView(view, viewRowStartIndex, viewColStartIndex)
                   );

               }
           }
        }

    }

    @Override
    protected FormattedCharsBuffer buildMyBuffer() {
        return borderStyle.createEmptyBufferWithBorders(this);
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        computeViewsPositionsAndSizes();
        return super.getContentAsFormattedCharsBuffer();
    }

}
