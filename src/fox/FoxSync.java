package fox;

public class FoxSync {
    private final FoxThread[] threads;
    private int pausedThreads = 0;
    private int finishedIterations = 0;

    public FoxSync(FoxThread[] threads) {
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
        //TODO: Implement cycle sending
    }
}
