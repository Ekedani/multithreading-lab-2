import common.Matrix;
import common.Result;
import fox.FoxMatrixMultiplicator;
import stripe.StripeMatrixMultiplicator;

public class Main {
    public static void main(String[] args) {
        final int matrixSize = 100;
        Matrix A = new Matrix(200, matrixSize);
        Matrix B = new Matrix(matrixSize, 200);
        A.generateRandomMatrix(1, 50);
        B.generateRandomMatrix(1, 50);

        StripeMatrixMultiplicator stripeMatrixMultiplicator = new StripeMatrixMultiplicator(1);
        FoxMatrixMultiplicator foxMatrixMultiplicator = new FoxMatrixMultiplicator(5);

        Matrix cNaive = A.multiply(B);

        Result cStripe = stripeMatrixMultiplicator.multiply(A, B);
        System.out.println("Stripe is correct: " + cNaive.equals(cStripe));

        Matrix cFox = foxMatrixMultiplicator.multiply(A, B);
        System.out.println("Fox is correct: " + cNaive.equals(cFox));
    }
}