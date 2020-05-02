package no.dervis.pokerhandkata.eval;

import no.dervis.pokerhandkata.domain.Card;
import no.dervis.pokerhandkata.domain.Hand;
import org.junit.jupiter.api.Test;

import static no.dervis.pokerhandkata.domain.CardType.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PokerPatternEvaluatorTest implements CardUtils {

    @Test
    void isStraight() {
        Card[] hand = {
                clubs(TWO),
                clubs(THREE),
                diamonds(FOUR),
                hearts(FIVE),
                spades(SIX)
        };
        assertTrue(PokerPatternEvaluator.isStraight(hand));
    }

    @Test
    void isNotStraight() {
        Card[] hand = {
                clubs(TWO),
                clubs(THREE),
                diamonds(FOUR),
                hearts(FOUR),
                spades(SIX)
        };
        assertFalse(PokerPatternEvaluator.isStraight(hand));
    }

    @Test
    void isHouse() {
        Card[] hand = {
                clubs(TWO),
                hearts(TWO),
                diamonds(TWO),
                hearts(FIVE),
                spades(FIVE)
        };
        assertTrue(PokerPatternEvaluator.isHouse(hand));
    }

    @Test
    void isNotHouse() {
        Card[] hand = {
                clubs(TWO),
                hearts(TWO),
                diamonds(NINE),
                hearts(FIVE),
                spades(FIVE)
        };
        assertFalse(PokerPatternEvaluator.isHouse(hand));
    }

    @Test
    void isTwoPairs() {
        Card[] cards = {
                clubs(TWO),
                hearts(TWO),
                hearts(FIVE),
                spades(FIVE),
                diamonds(NINE)
        };
        assertTrue(PokerPatternEvaluator.isTwoPairs(Hand.of(cards)));
    }

    @Test
    void isNotTwoPairs() {
        Card[] cards = {
                clubs(TWO),
                hearts(TWO),
                hearts(FIVE),
                spades(SEVEN),
                diamonds(NINE)
        };
        assertFalse(PokerPatternEvaluator.isTwoPairs(Hand.of(cards)));
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
    void strictTwoPairs() {
        Hand hand = textToHand("[7♦][7♥][9♦][T♦][T♥]");

        assertTrue(PokerPatternEvaluator.isTwoPairs(hand));
        assertTrue(PokerPatternEvaluator.isTwoPairsStrict(hand.getCards()));
        assertTrue(PokerPatternEvaluator.isOnePair(hand.getCards()));
        assertTrue(PokerPatternEvaluator.isOnePairStrict(hand.getCards()));
    }
}