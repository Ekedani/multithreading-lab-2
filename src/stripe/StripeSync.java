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

        while (pausedThreads < threads.length) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (iteration < finishedIterations) {
                break;
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
        final double[][] lastColumnGroup = threads[threads.length - 1].getBColumnGroup();
        final int lastBColOffset = threads[threads.length - 1].getBOffset();
        for (StripeThread thread : threads) {
            thread.sendDataInCycle();
        }
        threads[0].setBColumnData(lastColumnGroup, lastBColOffset);
    }
}
