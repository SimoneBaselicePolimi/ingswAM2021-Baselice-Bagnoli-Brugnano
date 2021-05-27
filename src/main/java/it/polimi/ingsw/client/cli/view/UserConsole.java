package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.UserIOLogger;
import it.polimi.ingsw.client.cli.NewCliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.CliColour;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBufferUtils;

import java.util.ArrayList;
import java.util.List;

public class UserConsole extends CliView implements UserIOLogger {

    protected static class IOMessage {
        final String message;
        final boolean isUserInput;

        public IOMessage(String message, boolean isUserInput) {
            this.message = message;
            this.isUserInput = isUserInput;
        }

    }

    public UserConsole(NewCliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    List<IOMessage> consoleMessages = new ArrayList<>();

    @Override
    protected FormattedCharsBuffer buildMyBuffer() {
        List<FormattedChar> text = new ArrayList<>();
        for(IOMessage message : consoleMessages) {
            if(message.isUserInput) {
                text.addAll(
                    FormattedChar.convertStringToFormattedCharList(" > ", CliColour.GREEN, CliColour.BLACK)
                );
                text.addAll(
                    FormattedChar.convertStringToFormattedCharList(message.message, CliColour.GREEN, CliColour.BLACK)
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
            columnSize,
            FormattedCharsBufferUtils.HorizontalAlignment.LEFT
        );
        if(textBuffer.getRowSize() > rowSize) {
            return textBuffer.crop(
                textBuffer.getRowSize()-rowSize,
                0,
                textBuffer.getRowSize()-1,
                columnSize
            );
        } else {
            return textBuffer;
        }
    }

    @Override
    public void logMessageFromUser(String text) {
        consoleMessages.add(new IOMessage(text, true));
    }

    @Override
    public void logMessageForUser(String text) {
        consoleMessages.add(new IOMessage(text, false));
    }

}
