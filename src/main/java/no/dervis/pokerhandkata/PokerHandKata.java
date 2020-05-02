package no.dervis.pokerhandkata;

import no.dervis.pokerhandkata.domain.Hand;
import no.dervis.pokerhandkata.domain.PokerCardDeck;
import no.dervis.pokerhandkata.domain.PokerPatternType;
import no.dervis.pokerhandkata.eval.PokerPatternEvaluator;

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
 >>> 1.13 sec
 High Card 245586(223688) (49.117%)
 One Pair 230344(220457) (46.069%)
 Three Of a Kind 11825(11676) (2.365%)
 Two Pairs 8430(8236) (1.686%)
 Straight 1731(1574) (0.346%)
 Flush 1063(989) (0.213%)
 House 838(833) (0.168%)
 Four Of a Kind 174(174) (0.035%)
 Straight Flush 8(5) (0.002%)
 Royal Flush 1(1) (0%)
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
        cardDeck.secureShuffle();

        EnumMap<PokerPatternType, Integer> patterns = new EnumMap<PokerPatternType, Integer>(PokerPatternType.class);
        EnumMap<PokerPatternType, Set<String>> uniques = new EnumMap<>(PokerPatternType.class);

        int numTrials = 5000000;
        int numDealt = 0;

        long startTime = nanoTime();

        while (numDealt < numTrials) {
            Hand hand = new Hand(cardDeck.getCards(5));
            PokerPatternType pattern = PokerPatternEvaluator.getPokerPattern(hand.sort(), true);
            PokerPatternType pattern2 = PokerPatternEvaluator.getPokerPattern(hand.sort(), false);

            if (pattern != pattern2) {
                progressbar.set(false);
                throw new IllegalArgumentException("Strict "  +
                        pattern + " != non-strict " + pattern2 + ": " + hand);
            }

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
