package it.polimi.ingsw.client.cli.graphicutils;

public enum ConsoleFGColour {
    BLACK("30"),
    RED("31"),
    GREEN("32"),
    WHITE("37");

    String colourCode;

    ConsoleFGColour(String colourCode) {
        this.colourCode = colourCode;
    }

    public String getColourCode() {
        return colourCode;
    }
}
