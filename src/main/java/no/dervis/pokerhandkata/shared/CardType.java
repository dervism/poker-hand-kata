package no.dervis.pokerhandkata.shared;

public enum CardType {
    TWO("Two", "2", 2),
    THREE("Three", "3", 3),
    FOUR("Four", "4", 4),
    FIVE("Five", "5", 5),
    SIX("Six", "6", 6),
    SEVEN("Seven", "7", 7),
    EIGHT("Eight", "8", 8),
    NINE("Nine", "9", 9),
    TEN("Ten", "T", 10),
    JACK("Jack", "J", 11),
    QUEEN("Queen", "Q", 12),
    KING("King", "K", 13),
    ACE("Ace", "A", 14),
    JOKER("Joker", "R", 15);

    private String name;
    private String prefix;
    private int index;
    CardType(String name, String prefix, int index) {
        this.name = name;
        this.prefix = prefix;
        this.index = index;
    }

    public String getName() {return name;}
    public String getPrefix() {return prefix;}
    public int getIndex() {return index;}
}
