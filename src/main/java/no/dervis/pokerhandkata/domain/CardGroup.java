package no.dervis.pokerhandkata.domain;

public enum CardGroup {
    HEARTS("Hearts", "♥"),
    SPADES("Spades", "♠"),
    DIAMONDS( "Diamonds", "♦"),
    CLUBS("Clubs", "♣");

    private final String name;
    private final String symbol;

    CardGroup(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {return name;}

    @Override
    public String toString() {
        return symbol;
    }
}
