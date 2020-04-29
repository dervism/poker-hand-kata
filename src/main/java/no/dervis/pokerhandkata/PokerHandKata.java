package no.dervis.pokerhandkata;

import no.dervis.pokerhandkata.comparators.PriorityComparator;
import no.dervis.pokerhandkata.datastructure.Card;
import no.dervis.pokerhandkata.datastructure.Hand;
import no.dervis.pokerhandkata.datastructure.PatternEvaluator;
import no.dervis.pokerhandkata.datastructure.PokerCardSet;
import no.dervis.pokerhandkata.shared.PokerPatternType;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

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

                if (++tmp % 40 == 0) {
                    System.out.print(">\n");
                } else System.out.print(">");
            }

        });
        t.start();

        long tt = System.currentTimeMillis();

        PokerCardSet set = new PokerCardSet();
        set.create();
        set.shuffle();

        EnumMap<PokerPatternType, Integer> patterns =
                new EnumMap<PokerPatternType, Integer>(PokerPatternType.class);

        EnumMap<PokerPatternType, HashSet<String>> uniques =
                new EnumMap<PokerPatternType, HashSet<String>>(PokerPatternType.class);

        int numTrials = 500000;
        int numDealt = 0;

        while (numDealt < numTrials) {
            Card[] hand = set.getCards(5);
            Arrays.sort(hand, new PriorityComparator());
            PokerPatternType pattern = PatternEvaluator.getPokerPattern(hand);
            Hand h = new Hand(hand);
            Integer num = patterns.get(pattern);
            HashSet<String> hashset = uniques.get(pattern);

            if (num == null) {
                patterns.put(pattern, 1);
            } else {
                num++;
                patterns.put(pattern, num);
            }

            if (hashset == null) {
                hashset = new HashSet<>();
                hashset.add(h.getAsString());
                uniques.put(pattern, hashset);
            } else
                hashset.add(h.getAsString());

            numDealt++;
        }

        System.out.println(" " + ((System.currentTimeMillis() - tt) / 1000f) + "sec");
        DecimalFormat des = new DecimalFormat("##.###");
        for (PokerPatternType pattern : patterns.keySet()) {
            System.out.println(pattern.getName() + " " + patterns.get(pattern) +
                    "(" + uniques.get(pattern).size() + ")" +
                    " (" + (des.format(((patterns.get(pattern) / (float) numTrials)) * 100)) + "%) ");
        }

        progressbar.set(false);
    }

}
