package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class CliView extends View {

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
        if(isVisible)
            getContentAsFormattedCharsBuffer().print(clientManager.getConsoleWriter());
    }

    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        if(isVisible) {
            FormattedCharsBuffer buffer = buildMyBuffer();
            for (ChildCliView child : children)
                buffer.drawOnTop(
                    child.getRowStartIndex(),
                    child.getColStartIndex(),
                    child.getView().getContentAsFormattedCharsBuffer()
                );
            return buffer;
        } else
            return new FormattedCharsBuffer(rowSize, columnSize);
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


}
