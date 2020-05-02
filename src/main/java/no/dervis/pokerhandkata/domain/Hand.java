package no.dervis.pokerhandkata.domain;

import no.dervis.pokerhandkata.comparators.OrdinalComparator;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Contains methods for adding, sorting and printing a hand.
 * 
 * @author Dervis Mansuroglu
 *
 */
public class Hand {
	protected Card[] cards;

	public Hand(Card[] hand) {
		cards = hand;
	}

	public static Hand of(Card[] cards) {
		return new Hand(cards);
	}

	public Hand sort(Comparator<Card> comparator) {
		Arrays.sort(cards, comparator);
		return this;
	}

	public Hand sort() {
		Arrays.sort(cards, new OrdinalComparator());
		return this;
	}
	
	public Card[] getCards() {
		return cards;
	}

	/**
	 * Only the card's ranks (without the suits)
	 */
	public String toShortString() {
		StringBuilder b = new StringBuilder(cards.length);
		for (Card card : cards) {
			b.append(card.shortString());
		}

		return b.toString();
	}

	/**
	 * Prints out a human readable text describing
	 * the card values (example: [8♦][9♠][J♠][J♣][K♠]).
	 * 
	 * @return
	 */
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		for(Card card : cards) {
			buffer.append(card);
		}
		return buffer.toString();
	}

}
