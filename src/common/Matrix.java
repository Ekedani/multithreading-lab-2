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

    protected void transpose() {
        double[][] newData = new double[numCols][numRows];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                newData[j][i] = data[i][j];
            }
        }
        int temp = numRows;
        numRows = numCols;
        numCols = temp;
        data = newData;
    }

    protected void generateRandomMatrix(double minVal, double maxVal) {
        Random random = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                data[i][j] = minVal + (maxVal - minVal) * random.nextDouble();
            }
        }
    }

    protected void generateIdentityMatrix() {
        if (numRows != numCols) {
            throw new IllegalArgumentException("Matrix must be square to generate identity matrix.");
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                data[i][j] = i == j ? 1.0 : 0.0;
            }
        }
    }
}
