package it.polimi.ingsw.client.cli.view.grid;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.ConsoleWriter;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SingleCharBorderStyleTest {

    @Mock
    CliClientManager clientManager;

    @Test
    void testPrintSingleCharBorderStyle() {
        new SingleCharBorderStyle(new FormattedChar('#'))
            .createEmptyBufferWithBorders(new GridView(clientManager, 3, 4, 2, 18, 100))
            .print(new ConsoleWriter() {
                @Override
                public void writeToConsole(String text) {
                    System.out.print(text);
                }

                @Override
                public void writeNewLineToConsole(String line) {
                    System.out.println(line);
                }
            });
    }

    @Test
    void testPrintLineBorderStyle() {
        new LineBorderStyle()
            .createEmptyBufferWithBorders(new GridView(clientManager, 2, 8, 1, 18, 100))
            .print(new ConsoleWriter() {
                @Override
                public void writeToConsole(String text) {
                    System.out.print(text);
                }

                @Override
                public void writeNewLineToConsole(String line) {
                    System.out.println(line);
                }
            });
    }
}