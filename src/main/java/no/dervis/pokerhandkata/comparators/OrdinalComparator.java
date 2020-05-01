/**
 * 
 */
package no.dervis.pokerhandkata.comparators;

import no.dervis.pokerhandkata.domain.Card;

import java.util.Comparator;

/**
 * A simple comparator for comparing two cards.
 * 
 * @author Dervis Mansuroglu
 *
 */
public class OrdinalComparator implements Comparator<Card> {
	public int compare(Card o1, Card o2) {
		return Integer.compare(o1.ordinal(), o2.ordinal());
	}

}
