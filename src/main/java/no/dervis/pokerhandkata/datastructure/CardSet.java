/**
 *
 */
package no.dervis.pokerhandkata.datastructure;

import no.dervis.pokerhandkata.shared.CardGroup;
import no.dervis.pokerhandkata.shared.CardType;
import no.dervis.pokerhandkata.shared.Options;
import no.dervis.pokerhandkata.shared.Options.IncludeJokerCard;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * A CardSet represents a card deck and contains utility
 * methods for handling the deck. The card set has the possibiltiy
 * of setting and changning a highest priority card in the deck.
 *
 * @author Dervis Mansuroglu
 *
 */
public class CardSet {
    protected LinkedList<Card> list = new LinkedList<Card>();
    protected IncludeJokerCard includeJokerStatus;

    /**
     * Creates a standard CardSet
     */
    public CardSet(IncludeJokerCard jokerstat) {
        includeJokerStatus = jokerstat;
    }

    /**
     * Creates a new deck with/without priorities
     */
    public void create() {
        for (CardGroup group : CardGroup.values()) {
            for (CardType type : CardType.values()) {
                if (type == CardType.JOKER && includeJokerStatus == Options.IncludeJokerCard.JOKER_OFF) {
                    continue;
                }

                list.add(new Card(type, group));
            }
        }
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

    /**
     * Sort the deck with a given comparator
     * @param comparator Specifies how to sort the deck
     */
    public void sort(Comparator<Card> comparator) {
        Collections.sort(list, comparator);
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
    public void print(boolean prefix) {
        for (Card c : list) {
            System.out.println(c.toString(prefix));
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
