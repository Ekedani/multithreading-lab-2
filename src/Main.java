import common.Matrix;
import common.Result;
import fox.FoxMatrixMultiplicator;
import stripe.StripeMatrixMultiplicator;

public class Main {
    public static void main(String[] args) {
        final int defaultMatrixSize = 1000;
        final int defaultFoxBlocks = 2;

        testIfParallelAlgorithmsAreCorrect(defaultMatrixSize);

        final int[] testMatrixSizesData = {500, 750, 1000, 1250, 1500, 2000};
        testMatrixSizes(testMatrixSizesData, defaultFoxBlocks);

        final int[] testThreadNumsData = {2, 4, 6, 8, 9, 12, 15, 16, 25};
        testThreadsNums(defaultMatrixSize, testThreadNumsData);
    }

    static void testIfParallelAlgorithmsAreCorrect(int size) {
        final Matrix A = new Matrix(size);
        final Matrix B = new Matrix(size);

        StripeMatrixMultiplicator stripeMatrixMultiplicator = new StripeMatrixMultiplicator(4);
        FoxMatrixMultiplicator foxMatrixMultiplicator = new FoxMatrixMultiplicator(2);

        // We consider the sequential algorithm to be always correct
        Matrix cNaive = A.multiply(B);
        Result cStripe = stripeMatrixMultiplicator.multiply(A, B);
        System.out.println("Stripe is correct: " + cNaive.equals(cStripe));
        Matrix cFox = foxMatrixMultiplicator.multiply(A, B);
        System.out.println("Fox is correct: " + cNaive.equals(cFox));
    }

    static void testMatrixSizes(int[] sizes, int foxBlocksNum) {
        long[] naiveMetrics = new long[sizes.length];
        long[] stripeMetrics = new long[sizes.length];
        long[] foxMetrics = new long[sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            Matrix A = new Matrix(sizes[i]);
            Matrix B = new Matrix(sizes[i]);
            A.generateRandomMatrix(1, 100);
            B.generateRandomMatrix(1, 100);

            long start = System.nanoTime();
            A.multiply(B);
            long finish = System.nanoTime();
            naiveMetrics[i] = finish - start;

            StripeMatrixMultiplicator stripeMatrixMultiplicator = new StripeMatrixMultiplicator(foxBlocksNum * foxBlocksNum);
            start = System.nanoTime();
            stripeMatrixMultiplicator.multiply(A, B);
            finish = System.nanoTime();
            stripeMetrics[i] = finish - start;

            FoxMatrixMultiplicator foxMatrixMultiplicator = new FoxMatrixMultiplicator(foxBlocksNum);
            start = System.nanoTime();
            foxMatrixMultiplicator.multiply(A, B);
            finish = System.nanoTime();
            foxMetrics[i] = finish - start;
        }

        System.out.println("SUBTASK 3: SIZE TESTING (Threads num is " + foxBlocksNum * foxBlocksNum + ")");
        for (int i = 0; i < sizes.length; i++) {
            System.out.println("-------- Size: " + sizes[i] + " --------");
            System.out.println("Naive: " + naiveMetrics[i] + " ns");
            System.out.println("Stripe: " + stripeMetrics[i] + " ns, Speedup: " + naiveMetrics[i] * 1.0 / stripeMetrics[i]);
            System.out.println("Fox: " + foxMetrics[i] + " ns, Speedup: " + naiveMetrics[i] * 1.0 / foxMetrics[i]);
        }
    }

    static void testThreadsNums(int size, int[] threadsNums) {
        long[] naiveMetrics = new long[threadsNums.length];
        long[] stripeMetrics = new long[threadsNums.length];
        long[] foxMetrics = new long[threadsNums.length];

        final Matrix A = new Matrix(size);
        final Matrix B = new Matrix(size);
        A.generateRandomMatrix(1, 100);
        B.generateRandomMatrix(1, 100);

        for (int i = 0; i < threadsNums.length; i++) {
            long start = System.nanoTime();
            A.multiply(B);
            long finish = System.nanoTime();
            naiveMetrics[i] = finish - start;

            StripeMatrixMultiplicator stripeMatrixMultiplicator = new StripeMatrixMultiplicator(threadsNums[i]);
            start = System.nanoTime();
            stripeMatrixMultiplicator.multiply(A, B);
            finish = System.nanoTime();
            stripeMetrics[i] = finish - start;

            int x = (int) Math.sqrt(threadsNums[i]);
            if (Math.pow(x, 2) == threadsNums[i]) {
                FoxMatrixMultiplicator foxMatrixMultiplicator = new FoxMatrixMultiplicator((int) Math.sqrt(threadsNums[i]));
                start = System.nanoTime();
                foxMatrixMultiplicator.multiply(A, B);
                finish = System.nanoTime();
                foxMetrics[i] = finish - start;
            }
        }

        System.out.println("SUBTASK 4: THREAD NUM TESTING (Matrix size is " + size + ")");
        for (int i = 0; i < threadsNums.length; i++) {
            System.out.println("-------- Threads num: " + threadsNums[i] + " --------");
            System.out.println("Naive: " + naiveMetrics[i] + " ns");
            System.out.println("Stripe: " + stripeMetrics[i] + " ns, Speedup: " + naiveMetrics[i] * 1.0 / stripeMetrics[i]);
            if (foxMetrics[i] == 0) {
                System.out.println("Fox: the number of threads is not square");
            } else {
                System.out.println("Fox: " + foxMetrics[i] + " ns, Speedup: " + naiveMetrics[i] * 1.0 / foxMetrics[i]);
            }
        }
    }
}