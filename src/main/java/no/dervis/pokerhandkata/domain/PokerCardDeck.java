package no.dervis.pokerhandkata.domain;

public class PokerCardDeck extends CardDeck {
	
	/**
	 * Creates a new PokerCardDeck
	 */
	public PokerCardDeck() {
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
