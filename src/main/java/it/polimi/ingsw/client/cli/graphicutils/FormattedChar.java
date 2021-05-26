package it.polimi.ingsw.client.cli.graphicutils;

import java.util.ArrayList;
import java.util.List;

public class FormattedChar {

    public static final String ESCAPE_CMD_RESET_FORMAT = "\u001B[0m";

    public static final String ESCAPE_CMD_PREFIX = "\u001B[";
    public static final String ESCAPE_CMD_SUFFIX = "m";

    public static final String RGB_FG_COLOR_CODE_PREFIX = "38;2";
    public static final String RGB_BG_COLOR_CODE_PREFIX = "48;2";

    public static final String BOLD_CODE = "1";
    public static final String DISABLE_BOLD_CODE = "21";

    public static final String ITALIC_CODE = "3";
    public static final String DISABLE_ITALIC_CODE = "23";

    public static final String UNDERLINE_CODE = "4";
    public static final String DISABLE_UNDERLINE_CODE = "24";

    public final char character;
    public final CliColour foregroundColour;
    public final CliColour backgroundColour;
    public final boolean isBold;
    public final boolean isItalic;
    public final boolean isUnderlined;

    public static String getResetFormatCmd() {
        return ESCAPE_CMD_RESET_FORMAT;
    }

    public FormattedChar(
        char character
    ) {
        this(character, CliColour.WHITE, CliColour.BLACK);
    }

    public FormattedChar(
        char character,
        CliColour foregroundColour,
        CliColour backgroundColour
    ) {
        this(character, foregroundColour, backgroundColour, false, false);
    }

    public FormattedChar(
        char character,
        CliColour foregroundColour,
        CliColour backgroundColour,
        boolean isBold,
        boolean isUnderlined
    ) {
        this(character, foregroundColour, backgroundColour, isBold, isUnderlined, false);
    }

    public FormattedChar(
        char character,
        CliColour foregroundColour,
        CliColour backgroundColour,
        boolean isBold,
        boolean isItalic,
        boolean isUnderlined
    ) {
        this.character = character;
        this.foregroundColour = foregroundColour;
        this.backgroundColour = backgroundColour;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.isUnderlined = isUnderlined;
    }

    /**
     * Returns the command (ANSI escape code) that allows printing of a char with the specified formatting options.
     * Note: Does not include char
     * @return the ANSI command
     */
    public String getCompleteFormattingCmd() {
        List<Object> codes = new ArrayList<>(16);
        codes.add(isBold ? BOLD_CODE : DISABLE_BOLD_CODE);
        codes.add(isItalic ? ITALIC_CODE : DISABLE_ITALIC_CODE);
        codes.add(isUnderlined ? UNDERLINE_CODE : DISABLE_UNDERLINE_CODE);
        codes.addAll(List.of(RGB_FG_COLOR_CODE_PREFIX, foregroundColour.r, foregroundColour.g, foregroundColour.b));
        codes.addAll(List.of(RGB_BG_COLOR_CODE_PREFIX, backgroundColour.r, backgroundColour.g, backgroundColour.b));
        return getCommandWithCodes(codes);
    }

    /**
     * Returns a command (ANSI escape code) that includes only the formatting options that are different from the
     * ones in lastFormattedChar.
     * Note: Does not include char
     * @param lastFormattedChar lastFormattedChar
     * @return the ANSI command
     */
    public String getDeltaFormattingCmd(FormattedChar lastFormattedChar) {
        if(this.equals(lastFormattedChar))
            return "";
        List<Object> codes = new ArrayList<>(16);
        if(isBold != lastFormattedChar.isBold)
            codes.add(isBold ? BOLD_CODE : DISABLE_BOLD_CODE);
        if(isItalic != lastFormattedChar.isItalic)
            codes.add(isItalic ? ITALIC_CODE : DISABLE_ITALIC_CODE);
        if(isUnderlined != lastFormattedChar.isUnderlined)
            codes.add(isUnderlined ? UNDERLINE_CODE : DISABLE_UNDERLINE_CODE);
        if(!foregroundColour.equals(lastFormattedChar.foregroundColour))
            codes.addAll(List.of(RGB_FG_COLOR_CODE_PREFIX, foregroundColour.r, foregroundColour.g, foregroundColour.b));
        if(!backgroundColour.equals(lastFormattedChar.backgroundColour))
            codes.addAll(List.of(RGB_BG_COLOR_CODE_PREFIX, backgroundColour.r, backgroundColour.g, backgroundColour.b));
        return getCommandWithCodes(codes);
    }

    protected String getCommandWithCodes(List<Object> codes) {
        StringBuilder command = new StringBuilder();
        if(codes.size() > 0) {
            command.append(ESCAPE_CMD_PREFIX);
            command.append(codes.get(0));
            for(Object code : codes.subList(1, codes.size()))
                command.append(";").append(code);
            command.append(ESCAPE_CMD_SUFFIX);
        }
        return command.toString();
    }

}
