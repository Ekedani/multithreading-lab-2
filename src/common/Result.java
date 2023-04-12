package common;

public class Result extends Matrix {
    public Result(int numRows, int numCols) {
        super(numRows, numCols);
    }

    static public Result joinFoxBlockSplit(int numRows, int numCols, double[][][][] cBlocks) {
        Result result = new Result(numRows, numCols);
        for (int i = 0; i < cBlocks.length; i++) {
            for (int j = 0; j < cBlocks.length; j++) {
                double[][] subMatrix = cBlocks[i][j];
                int subMatrixStartRow = i * cBlocks[0][0].length;
                int subMatrixStartCol = j * cBlocks[0][0].length;
                for (int k = 0; k < cBlocks[0][0].length; k++) {
                    System.arraycopy(subMatrix[k], 0, result.data[subMatrixStartRow + k], subMatrixStartCol, cBlocks[0][0].length);
                }
            }
        }
        return result;
    }
}
