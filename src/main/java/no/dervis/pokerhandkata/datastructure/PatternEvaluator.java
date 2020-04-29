package no.dervis.pokerhandkata.datastructure;

import no.dervis.pokerhandkata.shared.CardGroup;
import no.dervis.pokerhandkata.shared.CardType;
import no.dervis.pokerhandkata.shared.PokerPatternType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a very simple poker hand evaluator. It relies
 * on poker hands sorted on card priority (low->high). The
 * evaluator will tell you which pattern it finds on a given
 * poker hand.The evaluator finds patterns, however, it will 
 * not evaluate best 5 out of x>5 cards.
 * 
 * All methods the can search a variable-length poker
 * hand, except "isHouse()" which only searches 5 cards.
 * 
 * In order to get an correct evaluation, patterns have to
 * search in order of poker ranking.
 * 
 * 
 * @author Dervis Mansuroglu
 *
 */
public class PatternEvaluator {

	public static PokerPatternType getPokerPattern(Card[] hand) {
		if (isRoyalFlush(hand)) {
			return PokerPatternType.ROYAL_FLUSH;
		}
		if (isStraightFlush(hand)) {
			return PokerPatternType.STRAIGHT_FLUSH;
		}
		if (isFourOfAKind(hand)) {
			return PokerPatternType.FOUR_OF_A_KIND;
		}
		if (isHouse(hand)) {
			return PokerPatternType.HOUSE;
		}
		if (isFlush(hand)) {
			return PokerPatternType.FLUSH;
		}
		if (isStraight(hand)) {
			return PokerPatternType.STRAIGHT;
		}
		if (isThreeOfAKind(hand)) {
			return PokerPatternType.THREE_OF_A_KIND;
		}
		if (isToPairs(hand)) {
			return PokerPatternType.TWO_PAIRS;
		}
		if (isOnePair(hand)) {
			return PokerPatternType.ONE_PAIR;
		}
		return PokerPatternType.HIGH_CARD;
	}
	
	/**
	 * In a royal flush hand, cards have to be
	 * both straight and flush, with 10 as the
	 * minimal.
	 * 
	 * @param hand
	 * @return
	 */
	public static boolean isRoyalFlush(Card[] hand) {
		if (hand[0].getType() != CardType.TEN) return false;
		return isStraightFlush(hand);
	}
	
	/**
	 * Same rule as for royal flush, however, with
	 * any type of card as minimal.
	 * 
	 * @param hand
	 * @return
	 */
	public static boolean isStraightFlush(Card[] hand) {
		return (isStraight(hand) && isFlush(hand));
	}
	
	/**
	 * True when all cards belong to the same card group (suite)
	 * 
	 * @param hand
	 * @return
	 */
	public static boolean isFlush(Card[] hand) {
		CardGroup cg = hand[0].getGroup();
		for (int i = 1; i < hand.length; i++) {
			if (hand[i].getGroup() != cg) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if a hand is straight. Hand can be
	 * of any length. Checks from right to left.
	 * 
	 * @param hand
	 * @return
	 */
	public static boolean isStraight(Card[] hand) {
		int j = hand.length-2;
		int lastPri = hand[hand.length-1].getPriority();
		
		while(((hand[j].getPriority() + 1) == lastPri) && j>0) {
			lastPri = hand[j].getPriority();
			j--;
		}

		return j == 0;
	}
	
	// x-x-x&-x-x, x-x&-x-x-x
	// should be rewritten to use regular expressions!
	public static boolean isHouse(Card[] hand) {
		if (hand.length>=5) {
			if (((hand[0].getType() == hand[1].getType() &&
					hand[1].getType() == hand[2].getType()) &&
				
				(hand[3].getType() == hand[4].getType()) && hand[2].getType() != hand[3].getType()) ||
				
			((hand[2].getType() == hand[3].getType() &&
					hand[3].getType() == hand[4].getType()) &&
				
				(hand[0].getType() == hand[1].getType()) && hand[4].getType() != hand[0].getType())) {
				return true;
			}
		}
		
		return false;
	}
	
	// x-x-x-x-0, 0-x-x-x-x
	/**
	 * True if and only if 4 cards in a row is of same type.
	 */
	public static boolean isFourOfAKind(Card[] hand) {
		for (int i = 0; i < hand.length; i++) {
			if (i + 3 > hand.length-1) {
				break;
			}
			if(hand[i].getType() == hand[i+1].getType() &&
					hand[i+1].getType() == hand[i+2].getType() &&
					hand[i+2].getType() == hand[i+3].getType()) {
				return true;
			}
		}
		return false;
	}
	
	// x-x-x-0-0, 0-x-x-x-0, 0-0-x-x-x
	/**
	 * True if and only if 3 cards in a row is of same type.
	 */
	public static boolean isThreeOfAKind(Card[] hand) {
		for (int i = 2; i < hand.length; i++) {
			if (hand[i-2].getType() == hand[i-1].getType() 
			 && hand[i-1].getType() == hand[i].getType()) {
				return true;
			}
		}
		return false;
	}
	
	// x-x|x-x-0, 0-x-x|x-x, x-x-0-x-x, 0-x-x-0-x-x-0
	/**
	 * Returns true if it finds at least two different pairs.
	 * The pairs can be located in any position.
	 */
	public static boolean isToPairs(Card[] hand) {
		Hand h = new Hand(hand);
		Pattern topair = Pattern.compile("([2-9TJQKA])\\1");
		Matcher m = topair.matcher(h.getAsString());
		
		int numPairs = 0;
		String lastPair = "";
		String nextPair = "";
		while(m.find()) {
			nextPair = m.group();
			if (!lastPair.equals(nextPair)) {
				numPairs++;
				lastPair = nextPair; 
			}
		}
		if (numPairs >= 2) return true;
		
		return false;
	}
	
	//x-x-0-0-0, 0-x-x-0-0, 0-0-x-x-0, 0-0-0-x-x
	/**
	 * Searches for one pair. Because we will always search
	 * for two pairs and 3 and 4 of a kind first, there is no
	 * point doing an in-depth search. 
	 */
	public static boolean isOnePair(Card[] hand) {
		for (int i = 1; i < hand.length; i++) {
			if (hand[i-1].getType() == hand[i].getType()) {
				return true;
			}
		}
		return false;
	}
}