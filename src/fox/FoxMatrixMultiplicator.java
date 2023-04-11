package fox;

import common.Matrix;
import common.MatrixMultiplicator;
import common.Result;

public class FoxMatrixMultiplicator implements MatrixMultiplicator {

    private final int threadsNum;

    public FoxMatrixMultiplicator(int threadsNum) {
        this.threadsNum = threadsNum;
    }

    @Override
    public Result multiply(Matrix A, Matrix B) {
        Result result = new Result(A.getNumRows(), B.getNumCols());
        return result;
    }
}
