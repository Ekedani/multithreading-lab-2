package common;

import java.util.Random;

public class Matrix {
    private int numRows;
    private int numCols;
    public double[][] data;

    public Matrix(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.data = new double[numRows][numCols];
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }

    public Matrix transpose() {
        final Matrix transposed = new Matrix(numCols, numRows);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                transposed.data[j][i] = data[i][j];
            }
        }
        return transposed;
    }

    public void generateRandomMatrix(double minVal, double maxVal) {
        Random random = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                data[i][j] = minVal + (maxVal - minVal) * random.nextDouble();
            }
        }
    }

    public void generateIdentityMatrix() {
        if (numRows != numCols) {
            throw new IllegalArgumentException("Matrix must be square to generate identity matrix.");
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                data[i][j] = i == j ? 1.0 : 0.0;
            }
        }
    }

    public double[][] getRows(int offset, int numRows) {
        final double[][] result = new double[numRows][this.numCols];
        System.arraycopy(this.data, offset, result, 0, numRows);
        return result;
    }
}
