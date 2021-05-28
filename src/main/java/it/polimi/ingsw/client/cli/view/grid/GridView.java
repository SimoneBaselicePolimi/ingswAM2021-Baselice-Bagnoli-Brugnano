package it.polimi.ingsw.client.cli.view.grid;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.CliView;

import java.util.HashMap;
import java.util.Map;

public class GridView extends CliView {

    final int numOfRows, numOfColumns;
    int borderSize;
    final int[] rowsWeight, colsWeight;

    Map<Integer, Map<Integer, CliView>> viewsInGrid;

    BorderStyle borderStyle;


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

    protected int getRowStartIndexForView(int viewRowIndex, int viewColIndex) {
        int borderTotalSize = borderSize*(numOfRows+1);
        int viewTotalSize = rowSize - borderTotalSize;
        return 0;
    }

    protected int getRowEndIndexForView(int viewRowIndex, int viewColIndex) {
        return 0;

    }

    protected int getColStartIndexForView(int viewRowIndex, int viewColIndex) {
        int borderTotalSize = borderSize*(numOfColumns+1);
        return 0;

    }

    protected int getColEndIndexForView(int viewRowIndex, int viewColIndex) {
        return 0;

    }

    protected void checkIndexesBounds(int rowIndex, int colIndex) {

    }

    public void setView(int rowIndex, int colIndex, CliView view) {
        addChildView(view, 0, 0);
        viewsInGrid.get(rowIndex).put(colIndex, view);
    }

    public void setRowWeight(int rowIndex, int weight) {
        if(rowIndex < 0 || rowIndex >= numOfColumns)
            throw new IndexOutOfBoundsException();
        rowsWeight[rowIndex] = weight;
    }

    public void setColWeight(int colIndex, int weight) {
        if(colIndex < 0 || colIndex >= numOfColumns)
            throw new IndexOutOfBoundsException();
        rowsWeight[colIndex] = weight;
    }

    public void setBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    protected void computeViewsPositionsAndSizes() {
        for(int rowIndex = 0; rowIndex < numOfRows; rowIndex++) {
           for(int colIndex = 0; colIndex < numOfColumns; colIndex++) {
               if(viewsInGrid.get(rowIndex).containsKey(colIndex)) {

                   int viewRowStartIndex = getRowStartIndexForView(rowIndex, colIndex);
                   int viewRowEndIndex = getRowEndIndexForView(rowIndex, colIndex);
                   int viewColStartIndex = getColStartIndexForView(rowIndex, colIndex);
                   int viewColEndIndex = getColEndIndexForView(rowIndex, colIndex);

                   CliView view = viewsInGrid.get(rowIndex).get(colIndex);
                   view.setRowSize(viewRowEndIndex - viewRowStartIndex);
                   view.setColumnSize(viewColEndIndex - viewColStartIndex);

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
