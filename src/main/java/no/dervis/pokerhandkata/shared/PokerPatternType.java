package no.dervis.pokerhandkata.shared;

public enum PokerPatternType {
    HIGH_CARD("High Card"),
    ONE_PAIR("One Pair"),
    TWO_PAIRS("Two Pairs"),
    THREE_OF_A_KIND("Three Of a Kind"),
    HOUSE("House"),
    FOUR_OF_A_KIND("Four Of a Kind"),
    STRAIGHT("Straight"),
    FLUSH("Flush"),
    STRAIGHT_FLUSH("Straight Flush"),
    ROYAL_FLUSH("Royal Flush");

    private String name;
    PokerPatternType(String name) {
        this.name = name;
    }
    public String getName() {return name;}
}
