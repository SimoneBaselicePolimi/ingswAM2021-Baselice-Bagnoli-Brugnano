package it.polimi.ingsw.client.cli.graphicutils;

import java.util.Objects;

public class CliColour {

    public static final CliColour BLACK = new CliColour(0,0,0);
    public static final CliColour WHITE = new CliColour(255,255,255);
    public static final CliColour RED = new CliColour(255,0,0);
    public static final CliColour GREEN = new CliColour(0,255,0);
    public static final CliColour GREY = new CliColour(100, 100, 100) ;

    public final int r, g, b;

    public CliColour(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CliColour cliColour = (CliColour) o;
        return r == cliColour.r && g == cliColour.g && b == cliColour.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

}
