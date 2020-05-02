package no.dervis.pokerhandkata.domain;

/**
 * This class defines a card.
 * 
 * @author Dervis Mansuroglu
 *
 */
public class Card implements Comparable<Card>{
	private final CardType type;
	private final CardGroup group;

	public Card(CardType type, CardGroup group) {
		this.type = type;
		this.group = group;
	}

	public CardType getType() {
		return type;
	}

	public int ordinal() {
		return type.ordinal();
	}

	public CardGroup getGroup() {
		return group;
	}

	public final int compareTo(Card c) {
		return Integer.compare(ordinal(), c.ordinal());
	}

	/**
	 * Checks whether two cards are equal.
	 */
	public final boolean equals(Object other) {
		if (other instanceof Card card)
			return type == card.getType() && group == card.getGroup();

		return false;
	}

	public String shortString() {
		return type.getShortName();
	}

	/**
	 * Prints the name and the group of the card.
	 *
	 * @return
	 */
	public String toString() {
		return "["+ type + group + "]";
	}
	
}

