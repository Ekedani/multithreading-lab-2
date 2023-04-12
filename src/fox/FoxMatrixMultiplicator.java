package fox;

import common.Matrix;
import common.MatrixMultiplicator;
import common.Result;

public class FoxMatrixMultiplicator implements MatrixMultiplicator {
    private final int threadsNum;
    private Result result = null;

    public FoxMatrixMultiplicator(int threadsNum) {
        this.threadsNum = threadsNum;
    }

    @Override
    public Result multiply(Matrix A, Matrix B) {
        this.result = new Result(A.getNumRows(), B.getNumCols());
        FoxThread[] threads = createThreads(A, B);

        JoinResults(threads);
        return result;
    }

    private FoxThread[] createThreads(Matrix A, Matrix B) {
        FoxThread[] threads = new FoxThread[threadsNum];

        return threads;
    }

    private void JoinResults(FoxThread[] threads) {

    }
}
