package no.dervis.pokerhandkata.eval;

import no.dervis.pokerhandkata.domain.Card;
import no.dervis.pokerhandkata.domain.CardGroup;
import no.dervis.pokerhandkata.domain.CardType;
import no.dervis.pokerhandkata.domain.Hand;
import org.junit.jupiter.api.Test;

import static no.dervis.pokerhandkata.domain.CardType.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatternEvaluatorTest {

    @Test
    void isStraight() {
        Card[] hand = {
                clubs(TWO),
                clubs(THREE),
                diamonds(FOUR),
                hearts(FIVE),
                spades(SIX)
        };
        assertTrue(PatternEvaluator.isStraight(hand));
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
        assertFalse(PatternEvaluator.isStraight(hand));
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
        assertTrue(PatternEvaluator.isHouse(hand));
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
        assertFalse(PatternEvaluator.isHouse(hand));
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
        assertTrue(PatternEvaluator.isTwoPairs(Hand.of(cards)));
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
        assertFalse(PatternEvaluator.isTwoPairs(Hand.of(cards)));
    }



    public Card hearts(CardType type) {
        return new Card(type, CardGroup.HEARTS);
    }

    public Card clubs(CardType type) {
        return new Card(type, CardGroup.CLUBS);
    }

    public Card diamonds(CardType type) {
        return new Card(type, CardGroup.DIAMONDS);
    }

    public Card spades(CardType type) {
        return new Card(type, CardGroup.SPADES);
    }
}