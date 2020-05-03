package no.dervis.pokerhandkata.simulator;

import no.dervis.pokerhandkata.domain.Hand;
import no.dervis.pokerhandkata.domain.PokerCardDeck;
import no.dervis.pokerhandkata.domain.PokerPatternType;
import no.dervis.pokerhandkata.evaluator.PokerPatternEvaluator;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.lineSeparator;
import static java.lang.System.nanoTime;

/**
 * PokerHandSimulator
 *
 * When executed, you'll get an output such as
 * >>> 1.13 sec
 * High Card 245586(223688) (49.117%)
 * One Pair 230344(220457) (46.069%)
 * Three Of a Kind 11825(11676) (2.365%)
 * Two Pairs 8430(8236) (1.686%)
 * Straight 1731(1574) (0.346%)
 * Flush 1063(989) (0.213%)
 * House 838(833) (0.168%)
 * Four Of a Kind 174(174) (0.035%)
 * Straight Flush 8(5) (0.002%)
 * Royal Flush 1(1) (0%)
 *
 * Output:
 * Poker-pattern x1 (x2) (x3), where
 *
 * x1 = number of times a hand is drawn
 * x2 = number of unique hands
 * x3 = precentage of this rank in the set.
 *
 * @author Dervis Mansuroglu
 * @version 0.1
 */

public class PokerHandSimulator implements Runnable {
    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean stopped = new AtomicBoolean(true);

    @Override
    public void run() {
        Progressbar progressbar = new Progressbar();

        running.set(true);
        stopped.set(false);

        PokerCardDeck cardDeck = new PokerCardDeck();
        cardDeck.create();
        cardDeck.shuffle();
        cardDeck.secureShuffle();

        EnumMap<PokerPatternType, Integer> patterns = new EnumMap<PokerPatternType, Integer>(PokerPatternType.class);
        EnumMap<PokerPatternType, Set<String>> uniques = new EnumMap<>(PokerPatternType.class);

        int numTrials = 500000;
        int numDealt = 0;

        progressbar.start();
        long startTime = nanoTime();

        while (numDealt < numTrials) {
            Hand hand = new Hand(cardDeck.getCards(5));
            PokerPatternType pattern = PokerPatternEvaluator.getPokerPattern(hand.sort(), false);

            var pc = patterns.putIfAbsent(pattern, 1);
            if (pc != null) patterns.put(pattern, patterns.get(pattern) + 1);

            var uc = uniques.putIfAbsent(pattern, new HashSet<>(Set.of(hand.toString())));
            if (uc != null) uniques.get(pattern).add(hand.toString());

            numDealt++;
        }

        progressbar.stop();
        System.out.printf("> %.2f sec" + lineSeparator(), ((nanoTime() - startTime) / 1_000_000_000d));

        TreeSet<Map.Entry<PokerPatternType, Integer>> set = new TreeSet<>(Map.Entry.<PokerPatternType, Integer>comparingByValue().reversed());
        set.addAll(patterns.entrySet());

        DecimalFormat des = new DecimalFormat("##.###");
        for (Map.Entry<PokerPatternType, Integer> entry : set) {
            System.out.println(entry.getKey().getName() + " " + entry.getValue() +
                    "(" + uniques.get(entry.getKey()).size() + ")" +
                    " (" + (des.format(((entry.getValue() / (float) numTrials)) * 100)) + "%) ");
        }

        running.set(false);
        stopped.set(true);
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        running.set(false);
    }

    public void interrupt() {
        running.set(false);
        worker.interrupt();
    }

    boolean isRunning() {
        return running.get();
    }

    boolean isStopped() {
        return stopped.get();
    }

}
