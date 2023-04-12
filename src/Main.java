import common.Matrix;
import common.Result;
import fox.FoxMatrixMultiplicator;
import stripe.StripeMatrixMultiplicator;

public class Main {
    public static void main(String[] args) {
        final int matrixSize = 100;
        Matrix A = new Matrix(matrixSize);
        Matrix B = new Matrix(matrixSize);
        A.generateRandomMatrix(1, 50);
        B.generateRandomMatrix(1, 50);

        A.print();
        B.print();

        StripeMatrixMultiplicator stripeMatrixMultiplicator = new StripeMatrixMultiplicator(2);
        FoxMatrixMultiplicator foxMatrixMultiplicator = new FoxMatrixMultiplicator(2);

        Matrix cFox = foxMatrixMultiplicator.multiply(A, B);
        cFox.print();
        Matrix cNaive = A.multiply(B);
        System.out.println(" ");
        cNaive.print();
        Result cStripe = stripeMatrixMultiplicator.multiply(A, B);
        System.out.println("Stripe is correct: " + cNaive.equals(cStripe));
        System.out.println("Fox is correct: " + cNaive.equals(cFox));
    }
}