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

}