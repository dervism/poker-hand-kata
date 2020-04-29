package no.dervis.pokerhandkata.datastructure;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains methods for adding, removing and printing
 * a hand.
 * 
 * @author Dervis Mansuroglu
 *
 */
public class Hand {
	protected ArrayList<Card> cards;

	public Hand() {
		cards = new ArrayList<>();
	}
	
	public Hand(Card[] hand) {
		cards = new ArrayList<>();
		setHand(hand);
	}
	
	public void addCard(Card c) {
		cards.add(c);
	}
	
	public void addCard(Card c, int pos) {
		cards.add(pos, c);
	}
	
	public void removeCard(Card c) {
		cards.remove(c);
	}
	
	public void removeCard(int pos) {
		cards.remove(pos);
	}
	
	public int size() {
		return cards.size();
	}
	
	public Card[] cloneCards() {
		Card[] tmp = new Card[cards.size()];
		int i = 0;
		for(Card card : cards) {
			tmp[i++] = card;
		}
		
		return tmp;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public void setHand(Card[] hand) {
		cards.clear();
		cards.addAll(Arrays.asList(hand));
	}
	
	/**
	 * Prints out a human readable text describing
	 * the card values (example: 789TK).
	 * 
	 * @return
	 */
	public String getAsString() {
		StringBuffer buffer = new StringBuffer();
		for(Card card : cards) {
			buffer.append(card.getType().getPrefix());
		}
		return buffer.toString();
	}

}
