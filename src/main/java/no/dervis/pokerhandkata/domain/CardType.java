package no.dervis.pokerhandkata.domain;

public enum CardType {
    TWO("Two", "2"),
    THREE("Three", "3"),
    FOUR("Four", "4"),
    FIVE("Five", "5"),
    SIX("Six", "6"),
    SEVEN("Seven", "7"),
    EIGHT("Eight", "8"),
    NINE("Nine", "9"),
    TEN("Ten", "T"),
    JACK("Jack", "J"),
    QUEEN("Queen", "Q"),
    KING("King", "K"),
    ACE("Ace", "A"),
    JOKER("Joker", "R");

    private final String name;
    private final String prefix;
    CardType(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return prefix;
    }
}
