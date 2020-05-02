package no.dervis.pokerhandkata.evaluator;

import no.dervis.pokerhandkata.domain.Card;
import no.dervis.pokerhandkata.domain.CardType;
import no.dervis.pokerhandkata.domain.Hand;
import no.dervis.pokerhandkata.domain.PokerPatternType;

/**
 * This is a very simple poker hand evaluator. It relies
 * on poker hands sorted on card value (low->high).
 * 
 * All methods search a variable-length poker.
 * 
 * In order to get an correct evaluation, patterns have to
 * search in order of poker ranking.
 * 
 * @author Dervis Mansuroglu
 *
 */
public class PokerPatternEvaluator extends PatternVerifier {

	public static PokerPatternType getPokerPattern(Hand hand, boolean strict) {
		Card[] cards = hand.getCards();

		if (isRoyalFlush(cards)) {
			return PokerPatternType.ROYAL_FLUSH;
		}
		if (isStraightFlush(cards)) {
			return PokerPatternType.STRAIGHT_FLUSH;
		}
		if (isFourOfAKind(cards)) {
			return PokerPatternType.FOUR_OF_A_KIND;
		}
		if ((strict && isHouseStrict(cards)) || isHouse(cards)) {
			return PokerPatternType.HOUSE;
		}
		if (isFlush(cards)) {
			return PokerPatternType.FLUSH;
		}
		if (isStraight(cards)) {
			return PokerPatternType.STRAIGHT;
		}
		if ((strict && isThreeOfAKindStrict(cards)) || isThreeOfAKind(cards)) {
			return PokerPatternType.THREE_OF_A_KIND;
		}
		if ((strict && isTwoPairsStrict(cards)) || isTwoPairs(hand)) {
			return PokerPatternType.TWO_PAIRS;
		}
		if ((strict && isOnePairStrict(cards)) || isOnePair(cards)) {
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
		for (int i = 1; i < hand.length; i++) {
			if (hand[i].getGroup() != hand[i-1].getGroup()) {
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
		for (int i = 1; i < hand.length; i++) {
			if (hand[i].ordinal() - 1 != hand[i-1].ordinal()) {
				return false;
			}
		}

		return true;
	}

	public static boolean isHouse(Card[] hand) {
		var set = groupByTypeAndCount(hand);

		boolean kinds3 = set.containsValue(3);
		boolean kinds2 = set.containsValue(2);

		return kinds3 && kinds2;
	}

	public static boolean isHouseStrict(Card[] hand) {
		var set = groupByTypeAndGroupCount(hand);

		boolean size = set.size() >= 2;
		boolean kinds3 = set.values().stream().anyMatch(g -> g.size() == 3);
		boolean kinds2 = set.values().stream().anyMatch(g -> g.size() == 2);

		return size && kinds3 && kinds2;
	}

	/**
	 * True if and only if 4 cards in a row is of same type.
	 */
	public static boolean isFourOfAKind(Card[] hand) {
		return groupByTypeAndCount(hand).containsValue(4);
	}

	public static boolean isFourOfAKindStrict(Card[] hand) {
		return groupByTypeAndGroupCount(hand)
				.values().stream().anyMatch(g -> g.size() == 4);
	}

	/**
	 * True if and only if 3 cards in a row is of same type
	 * and has 3 different groups.
	 */
	public static boolean isThreeOfAKind(Card[] hand) {
		return groupByTypeAndCount(hand).containsValue(3);
	}

	public static boolean isThreeOfAKindStrict(Card[] hand) {
		return groupByTypeAndGroupCount(hand)
				.values().stream().anyMatch(g -> g.size() == 3);
	}

	/**
	 * Returns true if it finds at least two different pairs.
	 * The pairs can be located in any position.
	 */
	public static boolean isTwoPairs(Hand hand) {
		String string = hand.toShortString();
		int numPairs = 0;
		char first, second = 0;

		for (int i = 1; i < string.length(); i++) {
			char c = string.charAt(i-1);
			char c2 = string.charAt(i);
			if (c == c2) {
				first = c;
				if (second != first) {
					second = first;
					numPairs++;
				}
			}

		}

		return numPairs >= 2;
	}

	public static boolean isTwoPairsStrict(Card[] hand) {
		var set = groupByTypeAndGroupCount(hand);

		boolean size = set.size() >= 2;
		long c = set.values().stream().filter(g -> g.size() == 2).count();

		return size && c >= 2;
	}

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

	public static boolean isOnePairStrict(Card[] hand) {
		for (int i = 1; i < hand.length; i++) {
			if (hand[i-1].getType() == hand[i].getType()
					&& hand[i-1].getGroup() != hand[i].getGroup()) {
				return true;
			}
		}
		return false;
	}
}