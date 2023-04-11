package stripe;

import common.Result;

public class StripeThread extends Thread {
    private final double[][] aRowGroup;
    private final int aOffset;
    private double[][] bColumnGroup;
    private int bOffset;
    private StripeThread nextThreadInCycle;
    private final int iterations;
    private final Result result;
    private final StripeSync sync;

    public StripeThread(double[][] aRowGroup, int aOffset, double[][] bColumnGroup, int bOffset,
                        int iterations,
                        Result result,
                        StripeSync sync) {
        this.aRowGroup = aRowGroup;
        this.aOffset = aOffset;
        this.bColumnGroup = bColumnGroup;
        this.bOffset = bOffset;
        this.iterations = iterations;
        this.result = result;
        this.sync = sync;
    }

    public void setNextThreadInCycle(StripeThread nextThreadInCycle) {
        this.nextThreadInCycle = nextThreadInCycle;
    }

    public double[][] getBColumnGroup() {
        return bColumnGroup;
    }

    public int getBOffset() {
        return bOffset;
    }

    public void setBColumnData(double[][] bColumnGroup, int bOffset) {
        this.bColumnGroup = bColumnGroup;
        this.bOffset = bOffset;
    }

    public void sendDataInCycle() {
        this.nextThreadInCycle.setBColumnData(this.bColumnGroup, this.bOffset);
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            this.calculateSubtaskIteration();
            sync.waitForNextIteration(i);
        }
    }

    private void calculateSubtaskIteration() {
        for (int i = 0; i < aRowGroup.length; i++) {
            for (int j = 0; j < bColumnGroup.length; j++) {
                for (int k = 0; k < bColumnGroup[0].length; k++) {
                    this.result.data[i + aOffset][j + bOffset] += aRowGroup[i][k] * bColumnGroup[j][k];
                }
            }
        }
    }
}
