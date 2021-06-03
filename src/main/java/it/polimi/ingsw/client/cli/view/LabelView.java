package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBufferUtils;

import java.util.List;

public class LabelView extends CliView {

    List<FormattedChar> text;
    FormattedChar backgroundChar = new FormattedChar(' ');
    FormattedCharsBufferUtils.HorizontalAlignment horizontalAlignment = FormattedCharsBufferUtils.HorizontalAlignment.LEFT;

    public LabelView(List<FormattedChar> text, CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
        this.text = text;
    }

    public LabelView(List<FormattedChar> text, CliClientManager clientManager) {
        super(clientManager);
        this.text = text;
    }

    public void setText(List<FormattedChar> text) {
        this.text = text;
    }

    public void setBackgroundChar(FormattedChar backgroundChar) {
        this.backgroundChar = backgroundChar;
    }

    public void setHorizontalAlignment(FormattedCharsBufferUtils.HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    @Override
    protected FormattedCharsBuffer buildMyBuffer() {
        FormattedCharsBuffer backgroundBuffer = new FormattedCharsBuffer(rowSize, columnSize);
        backgroundBuffer.setDefaultFormattedChar(backgroundChar);
        FormattedCharsBuffer textBuffer =
            FormattedCharsBufferUtils.createBufferForText(text, columnSize, horizontalAlignment, backgroundChar);
        if(textBuffer.getRowSize() > rowSize)
            textBuffer = textBuffer.crop(0, 0, columnSize, rowSize);
        backgroundBuffer.drawOnTop(0, 0, textBuffer);
        return backgroundBuffer;
    }

}
