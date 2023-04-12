package fox;

import common.Matrix;
import common.MatrixMultiplicator;
import common.Result;

public class FoxMatrixMultiplicator implements MatrixMultiplicator {
    private final int blocksNumSqrt;
    private Result result = null;

    public FoxMatrixMultiplicator(int blocksNum) {
        this.blocksNumSqrt = blocksNum;
    }

    @Override
    public Result multiply(Matrix A, Matrix B) {
        FoxThread[][] threads = createThreads(A, B);
        for (FoxThread[] thread : threads) {
            for (int j = 0; j < threads[0].length; j++) {
                thread[j].start();
            }
        }
        for (FoxThread[] thread : threads) {
            for (int j = 0; j < threads[0].length; j++) {
                try {
                    thread[j].join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        double[][][][] resultBlocks = new double[blocksNumSqrt][blocksNumSqrt][][];
        for (int i = 0; i < threads.length; i++) {
            for (int j = 0; j < threads[0].length; j++) {
                resultBlocks[i][j] = threads[i][j].getResult();
            }
        }
        this.result = Result.joinFoxBlockSplit(A.getNumRows(), B.getNumCols(), resultBlocks);
        return result;
    }

    private FoxThread[][] createThreads(Matrix A, Matrix B) {
        double[][][][] aBlocks = A.getFoxBlockSplit(blocksNumSqrt);
        double[][][][] bBlocks = B.getFoxBlockSplit(blocksNumSqrt);

        FoxThread[][] threads = new FoxThread[blocksNumSqrt][blocksNumSqrt];
        FoxSync sync = new FoxSync(aBlocks, bBlocks, threads);

        for (int i = 0; i < blocksNumSqrt; i++) {
            for (int j = 0; j < blocksNumSqrt; j++) {
                threads[i][j] = new FoxThread(aBlocks[i][i], bBlocks[i][j], blocksNumSqrt, sync);
            }
        }
        return threads;
    }
}
