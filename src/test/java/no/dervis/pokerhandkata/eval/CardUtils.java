package no.dervis.pokerhandkata.eval;

import no.dervis.pokerhandkata.domain.Card;
import no.dervis.pokerhandkata.domain.CardGroup;
import no.dervis.pokerhandkata.domain.CardType;
import no.dervis.pokerhandkata.domain.Hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface CardUtils {

    public default Card hearts(CardType type) {
        return new Card(type, CardGroup.HEARTS);
    }

    public default Card clubs(CardType type) {
        return new Card(type, CardGroup.CLUBS);
    }

    public default Card diamonds(CardType type) {
        return new Card(type, CardGroup.DIAMONDS);
    }

    public default Card spades(CardType type) {
        return new Card(type, CardGroup.SPADES);
    }

    public default Hand textToHand(String hand) {
        List<Card> list = new ArrayList<>();
        hand = hand.replaceAll("\\[", "").replaceAll("\\]", "");
        for (int i = 0; i < hand.toCharArray().length; i+=2) {
            final String ik = Character.toString(hand.charAt(i));
            final String ij = Character.toString(hand.charAt(i+1));
            CardType type = Arrays
                    .stream(CardType.values()).filter(c -> c.getShortName().equals(ik)).findAny().orElseThrow();
            CardGroup group = Arrays
                    .stream(CardGroup.values()).filter(g -> g.getSymbol().equals(ij)).findAny().orElseThrow();
            list.add(new Card(type, group));
        }

        return new Hand(list.toArray(new Card[]{}));
    }
}
