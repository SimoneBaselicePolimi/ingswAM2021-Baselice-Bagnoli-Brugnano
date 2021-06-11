package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBufferUtils;
import it.polimi.ingsw.gameactionshistory.GameAction;

import java.util.ArrayList;
import java.util.List;

public class GameHistoryView extends CliView {

    public GameHistoryView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
    }

    public GameHistoryView(CliClientManager clientManager) {
        super(clientManager);
        this.subscribeToRepresentation(clientManager.getGameHistoryRepresentation());
    }

    @Override
    protected FormattedCharsBuffer buildMyBuffer() {
        List<GameAction> gameActions = clientManager.getGameHistoryRepresentation().getGameActions();
        List<String> gameActionsMessages = new ArrayList<>();
        List<FormattedChar> text = new ArrayList<>();
        for(GameAction gameAction : gameActions)
            gameActionsMessages.add(gameAction.getActionMessage());
        for(String gameActionMessage : gameActionsMessages) {
            text.addAll(
                FormattedChar.convertStringToFormattedCharList(" - ")
            );
            text.addAll(
                FormattedChar.convertStringToFormattedCharList(gameActionMessage)
            );
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
}
