package it.polimi.ingsw.client.cli.graphicutils;

public enum ConsoleBGColour {
    BLACK("40"),
    RED("41"),
    GREEN("42");

    String colourCode;

    ConsoleBGColour(String colourCode) {
        this.colourCode = colourCode;
    }

    public String getColourCode() {
        return colourCode;
    }
}
