package no.dervis.pokerhandkata.domain;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Stream;

import static no.dervis.pokerhandkata.domain.CardDeck.IncludeJokerCard.JOKER_ON;
import static no.dervis.pokerhandkata.domain.CardType.JOKER;

/**
 * A CardSet represents a card deck and contains utility
 * methods for handling the deck. The card set has the possibiltiy
 * of setting and changning a highest priority card in the deck.
 *
 * @author Dervis Mansuroglu
 *
 */
public class CardDeck {
    protected LinkedList<Card> list = new LinkedList<Card>();
    protected IncludeJokerCard includeJokerStatus;

    /**
     * Creates a standard CardSet
     */
    public CardDeck(IncludeJokerCard jokerstat) {
        includeJokerStatus = jokerstat;
    }

    public enum IncludeJokerCard {
        JOKER_ON, JOKER_OFF;
    }

    /**
     * Creates a new deck
     */
    public void create() {
        for (CardGroup group : CardGroup.values()) {
            for (CardType type : CardType.values())
                if (type != JOKER || includeJokerStatus == JOKER_ON)
                    list.add(new Card(type, group));
        }
    }

    public Stream<Card> stream() {
        return list.stream();
    }

    /**
     * Creates n number of decks
     * @param nDecks Number of decks to create
     */
    public void create(int nDecks) {
        for (int i = 0; i < nDecks; i++) {
            create();
        }
    }

    /**
     * Rolls all cards in the deck
     * @param steps Number of steps to roll
     */
    public void rollCards(int steps) {
        int stepsTaken = 0;
        while (stepsTaken < steps) {
            Card tmp = removeFirst();
            addLast(tmp);
            stepsTaken++;
        }
    }

    /**
     * Look at next card, then put it back
     * @return The card at top
     */
    public Card peek() {
        Card tmp = removeFirst();
        addLast(tmp);
        return tmp;
    }

    /**
     * Shuffles the cards
     */
    public void shuffle() {
        Collections.shuffle(list);
    }

    public void secureShuffle() {
        Collections.shuffle(list, new SecureRandom());
    }

    /**
     * Sort the deck with a given comparator
     * @param comparator Specifies how to sort the deck
     */
    public void sort(Comparator<Card> comparator) {
        list.sort(comparator);
    }

    /**
     * Sorts the deck by card index
     */
    public void sort() {
        Collections.sort(list);
    }

    /**
     * Prints the deck
     */
    public void print() {
        for (Card c : list) {
            System.out.println(c.toString());
        }
        System.out.println("Size: " + size());
    }

    public void addLast(Card o) {
        list.addLast(o);
    }

    public Card removeFirst() {
        return list.removeFirst();
    }

    public int size() {
        return list.size();
    }
}
