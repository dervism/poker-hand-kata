package no.dervis.pokerhandkata.domain;

public enum CardGroup {
    SPADES("Spades", "\u2660", "♠"),
    CLUBS("Clubs", "\u2663", "♣"),
    HEARTS("Hearts", "\u2665", "♥"),
    DIAMONDS( "Diamonds", "\u2666", "♦");

    private final String name;
    private final String symbol;

    CardGroup(String name, String unicode, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {return name;}

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
