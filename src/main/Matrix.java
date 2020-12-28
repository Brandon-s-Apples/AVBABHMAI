package main;

import java.util.Random;

public class Matrix {

    private static final Random random = new Random();

    double[][] data;
    public final int width, height;

    public Matrix(int width, int height) {
        this.width = width;
        this.height = height;

        data = new double[width][height];
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                data[x][y] = 0;
    }

    public Matrix(Matrix matrix) {
        this.width = matrix.width;
        this.height = matrix.height;

        data = new double[width][height];
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                data[x][y] = matrix.get(x, y);
    }

    public double get(int x, int y) {
        return data[x][y];
    }

    public void set(int x, int y, double val) {
        data[x][y] = val;
    }

    public void add(int x, int y, double val) {
        data[x][y] += val;
    }

    public static Matrix add(Matrix m1, Matrix m2) {
        if(m1.width != m2.width || m1.height != m2.height)
            throw new RuntimeException("Matrix sizes do not match");
        Matrix retVal = new Matrix(m1);
        for(int x = 0; x < retVal.width; x++)
            for(int y = 0; y < retVal.height; y++)
                retVal.add(x, y, m2.get(x, y));
        return retVal;
    }

    public static Matrix matrixProduct(Matrix m1, Matrix m2) {
        if(m2.width == 1 && m1.height == m2.height) {}
        else throw RuntimeException("Matrices not compatible");
    }

}
