package no.dervis.pokerhandkata.datastructure;

import no.dervis.pokerhandkata.shared.CardGroup;
import no.dervis.pokerhandkata.shared.CardType;
import org.junit.jupiter.api.Test;

class PatternEvaluatorTest {

    @Test
    void isStraight() {
        Card[] hand = {
                new Card(CardType.TWO, CardGroup.CLUBS),
                new Card(CardType.THREE, CardGroup.CLUBS),
                new Card(CardType.FOUR, CardGroup.DIAMONDS),
                new Card(CardType.FIVE, CardGroup.HEARTS),
                new Card(CardType.SIX, CardGroup.SPADES)
        };
    }
}