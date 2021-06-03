package it.polimi.ingsw.client.cli.graphicutils;

import java.util.ArrayList;
import java.util.List;

public class FormattedCharsBufferUtils {

    public enum HorizontalAlignment {
        LEFT,
        CENTER,
        RIGHT
    }

    public static FormattedCharsBuffer createBufferForText(
        List<FormattedChar> text,
        int bufferColSize,
        HorizontalAlignment horizontalAlignment
    ) {
        return createBufferForText(text, bufferColSize, horizontalAlignment, new FormattedChar(' '));
    }

    public static FormattedCharsBuffer createBufferForText(
        List<FormattedChar> text,
        int bufferColSize,
        HorizontalAlignment horizontalAlignment,
        FormattedChar backgroundChar
    ) {

        List<Integer> newLineIndexes = new ArrayList<>();
        for (int i = 0; i < text.size(); i++)
            if(text.get(i).character == '\n')
                newLineIndexes.add(i);

        List<List<FormattedChar>> rows = new ArrayList<>();
        int nextRowStartIndex = 0;
        for (int nextRowEndIndex : newLineIndexes) {
            rows.add(text.subList(nextRowStartIndex, nextRowEndIndex));
            nextRowStartIndex = nextRowEndIndex + 1;
        }
        rows.add(text.subList(nextRowStartIndex, text.size()));

        List<List<FormattedChar>> normalizedRows = new ArrayList<>();
        for(List<FormattedChar> row : rows) {
            List<FormattedChar> rowSectionToNormalize = row;
            while (!rowSectionToNormalize.isEmpty()) {
                if (rowSectionToNormalize.size() <= bufferColSize) {
                    normalizedRows.add(rowSectionToNormalize);
                    rowSectionToNormalize = List.of();
                } else {
                    int whitespaceCharIndex;
                    for (
                        whitespaceCharIndex = bufferColSize;
                        whitespaceCharIndex > -1 &&
                        rowSectionToNormalize.get(whitespaceCharIndex).character != ' ' &&
                            rowSectionToNormalize.get(whitespaceCharIndex).character != '\t';
                        whitespaceCharIndex--
                    );
                    if(whitespaceCharIndex != -1) {
                        normalizedRows.add(rowSectionToNormalize.subList(0, whitespaceCharIndex));
                        rowSectionToNormalize =
                            rowSectionToNormalize.subList(whitespaceCharIndex+1, rowSectionToNormalize.size());
                    } else {
                        normalizedRows.add(rowSectionToNormalize.subList(0, bufferColSize));
                        rowSectionToNormalize =
                            rowSectionToNormalize.subList(bufferColSize, rowSectionToNormalize.size());
                    }
                }
            }
        }

        FormattedCharsBuffer textBuffer = new FormattedCharsBuffer(normalizedRows.size(), bufferColSize);
        textBuffer.setDefaultFormattedChar(backgroundChar);
        for (int r = 0; r < normalizedRows.size(); r++) {
            for (int i = 0; i < normalizedRows.get(r).size(); i++) {
                int colIndex;
                if(horizontalAlignment == HorizontalAlignment.LEFT)
                    colIndex = i;
                else if(horizontalAlignment == HorizontalAlignment.RIGHT)
                    colIndex = bufferColSize - normalizedRows.get(r).size() + i;
                else
                    colIndex = (bufferColSize-normalizedRows.get(r).size())/2 + i;
                textBuffer.setFormattedCharAtPosition(r, colIndex, normalizedRows.get(r).get(i));
            }
        }

        return textBuffer;
    }

}
