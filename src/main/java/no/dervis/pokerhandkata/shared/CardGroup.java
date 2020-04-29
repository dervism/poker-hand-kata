package no.dervis.pokerhandkata.shared;

public enum CardGroup {
    HEARTS("Hearts"),
    SPADES("Spades"),
    DIAMONDS( "Diamonds"),
    CLUBS("Clubs");

    private String name;
    CardGroup(String name){
        this.name = name;
    }

    public String getName() {return name;}
}
