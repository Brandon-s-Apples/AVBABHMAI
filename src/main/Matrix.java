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

    public Matrix(double[] array) {
        width = 1;
        height = array.length;

        data = new double[width][height];
        for(int y = 0; y < height; y++)
            data[0][y] = array[y];
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

    public void sub(int x, int y, double val) {
        data[x][y] -= val;
    }

    public void mult(int x, int y, double val) {
        data[x][y] *= val;
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

    public Matrix add(Matrix m) {
        if(width != m.width || height != m.height)
            throw new RuntimeException("Matrix sizes do not match");
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                add(x, y, m.get(x, y));
        return this;
    }

    public static Matrix sub(Matrix m1, Matrix m2) {
        if(m1.width != m2.width || m1.height != m2.height)
            throw new RuntimeException("Matrix sizes do not match");
        Matrix retVal = new Matrix(m1);
        for(int x = 0; x < retVal.width; x++)
            for(int y = 0; y < retVal.height; y++)
                retVal.sub(x, y, m2.get(x, y));
        return retVal;
    }

    public static Matrix mult(Matrix m1, Matrix m2) {
        if(m1.width != m2.width || m1.height != m2.height)
            throw new RuntimeException("Matrix sizes do not match");
        Matrix retVal = new Matrix(m1);
        for(int x = 0; x < retVal.width; x++)
            for(int y = 0; y < retVal.height; y++)
                retVal.mult(x, y, m2.get(x, y));
        return retVal;
    }

    public static Matrix mult(Matrix m1, double scalar) {
        Matrix retVal = new Matrix(m1);
        for(int x = 0; x < retVal.width; x++)
            for(int y = 0; y < retVal.height; y++)
                retVal.mult(x, y, scalar);
        return retVal;
    }

    public static Matrix matrixMult(Matrix m1, Matrix m2) {
        if(m1.width == m2.height) {
            Matrix retVal = new Matrix(m2.width, m1.height);
            for(int x = 0; x < retVal.width; x++)
                for(int y = 0; y < retVal.height; y++) {
                    double sum = 0;
                    for(int index = 0; index < m1.width; index++) {
                        sum += m1.get(index, y) * m2.get(x, index);
                    }
                    retVal.set(x, y, sum);
                }
            return retVal;
        } else throw new RuntimeException("Matrices not compatible");
    }

    public void randomize() {
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                set(x, y, (random.nextDouble() * 2) - 1);
    }

    public double[] toArray() {
        if(width > 1)
            throw new RuntimeException("Matrix cannot be converted");
        else {
            double[] retVal = new double[height];
            for(int i = 0; i < height; i++)
                retVal[i] = get(0, i);
            return retVal;
        }
    }

    public void sigmoid() {
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                set(x, y, sigmoid(get(x, y)));
    }

    public static double sigmoid(double val) {
        return 1 / (1 + Math.pow(Math.E, -val));
    }

    public void notReallyDSigmoid() {
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                set(x, y, notReallyDSigmoid(get(x, y)));
    }

    public static double notReallyDSigmoid(double alreadySigmoidedVal) {
        return alreadySigmoidedVal * (1 - alreadySigmoidedVal);
    }

    public void print() {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                System.out.print(data[x][y] + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static Matrix transpose(Matrix m) {
        Matrix retVal = new Matrix(m.height, m.width);
        for(int x = 0; x < m.width; x++)
            for(int y = 0; y < m.height; y++)
                retVal.set(y, x, m.get(x, y));
        return retVal;
    }

    public void printSize() {
        System.out.println(width + ", " + height);
    }

    public static Matrix removeLastRow(Matrix m) {
        Matrix retVal = new Matrix(m.width, m.height - 1);
        for(int x = 0; x < retVal.width; x++)
            for(int y = 0; y < retVal.height; y++)
                retVal.set(x, y, m.get(x, y));
            return retVal;
    }

}
