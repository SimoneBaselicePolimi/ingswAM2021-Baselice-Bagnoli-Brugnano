package it.polimi.ingsw.client.cli.graphicutils;

import java.util.Objects;

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
