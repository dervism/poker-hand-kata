package no.dervis.pokerhandkata.datastructure;

import no.dervis.pokerhandkata.shared.CardGroup;
import no.dervis.pokerhandkata.shared.CardType;

/**
 * This class defines a playing card. 
 * 
 * @author Dervis Mansuroglu
 *
 */
public class Card implements Comparable<Card>{
	private final CardType type;
	private final CardGroup group;
	private int priority;

	public Card(CardType type, CardGroup group) {
		this.type = type;
		this.group = group;
	}

	public Card(CardType type, CardGroup group, int priority) {
		this.type = type;
		this.group = group;
		this.priority = priority;
	}

	public CardType getType() {
		return type;
	}

	public CardGroup getGroup() {
		return group;
	}

	public int getPriority() {
		return priority;
	}
	
	public int getIndex() {
		return type.getIndex();
	}

	public final int compareTo(Card c) {
		return Integer.compare(type.getIndex(), c.getIndex());
	}

	/**
	 * Checks whether two cards are meaningfully equal.
	 */
	public final boolean equals(Object other) {
		if (other instanceof Card card)
			return type == card.getType() && group == card.getGroup();

		return false;
	}

	/**
	 * Prints the name and the group of the card.
	 * 
	 * @param prefix If true, prints out "A" instead of "Ace". 
	 * @return
	 */
	public String toString(boolean prefix) {
		return "["+ (prefix?type.getPrefix():type.getName()) 
		+" of "+group.getName()+"]";
	}
	
}

