package it.polimi.ingsw.client.cli.graphicutils;

public class FormattedChar {

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

}
