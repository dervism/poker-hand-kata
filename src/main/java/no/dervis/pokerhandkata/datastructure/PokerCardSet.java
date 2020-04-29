package no.dervis.pokerhandkata.datastructure;

import no.dervis.pokerhandkata.shared.Options.IncludeJokerCard;


/**
 * The PokerCardSet class represents a poker cardset that
 * can be used in any type of poker games.
 * 
 * @author Dervis Mansuroglu
 * @version 0.0.1
 */
public class PokerCardSet extends CardSet {
	
	/**
	 * Creates a new PokerCardSet
	 */
	public PokerCardSet() {
		super(IncludeJokerCard.JOKER_OFF);
	}
	
	/**
	 * Creates a matrix of poker hands.
	 * 
	 * @param numHands Number of players
	 * @param numCards Number of cards to each player
	 * @return Arrays of poker hands
	 */
	public Card[][] deal(int numHands, int numCards) {
		Card[][] hands = new Card[numHands][];
		shuffle();
		
		for (int i = 0; i < hands.length; i++) {
			hands[i] = getCards(numCards);
		}
				
		return hands;
	}
	
	/**
	 * Draws a certain number of cards from the set.
	 * 
	 * @param numCards Number of cards to draw.
	 * @return A new poker hand
	 */
	public Card[] getCards(int numCards) {
		Card[] hand = new Card[numCards];
		
		if (size() <= numCards) {
			create();
			shuffle();
		}

		for (int i = 0; i < numCards; i++) {
			hand[i] = removeFirst();
		}
		
		return hand;
	}

}
