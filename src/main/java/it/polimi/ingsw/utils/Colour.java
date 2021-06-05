package it.polimi.ingsw.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Colour {

    public static final Colour BLACK = new Colour(0,0,0);
    public static final Colour WHITE = new Colour(255,255,255);
    public static final Colour RED = new Colour(255,0,0);
    public static final Colour GREEN = new Colour(0,255,0);
    public static final Colour GREY = new Colour(100, 100, 100);
    public static final Colour BLUE = new Colour(0,0,255);

    public final int r, g, b;

    public Colour(
        @JsonProperty("r") int r,
        @JsonProperty("g") int g,
        @JsonProperty("b") int b
    ) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colour colour = (Colour) o;
        return r == colour.r && g == colour.g && b == colour.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

}
