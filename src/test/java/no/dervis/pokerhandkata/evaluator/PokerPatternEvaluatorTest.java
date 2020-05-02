package no.dervis.pokerhandkata.evaluator;

import no.dervis.pokerhandkata.domain.Card;
import no.dervis.pokerhandkata.domain.Hand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PokerPatternEvaluatorTest implements CardUtils {

    @Test
    void isStraight() {
        Card[] cards = textToHand("[2♣][3♣][4♦][5♥][6♠]").getCards();
        assertTrue(PokerPatternEvaluator.isStraight(cards));
    }

    @Test
    void isNotStraight() {
        Card[] cards = textToHand("[2♣][3♣][4♦][4♥][6♠]").getCards();
        assertFalse(PokerPatternEvaluator.isStraight(cards));
    }

    @Test
    void isHouse() {
        Card[] cards = textToHand("[2♣][2♥][2♦][5♥][5♠]").getCards();
        assertTrue(PokerPatternEvaluator.isHouse(cards));
    }

    @Test
    void isNotHouse() {
        Card[] cards = textToHand("[2♣][2♥][9♦][5♥][5♠]").getCards();
        assertFalse(PokerPatternEvaluator.isHouse(cards));
    }

    @Test
    void strictOnePair() {
        Card[] cards = textToHand("[8♦][9♠][T♠][A♣][A♠]").getCards();

        assertTrue(PokerPatternEvaluator.isOnePair(cards));
        assertTrue(PokerPatternEvaluator.isOnePairStrict(cards));
        assertFalse(PokerPatternEvaluator.isTwoPairs(Hand.of(cards)));
        assertFalse(PokerPatternEvaluator.isTwoPairsStrict(cards));
    }

    @Test
    void strictOnePair2() {
        Card[] cards = textToHand("[8♦][9♠][J♠][J♣][K♠]").getCards();

        assertTrue(PokerPatternEvaluator.isOnePair(cards));
        assertTrue(PokerPatternEvaluator.isOnePairStrict(cards));
        assertFalse(PokerPatternEvaluator.isTwoPairs(Hand.of(cards)));
        assertFalse(PokerPatternEvaluator.isTwoPairsStrict(cards));
    }

    @Test
    void strictTwoPairs() {
        Hand hand = textToHand("[7♦][7♥][9♦][T♦][T♥]");

        assertTrue(PokerPatternEvaluator.isTwoPairs(hand));
        assertTrue(PokerPatternEvaluator.isTwoPairsStrict(hand.getCards()));
        assertTrue(PokerPatternEvaluator.isOnePair(hand.getCards()));
        assertTrue(PokerPatternEvaluator.isOnePairStrict(hand.getCards()));
    }

    @Test
    void strictTwoPairs2() {
        Hand hand = textToHand("[3♦][7♦][7♥][T♦][T♥]");

        assertTrue(PokerPatternEvaluator.isTwoPairs(hand));
        assertTrue(PokerPatternEvaluator.isTwoPairsStrict(hand.getCards()));
        assertTrue(PokerPatternEvaluator.isOnePair(hand.getCards()));
        assertTrue(PokerPatternEvaluator.isOnePairStrict(hand.getCards()));
    }
}