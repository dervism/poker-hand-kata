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
    private final String label;
    CardType(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
