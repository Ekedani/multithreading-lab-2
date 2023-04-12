package fox;

public class FoxThread extends Thread {
    private double[][] aBlock;
    private double[][] bBlock;
    private final double[][] result;
    private final int iterations;
    private final FoxSync sync;

    public FoxThread(double[][] aBlock, double[][] bBlock, int iterations, FoxSync sync) {
        this.aBlock = aBlock;
        this.bBlock = bBlock;
        this.result = new double[aBlock.length][aBlock.length];
        this.iterations = iterations;
        this.sync = sync;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            calculateSubtaskIteration();
            sync.waitForNextIteration(i);
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

    public void setABlock(double[][] aBlock) {
        this.aBlock = aBlock;
    }

    public void setBBlock(double[][] bBlock) {
        this.bBlock = bBlock;
    }

    public void printABlock() {
        for (int i = 0; i < aBlock.length; i++) {
            for (int j = 0; j < aBlock[0].length; j++) {
                System.out.print(aBlock[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public void printBBlock() {
        for (int i = 0; i < bBlock.length; i++) {
            for (int j = 0; j < bBlock[0].length; j++) {
                System.out.print(bBlock[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
