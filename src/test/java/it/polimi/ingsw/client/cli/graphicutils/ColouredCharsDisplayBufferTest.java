package it.polimi.ingsw.client.cli.graphicutils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ColouredCharsDisplayBufferTest {

    @Test
    void testGetBlockForIndex() {
        //0 3 66 77
        //4 : (0, 4) - (1, 3) - (1, 2) - ()
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

}