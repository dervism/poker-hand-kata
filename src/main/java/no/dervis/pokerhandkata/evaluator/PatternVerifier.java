package no.dervis.pokerhandkata.evaluator;

import no.dervis.pokerhandkata.domain.Card;
import no.dervis.pokerhandkata.domain.CardGroup;
import no.dervis.pokerhandkata.domain.CardType;

import java.util.*;
import java.util.stream.Collectors;

public class PatternVerifier {

    public static HashMap<CardType, Set<CardGroup>> groupByTypeAndGroupCount(Card[] hand) {
        HashMap<CardType, Set<CardGroup>> set = new HashMap<>();
        for (Card card : hand) {
            Set<CardGroup> groups = set.putIfAbsent(card.getType(), new HashSet<>(Set.of(card.getGroup())));
            if (groups != null) groups.add(card.getGroup());
        }
        return set;
    }

    /**
     * Same method as groupByTypeAndGroupCount, but with collectors.
     */
    public static Map<CardType, Set<CardGroup>> groupByTypeAndGroupCount2(Card[] hand) {
        return Arrays.stream(hand)
                .collect(Collectors.groupingBy(Card::getType,
                        Collectors.mapping(Card::getGroup, Collectors.toSet())));
    }


    public static HashMap<CardType, Integer> groupByTypeAndCount(Card[] hand) {
        HashMap<CardType, Integer> set = new HashMap<>();
        for (Card card : hand) {
            Integer count = set.putIfAbsent(card.getType(), 1);
            if (count != null) set.merge(card.getType(), 1, Integer::sum);
        }
        return set;
    }

    /**
     * Same method as groupByTypeAndCount, but with collectors.
     */
    public static Map<CardType, Long> groupByTypeAndCount2(Card[] hand) {
        return Arrays.stream(hand)
                .collect(Collectors.groupingBy(Card::getType, Collectors.counting()));
    }

}
