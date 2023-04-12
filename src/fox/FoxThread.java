package fox;

public class FoxThread extends Thread {
    private final int resRow;
    private final int resCol;
    private double[][] aBlock;
    private double[][] bBlock;
    private final double[][] result;
    private final int iterations;

    public FoxThread(int resRow, int resCol, double[][] aBlock, double[][] bBlock, int iterations) {
        this.resRow = resRow;
        this.resCol = resCol;
        this.aBlock = aBlock;
        this.bBlock = bBlock;
        this.result = new double[aBlock.length][aBlock.length];
        this.iterations = iterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            calculateSubtaskIteration();
        }
    }

    private void calculateSubtaskIteration() {
        for (int i = 0; i < aBlock.length; i++) {
            for (int j = 0; j < bBlock[0].length; j++) {
                for (int k = 0; k < bBlock.length; k++) {
                    result[i][j] += aBlock[i][k] * bBlock[k][j];
                }
            }
        }
    }

    public double[][] getResult() {
        return result;
    }

    public int getResRow() {
        return resRow;
    }

    public int getResCol() {
        return resCol;
    }
}
