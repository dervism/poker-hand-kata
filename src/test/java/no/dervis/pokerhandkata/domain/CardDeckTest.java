package no.dervis.pokerhandkata.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardDeckTest {

    @Test
    void createWithJokers() {
        CardDeck cs = new CardDeck(CardDeck.IncludeJokerCard.JOKER_ON);
        cs.create();

        var count = cs.stream()
                .filter(card -> card.getType() == CardType.JOKER)
                .count();

        assertEquals(4, count);
    }

    @Test
    void createWithoutJokers() {
        CardDeck cs = new CardDeck(CardDeck.IncludeJokerCard.JOKER_OFF);
        cs.create();

        var count = cs.stream()
                .filter(card -> card.getType() == CardType.JOKER)
                .count();

        assertEquals(0, count);
    }
}