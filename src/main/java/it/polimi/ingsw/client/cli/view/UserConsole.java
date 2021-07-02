package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.UserIOLogger;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBufferUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * View representing the console available to the user to view the various game options
 * and choose which screen to switch to and what actions to perform
 */
public class UserConsole extends CliView implements UserIOLogger {

    protected static class IOMessage {
        final String message;
        final boolean isUserInput;

        public IOMessage(String message, boolean isUserInput) {
            this.message = message;
            this.isUserInput = isUserInput;
        }

    }

    public UserConsole(CliClientManager clientManager) {
        super(clientManager);
    }

    public UserConsole(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    List<IOMessage> consoleMessages = new ArrayList<>();

    @Override
    protected FormattedCharsBuffer buildMyBuffer() {
        List<FormattedChar> text = new ArrayList<>();
        for(IOMessage message : consoleMessages) {
            if(message.isUserInput) {
                text.addAll(
                    FormattedChar.convertStringToFormattedCharList(" > ", Colour.GREEN, Colour.BLACK)
                );
                text.addAll(
                    FormattedChar.convertStringToFormattedCharList(message.message, Colour.GREEN, Colour.BLACK)
                );
            } else {
                text.addAll(
                    FormattedChar.convertStringToFormattedCharList(message.message)
                );
            }
            text.add(new FormattedChar('\n'));
        }
        FormattedCharsBuffer textBuffer = FormattedCharsBufferUtils.createBufferForText(
            text,
            columnSize-2,
            FormattedCharsBufferUtils.HorizontalAlignment.LEFT
        );
        if(textBuffer.getRowSize() > rowSize)
            textBuffer = textBuffer.crop(
                textBuffer.getRowSize() - rowSize,
                0,
                textBuffer.getRowSize()-1,
                columnSize - 1 - 2
            );
        FormattedCharsBuffer buffer = new FormattedCharsBuffer(rowSize, columnSize);
        buffer.drawOnTop(0, 1, textBuffer);
        return buffer;
    }

    @Override
    public void logMessageFromUser(String text) {
        consoleMessages.add(new IOMessage(text, true));
        updateView();
    }

    @Override
    public void logMessageForUser(String text) {
        consoleMessages.add(new IOMessage(text, false));
        updateView();
    }

}
