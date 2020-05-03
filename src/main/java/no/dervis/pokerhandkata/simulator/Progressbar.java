package no.dervis.pokerhandkata.simulator;

import java.util.concurrent.atomic.AtomicBoolean;

public class Progressbar implements Runnable {
    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean stopped = new AtomicBoolean(true);

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

    @Override
    public void run() {
        int tmp = 0, interval = 300;

        running.set(true);
        stopped.set(false);

        while (running.get()) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (running.get()) {
                if (++tmp % 40 == 0) {
                    System.out.print("|\n");
                } else System.out.print("|");
            }
        }

        stopped.set(true);
    }
}
