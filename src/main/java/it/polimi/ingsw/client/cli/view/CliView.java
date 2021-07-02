package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.view.AbstractView;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;

import java.util.ArrayList;
import java.util.List;

public abstract class CliView extends AbstractView {

    protected static class ChildCliView {

        final CliView view;
        final int rowStartIndex, colStartIndex;

        public ChildCliView(CliView child, int rowStartIndex, int colStartIndex) {
            this.view = child;
            this.rowStartIndex = rowStartIndex;
            this.colStartIndex = colStartIndex;
        }

        public CliView getView() {
            return view;
        }

        public int getRowStartIndex() {
            return rowStartIndex;
        }

        public int getColStartIndex() {
            return colStartIndex;
        }

        public int getRowEndIndex() {
            return rowStartIndex + view.getViewRowSize();
        }

        public int getColEndIndex() {
            return colStartIndex + view.getViewColumnSize();
        }

    }

    CliClientManager clientManager;

    protected CliView parent = null;
    protected List<ChildCliView> children = new ArrayList<>();

    protected int rowSize, columnSize;

    public CliView(CliClientManager clientManager, int rowSize, int columnSize) {
        this.clientManager = clientManager;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
    }

    public CliView(CliClientManager clientManager) {
        this.clientManager = clientManager;
        this.rowSize = 0;
        this.columnSize = 0;
    }

    public void addChildView(CliView childView, int childViewRowStartIndex, int childViewColStartIndex) {
        children.add(new ChildCliView(childView, childViewRowStartIndex, childViewColStartIndex));
        childView.parent = this;
    }

    @Override
    public void updateView() {
        if(isTopLevelView())
            print();
        else
            parent.updateView();
    }

    public void print() {
        try {
            if (isVisible) {
                cleanConsole();
                getContentAsFormattedCharsBuffer().print(clientManager.getConsoleWriter());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getViewHierarchyAsString() {
        if(isTopLevelView())
            return this.toString();
        else
            return this.toString() + " -> " + parent.getViewHierarchyAsString();
    }

    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        try {
            if (isVisible) {
                FormattedCharsBuffer buffer = buildMyBuffer();

                for (ChildCliView child : children) {

                    FormattedCharsBuffer childBuffer = child.getView().getContentAsFormattedCharsBuffer();

                    if (
                        child.getRowStartIndex() + childBuffer.getRowSize() - 1 > rowSize ||
                            child.getRowStartIndex() < 0 ||
                            child.getColStartIndex() + childBuffer.getColumnSize() - 1 > columnSize ||
                            child.getColStartIndex() < 0
                    ) {
                        childBuffer = childBuffer.crop(
                            Math.max(0, -child.getRowStartIndex()),
                            Math.max(0, -child.getColStartIndex()),
                            Math.min(childBuffer.getRowSize() - 1, rowSize - 1 - child.getRowStartIndex()),
                            Math.min(childBuffer.getColumnSize() - 1, columnSize - 1 - child.getColStartIndex())
                        );
                    }

                    buffer.drawOnTop(
                        Math.max(0, child.getRowStartIndex()),
                        Math.max(0, child.getColStartIndex()),
                        childBuffer
                    );

                }

                return buffer;
            } else
                return new FormattedCharsBuffer(rowSize, columnSize);
        } catch (Exception e) {
            ProjectLogger.getLogger().log(
                LogLevel.ERROR,
                "Error while rendering view: \n%s",
                getViewHierarchyAsString()
            );
            ProjectLogger.getLogger().log(e);
            throw e;
        }
    }

    protected FormattedCharsBuffer buildMyBuffer() {
        return new FormattedCharsBuffer(rowSize, columnSize);
    }

    public boolean isTopLevelView() {
        return parent == null;
    }

    public int getViewRowSize() {
        return rowSize;
    }

    public int getViewColumnSize() {
        return columnSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    protected void cleanConsole() {
        clientManager.getConsoleWriter().writeToConsole(System.lineSeparator().repeat(columnSize*2));
    }

    @Override
    public void destroyView() {
        super.destroyView();
        children.forEach(child -> child.getView().destroyView());
    }

}
