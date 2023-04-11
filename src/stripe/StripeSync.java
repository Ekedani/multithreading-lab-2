package stripe;

public class StripeSync {
    private final StripeThread[] threads;
    private int pausedThreads = 0;
    private int finishedIterations = 0;

    public StripeSync(StripeThread[] threads) {
        this.threads = threads;
    }

    public synchronized void waitForNextIteration(int iteration) {
        this.pausedThreads++;
        while (pausedThreads < threads.length || iteration >= finishedIterations) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (pausedThreads == threads.length) {
            updateThreads();
            pausedThreads = 0;
            finishedIterations++;
            notifyAll();
        }
    }

    private void updateThreads() {
        for (StripeThread thread : threads) {
            thread.sendDataInCycle();
        }
    }
}
