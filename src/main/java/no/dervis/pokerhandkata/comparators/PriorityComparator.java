/**
 * 
 */
package no.dervis.pokerhandkata.comparators;

import no.dervis.pokerhandkata.datastructure.Card;

import java.util.Comparator;

/**
 * A simple comparator for comparing two cards.
 * 
 * @author Dervis Mansuroglu
 *
 */
public class PriorityComparator implements Comparator<Card> {
	public int compare(Card o1, Card o2) {
		return Integer.compare(o1.getPriority(), o2.getPriority());
	}

}
