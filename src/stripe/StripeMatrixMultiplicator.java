package stripe;

import common.Matrix;
import common.MatrixMultiplicator;
import common.Result;

public class StripeMatrixMultiplicator implements MatrixMultiplicator {
    private final int threadsNum;
    private Result result = null;

    public StripeMatrixMultiplicator(int threadsNum) {
        this.threadsNum = threadsNum;
    }

    @Override
    public Result multiply(Matrix A, Matrix B) {
        this.result = new Result(A.getNumRows(), B.getNumCols());
        final StripeThread[] threads = this.createThreads(A, B);
        for (StripeThread thread : threads) {
            thread.start();
        }
        for (StripeThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return this.result;
    }

    private StripeThread[] createThreads(Matrix A, Matrix B) {
        final int groupsNum = Math.min(A.getNumRows(), this.threadsNum);
        final int rowGroupSize = A.getNumRows() / groupsNum;
        final int colGroupSize = B.getNumCols() / groupsNum;

        final Matrix transposedB = B.transpose();
        final StripeThread[] threads = new StripeThread[groupsNum];
        final StripeSync sync = new StripeSync(threads);

        for (int i = 0; i < groupsNum; i++) {
            double[][] aRowGroup = A.getRows(i * rowGroupSize, rowGroupSize);
            double[][] bColGroup = transposedB.getRows(i * colGroupSize, colGroupSize);
            StripeThread stripeThread = new StripeThread(aRowGroup, rowGroupSize, bColGroup, colGroupSize,
                    groupsNum, this.result, sync);
            threads[i] = stripeThread;
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].setNextThreadInCycle(threads[(i + 1) % threads.length]);
        }
        return threads;
    }
}
