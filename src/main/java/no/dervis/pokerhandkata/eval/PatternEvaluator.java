package no.dervis.pokerhandkata.eval;

import no.dervis.pokerhandkata.domain.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

	public static PokerPatternType getPokerPattern(Hand hand) {
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
		if (isHouse(cards)) {
			return PokerPatternType.HOUSE;
		}
		if (isFlush(cards)) {
			return PokerPatternType.FLUSH;
		}
		if (isStraight(cards)) {
			return PokerPatternType.STRAIGHT;
		}
		if (isThreeOfAKind(cards)) {
			return PokerPatternType.THREE_OF_A_KIND;
		}
		if (isTwoPairs2(hand)) {
			return PokerPatternType.TWO_PAIRS;
		}
		if (isOnePair(cards)) {
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
		for (int i = 1; i < hand.length; i++) {
			if (hand[i].ordinal() - 1 != hand[i-1].ordinal()) {
				return false;
			}
		}

		return true;
	}
	
	// x-x-x&-x-x, x-x&-x-x-x
	public static boolean isHouse(Card[] hand) {
		var set = groupByTypeAndCount(hand);

		boolean kinds3 = set.containsValue(3);
		boolean kinds2 = set.containsValue(2);

		return kinds3 && kinds2;
	}
	
	// x-x-x-x-0, 0-x-x-x-x
	/**
	 * True if and only if 4 cards in a row is of same type.
	 */
	public static boolean isFourOfAKind(Card[] hand) {
		return groupByTypeAndCount(hand).containsValue(4);
	}
	
	// x-x-x-0-0, 0-x-x-x-0, 0-0-x-x-x
	/**
	 * True if and only if 3 cards in a row is of same type
	 * and has 3 different groups.
	 */
	public static boolean isThreeOfAKind(Card[] hand) {
		return groupByTypeAndCount(hand).containsValue(3);
	}

	private static HashMap<CardType, HashSet<CardGroup>> groupByKind(Card[] hand) {
		HashMap<CardType, HashSet<CardGroup>> set = new HashMap<>();
		for (Card card : hand) {
			Optional.ofNullable(
					set.putIfAbsent(card.getType(), new HashSet<>(Set.of(card.getGroup()))))
					.ifPresent(cardGroups -> cardGroups.add(card.getGroup()));
		}
		return set;
	}

	private static HashMap<CardType, Integer> groupByTypeAndCount(Card[] hand) {
		HashMap<CardType, Integer> set = new HashMap<>();
		for (Card card : hand) {
			Integer count = set.putIfAbsent(card.getType(), 1);
			if (count != null) set.merge(card.getType(), 1, Integer::sum);
		}
		return set;
	}

	// slow
	private static Map<CardType, Long> groupByTypeAndCount2(Card[] hand) {
		return Arrays.stream(hand)
				.collect(Collectors.groupingBy(Card::getType, Collectors.counting()));
	}
	
	// x-x|x-x-0, 0-x-x|x-x, x-x-0-x-x, 0-x-x-0-x-x-0
	/**
	 * Returns true if it finds at least two different pairs.
	 * The pairs can be located in any position.
	 */
	public static boolean isTwoPairs(Hand hand) {
		Pattern topair = Pattern.compile("([2-9TJQKAR])\\1");
		Matcher m = topair.matcher(hand.toShortString());
		
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
		return numPairs >= 2;
	}

	public static boolean isTwoPairs2(Hand hand) {
		String string = hand.toShortString();
		int numPairs = 0;

		char first = 0;
		char second = 0;

		for (int i = 1; i < string.length(); i+=2) {
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