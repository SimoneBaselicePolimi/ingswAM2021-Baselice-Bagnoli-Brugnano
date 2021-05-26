package it.polimi.ingsw.client.cli.graphicutils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ColouredCharsDisplayBufferTest {

    FormattedChar defaultChar = new FormattedChar(
        ' ',
        ConsoleBGColour.BLACK,
        ConsoleFGColour.WHITE,
        true,
        false
    );
    FormattedChar char1 = new FormattedChar(
        'x',
        ConsoleBGColour.BLACK,
        ConsoleFGColour.WHITE,
        false,
        false
    );
    FormattedChar char2 = new FormattedChar(
        ' ',
        ConsoleBGColour.RED,
        ConsoleFGColour.WHITE,
        false,
        false
    );

    @Test
    void testGetBlockForIndex() {
        //0 3 66 77
        List<ColouredCharsDisplayBuffer.FormattedCharBlock> row = List.of(
            new ColouredCharsDisplayBuffer.FormattedCharBlock(0, mock(FormattedChar.class)),
            new ColouredCharsDisplayBuffer.FormattedCharBlock(3, mock(FormattedChar.class)),
            new ColouredCharsDisplayBuffer.FormattedCharBlock(66, mock(FormattedChar.class)),
            new ColouredCharsDisplayBuffer.FormattedCharBlock(77, mock(FormattedChar.class))
        );
        assertEquals(
            3,
            ColouredCharsDisplayBuffer.getBlockForIndex(row, 4, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            3,
            ColouredCharsDisplayBuffer.getBlockForIndex(row, 3, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            0,
            ColouredCharsDisplayBuffer.getBlockForIndex(row, 2, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            77,
            ColouredCharsDisplayBuffer.getBlockForIndex(row, 80, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            66,
            ColouredCharsDisplayBuffer.getBlockForIndex(row, 76, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            66,
            ColouredCharsDisplayBuffer.getBlockForIndex(row, 66, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            0,
            ColouredCharsDisplayBuffer.getBlockForIndex(row, 0, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            77,
            ColouredCharsDisplayBuffer.getBlockForIndex(row, 77, 0, row.size()).block.blockStartIndex
        );
    }

    @Test
    void testOutOfBoundsIndexes() {
        ColouredCharsDisplayBuffer charsDisplayBuffer = new ColouredCharsDisplayBuffer(10, 20);
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> charsDisplayBuffer.getFormattedCharAtPosition(-1, 0)
        );
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> charsDisplayBuffer.getFormattedCharAtPosition(10, 0)
        );
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> charsDisplayBuffer.getFormattedCharAtPosition(0, -1)
        );
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> charsDisplayBuffer.getFormattedCharAtPosition(0, 20)
        );
    }

    @Test
    void testSetChar() {

        ColouredCharsDisplayBuffer charsDisplayBuffer = new ColouredCharsDisplayBuffer(10, 20);

        charsDisplayBuffer.setDefaultFormattedChar(defaultChar);
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(0,0));
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(5,5));

        charsDisplayBuffer.setFormattedCharAtPosition(5,5, char1);
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(0,0));
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(5,4));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(5,5));
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(5,6));

        charsDisplayBuffer.setFormattedCharAtPosition(5,6, char2);
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(5,5));
        assertEquals(char2, charsDisplayBuffer.getFormattedCharAtPosition(5,6));
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(5,7));

        charsDisplayBuffer.setFormattedCharAtPosition(0, 19, char1);
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(0,18));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(0,19));

    }

    @Test
    void testDrawLine() {

        ColouredCharsDisplayBuffer charsDisplayBuffer = new ColouredCharsDisplayBuffer(10, 20);

        charsDisplayBuffer.setDefaultFormattedChar(defaultChar);

        charsDisplayBuffer.drawHorizontalLine(0,2, 10, char1);
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(0,1));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(0,2));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(0,3));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(0,9));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(0,10));
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(0,11));

        charsDisplayBuffer.drawHorizontalLine(0,5, 10, char2);
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(0,1));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(0,2));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(0,4));
        assertEquals(char2, charsDisplayBuffer.getFormattedCharAtPosition(0,5));
        assertEquals(char2, charsDisplayBuffer.getFormattedCharAtPosition(0,9));
        assertEquals(char2, charsDisplayBuffer.getFormattedCharAtPosition(0,10));
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(0,11));

    }

    @Test
    void testVerticalLine() {

        ColouredCharsDisplayBuffer charsDisplayBuffer = new ColouredCharsDisplayBuffer(20, 20);

        charsDisplayBuffer.setDefaultFormattedChar(defaultChar);

        charsDisplayBuffer.drawVerticalLine(2, 10, 11, char1);
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(1, 11));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(2, 11));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(3, 11));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(9, 11));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(10, 11));
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(11, 11));
        for (int r = 2; r <= 10; r++) {
            assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(r, 10));
            assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(r, 12));
        }

        charsDisplayBuffer.drawVerticalLine(5, 10, 11, char2);
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(1, 11));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(2, 11));
        assertEquals(char1, charsDisplayBuffer.getFormattedCharAtPosition(4, 11));
        assertEquals(char2, charsDisplayBuffer.getFormattedCharAtPosition(5, 11));
        assertEquals(char2, charsDisplayBuffer.getFormattedCharAtPosition(9, 11));
        assertEquals(char2, charsDisplayBuffer.getFormattedCharAtPosition(10, 11));
        assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(11, 11));
        for (int r = 2; r <= 10; r++) {
            assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(r, 10));
            assertEquals(defaultChar, charsDisplayBuffer.getFormattedCharAtPosition(r, 12));
        }

    }

}