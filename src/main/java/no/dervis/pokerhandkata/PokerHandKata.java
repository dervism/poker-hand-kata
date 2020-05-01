package no.dervis.pokerhandkata;

import no.dervis.pokerhandkata.domain.Hand;
import no.dervis.pokerhandkata.domain.PokerCardDeck;
import no.dervis.pokerhandkata.domain.PokerPatternType;
import no.dervis.pokerhandkata.eval.PatternEvaluator;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.lineSeparator;
import static java.lang.System.nanoTime;

/**
 * The PokerHandSimulator application is created for educational
 * purposes and illustrates a number of different Java 5 features.
 * Among them: enums, generic types, for-each loops, comparators,
 * the Java Collection Framework (HashSet, EnumMap, ArrayList, LinkedList),
 * sorting and ordering.
 * <p>
 * When executed, you'll get an output such as
 * <p>
 * >>>>>>>>>>>>>>>>>>>>> 6.422sec
 * High Card 240055(1251) (48,011%)
 * One Pair 213225(2851) (42,645%)
 * Two Pairs 24990(858) (4,998%)
 * Three Of a Kind 12135(858) (2,427%)
 * Straight 7583(45) (1,517%)
 * Flush 950(721) (0,19%)
 * House 848(154) (0,17%)
 * Four Of a Kind 189(104) (0,038%)
 * Straight Flush 25(18) (0,005%)
 * <p>
 * rank-name x1 (x2) (x3), where
 * <p>
 * x1 = number of times a hand is drawn
 * x2 = number of unique hands
 * x3 = precentage of this rank in the set.
 *
 * @author Dervis Mansuroglu
 * @version 0.1
 */
public class PokerHandKata {
    public static void main(String[] args) {
        final AtomicBoolean progressbar = new AtomicBoolean(true);

        Thread t = new Thread(() -> {
            int tmp = 0;
            while (progressbar.get()) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (progressbar.get()) {
                    if (++tmp % 40 == 0) {
                        System.out.print(">\n");
                    } else System.out.print(">");
                }
            }

        });
        t.start();

        PokerCardDeck cardDeck = new PokerCardDeck();
        cardDeck.create();
        cardDeck.shuffle();

        EnumMap<PokerPatternType, Integer> patterns = new EnumMap<PokerPatternType, Integer>(PokerPatternType.class);
        EnumMap<PokerPatternType, Set<String>> uniques = new EnumMap<>(PokerPatternType.class);

        int numTrials = 500000;
        int numDealt = 0;

        long startTime = nanoTime();

        while (numDealt < numTrials) {
            Hand hand = new Hand(cardDeck.getCards(5));
            PokerPatternType pattern = PatternEvaluator.getPokerPattern(hand.sort());

            var pc = patterns.putIfAbsent(pattern, 1);
            if (pc != null) patterns.put(pattern, patterns.get(pattern) + 1);

            var uc = uniques.putIfAbsent(pattern, new HashSet<>(Set.of(hand.toString())));
            if (uc != null) uniques.get(pattern).add(hand.toString());

            numDealt++;
        }

        progressbar.set(false);
        System.out.printf(" %.2f sec" + lineSeparator(), ((nanoTime() - startTime) / 1_000_000_000d));

        TreeSet<Map.Entry<PokerPatternType, Integer>> set = new TreeSet<>(Map.Entry.<PokerPatternType, Integer>comparingByValue().reversed());
        set.addAll(patterns.entrySet());

        DecimalFormat des = new DecimalFormat("##.###");
        for (Map.Entry<PokerPatternType, Integer> entry : set) {
            System.out.println(entry.getKey().getName() + " " + entry.getValue() +
                    "(" + uniques.get(entry.getKey()).size() + ")" +
                    " (" + (des.format(((entry.getValue() / (float) numTrials)) * 100)) + "%) ");
        }
    }

}
