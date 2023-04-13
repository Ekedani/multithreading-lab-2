import common.Matrix;
import common.Result;
import fox.FoxMatrixMultiplicator;
import stripe.StripeMatrixMultiplicator;

public class Main {
    public static void main(String[] args) {
        final int matrixSize = 4;
        Matrix A = new Matrix(matrixSize);
        Matrix B = new Matrix(matrixSize);
        A.generateRandomMatrix(1, 50);
        B.generateRandomMatrix(1, 50);

        B.print();

        StripeMatrixMultiplicator stripeMatrixMultiplicator = new StripeMatrixMultiplicator(3);
        FoxMatrixMultiplicator foxMatrixMultiplicator = new FoxMatrixMultiplicator(2);

        // Matrix cFox = foxMatrixMultiplicator.multiply(A, B);
        // System.out.println("Fox is correct: " + cNaive.equals(cFox));
        Matrix cNaive = A.multiply(B);
        cNaive.print();
        Result cStripe = stripeMatrixMultiplicator.multiply(A, B);
        cStripe.print();
        System.out.println("Stripe is correct: " + cNaive.equals(cStripe));

    }
}