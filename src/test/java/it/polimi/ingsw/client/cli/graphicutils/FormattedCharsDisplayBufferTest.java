package it.polimi.ingsw.client.cli.graphicutils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FormattedCharsDisplayBufferTest {

    FormattedChar defaultChar = new FormattedChar(
        ' ',
        CliColour.WHITE, CliColour.BLACK,
        true,
        false,
        false
    );
    FormattedChar char1 = new FormattedChar(
        'x',
        CliColour.WHITE, CliColour.BLACK,
        false,
        false,
        false
    );
    FormattedChar char2 = new FormattedChar(
        ' ',
        CliColour.WHITE, CliColour.BLACK,
        false,
        false,
        false
    );

    @Test
    void testGetBlockForIndex() {
        //0 3 66 77
        List<FormattedCharsDisplayBuffer.FormattedCharBlock> row = List.of(
            new FormattedCharsDisplayBuffer.FormattedCharBlock(0, mock(FormattedChar.class)),
            new FormattedCharsDisplayBuffer.FormattedCharBlock(3, mock(FormattedChar.class)),
            new FormattedCharsDisplayBuffer.FormattedCharBlock(66, mock(FormattedChar.class)),
            new FormattedCharsDisplayBuffer.FormattedCharBlock(77, mock(FormattedChar.class))
        );
        assertEquals(
            3,
            FormattedCharsDisplayBuffer.getBlockForIndex(row, 4, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            3,
            FormattedCharsDisplayBuffer.getBlockForIndex(row, 3, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            0,
            FormattedCharsDisplayBuffer.getBlockForIndex(row, 2, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            77,
            FormattedCharsDisplayBuffer.getBlockForIndex(row, 80, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            66,
            FormattedCharsDisplayBuffer.getBlockForIndex(row, 76, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            66,
            FormattedCharsDisplayBuffer.getBlockForIndex(row, 66, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            0,
            FormattedCharsDisplayBuffer.getBlockForIndex(row, 0, 0, row.size()).block.blockStartIndex
        );
        assertEquals(
            77,
            FormattedCharsDisplayBuffer.getBlockForIndex(row, 77, 0, row.size()).block.blockStartIndex
        );
    }

    @Test
    void testOutOfBoundsIndexes() {
        FormattedCharsDisplayBuffer charsDisplayBuffer = new FormattedCharsDisplayBuffer(10, 20);
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

        FormattedCharsDisplayBuffer charsDisplayBuffer = new FormattedCharsDisplayBuffer(10, 20);

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

        FormattedCharsDisplayBuffer charsDisplayBuffer = new FormattedCharsDisplayBuffer(10, 20);

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

        FormattedCharsDisplayBuffer charsDisplayBuffer = new FormattedCharsDisplayBuffer(20, 20);

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