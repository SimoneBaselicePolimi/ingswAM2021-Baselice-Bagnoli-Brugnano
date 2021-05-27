package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.NewCliClientManager;
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

    NewCliClientManager clientManager;

    protected CliView parent = null;
    protected List<ChildCliView> children = new ArrayList<>();

    protected int rowSize, columnSize;

    public CliView(NewCliClientManager clientManager, int rowSize, int columnSize) {
        this.clientManager = clientManager;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
    }

    public void setViewAsChild(CliView childView, int childViewRowStartIndex, int childViewColStartIndex) {
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
        getContentAsFormattedCharsBuffer().print(clientManager.getConsoleWriter());
    }

    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        FormattedCharsBuffer buffer = buildMyBuffer();
        for(ChildCliView child : children)
            buffer.drawOnTop(
                child.getRowStartIndex(),
                child.getColStartIndex(),
                child.getView().getContentAsFormattedCharsBuffer()
            );
        return buffer;
    }

    protected abstract FormattedCharsBuffer buildMyBuffer();

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
