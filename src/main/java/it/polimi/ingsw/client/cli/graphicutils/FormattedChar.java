package it.polimi.ingsw.client.cli.graphicutils;

import java.util.List;
import java.util.Objects;

public class FormattedChar {

    public static final String ESCAPE_CMD_PREFIX = "\u001B";
    public static final String REGULAR_TEXT_CODE = "0";
    public static final String BOLD_CODE = "1";
    public static final String UNDERLINE_CODE = "4";

    public final char character;
    public final ConsoleBGColour defaultBG;
    public final ConsoleFGColour defaultFG;
    public final boolean isBold;
    public final boolean isUnderlined;

    public FormattedChar(
        char character,
        ConsoleBGColour defaultBG,
        ConsoleFGColour defaultFG,
        boolean isBold,
        boolean isUnderlined
    ) {
        this.character = character;
        this.defaultBG = defaultBG;
        this.defaultFG = defaultFG;
        this.isBold = isBold;
        this.isUnderlined = isUnderlined;
    }

    /**
     * Returns the command (ANSI escape code) that allows printing of a char with the specified formatting options.
     * Note: Does not include char
     * @return the ANSI command
     */
    public String getCompleteFormattingCmd() {
        return "";
    }

    /**
     * Returns a command (ANSI escape code) that includes only the formatting options that are different from the
     * ones in oldFormattedChar.
     * Note: Does not include char
     * @param oldFormattedChar oldFormattedChar
     * @return the ANSI command
     */
    public String getDeltaFormattingCmd(FormattedChar oldFormattedChar) {
        return "";
    }

    protected String printCommandWithCodes(List<String> codes) {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormattedChar that = (FormattedChar) o;
        return character == that.character && isBold == that.isBold && isUnderlined == that.isUnderlined && defaultBG == that.defaultBG && defaultFG == that.defaultFG;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, defaultBG, defaultFG, isBold, isUnderlined);
    }

}
