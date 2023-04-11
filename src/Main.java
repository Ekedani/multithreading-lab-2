import common.Matrix;
import common.Result;
import stripe.StripeMatrixMultiplicator;

public class Main {
    public static void main(String[] args) {
        final int matrixSize = 500;
        Matrix A = new Matrix(matrixSize, matrixSize);
        Matrix B = new Matrix(matrixSize, matrixSize);
        A.generateRandomMatrix(1, 4);
        B.generateRandomMatrix(1, 4);

        StripeMatrixMultiplicator stripeMatrixMultiplicator = new StripeMatrixMultiplicator(4);
        Result C = stripeMatrixMultiplicator.multiply(A, B);
        C.print();
    }
}