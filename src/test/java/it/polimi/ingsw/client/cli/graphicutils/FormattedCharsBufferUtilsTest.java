package it.polimi.ingsw.client.cli.graphicutils;

import it.polimi.ingsw.client.cli.ConsoleWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormattedCharsBufferUtilsTest {

    @Test
    void testCreateBufferForText() {
        //TODO
        FormattedCharsBufferUtils.createBufferForText(
            FormattedChar.convertStringToFormattedCharList("This is not the real test, this is just a quick check " +
                "on the sanity of my implementation. \n\ntest\nmanual test"),
            30,
            FormattedCharsBufferUtils.HorizontalAlignment.CENTER
        ).print(new ConsoleWriter() {
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