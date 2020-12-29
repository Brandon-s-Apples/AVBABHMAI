package main;

import java.util.Random;

public class AVBABHMAI {

    public final int[] neuronNum;
    Matrix[] weightList;

    private double learningRate = 0.1;

    public AVBABHMAI(int numOfInput, int[] numsOfHidden, int numOfOutput) {
        neuronNum = new int[numsOfHidden.length + 2];
        neuronNum[0] = numOfInput;
        for(int i = 1; i < neuronNum.length - 1; i++)
            neuronNum[i] = numsOfHidden[i - 1];
        neuronNum[neuronNum.length - 1] = numOfOutput;

        weightList = new Matrix[neuronNum.length - 1];
        for(int i = 0; i < weightList.length; i++) {
            weightList[i] = new Matrix(neuronNum[i] + 1, neuronNum[i + 1]);
            weightList[i].randomize();
        }
    }

    public double[] feedForward(double[] input) {
        if(input.length != neuronNum[0])
            throw new RuntimeException("Incorrect number of inputs");

        Matrix[] guesses = new Matrix[neuronNum.length];
        guesses[0] = new Matrix(input);
        for(int i = 1; i < guesses.length; i++) {
            guesses[i] = Matrix.matrixMult(weightList[i - 1], addBias(guesses[i - 1]));
            guesses[i].sigmoid();
        }
        return guesses[guesses.length - 1].toArray();
    }

    public double[] train(double[] input, double[] answer) {
        if(input.length != neuronNum[0])
            throw new RuntimeException("Incorrect number of inputs");
        if(answer.length != neuronNum[neuronNum.length - 1])
            throw new RuntimeException("Incorrect number of outputs");

        Matrix[] guesses = new Matrix[neuronNum.length];
        guesses[0] = new Matrix(input);
        for(int i = 1; i < guesses.length; i++) {
            guesses[i] = Matrix.matrixMult(weightList[i - 1], addBias(guesses[i - 1]));
            guesses[i].sigmoid();
        }

        Matrix error = Matrix.sub(new Matrix(answer), guesses[guesses.length - 1]);
        for(int i = weightList.length - 1; i >= 0; i--) {
            Matrix derivative = new Matrix(guesses[i + 1]);
            derivative.notReallyDSigmoid();
            Matrix t_input = Matrix.transpose(addBias(guesses[i]));
            Matrix delta = Matrix.matrixMult(Matrix.mult(Matrix.mult(error, derivative), learningRate), t_input);

            error = Matrix.removeLastRow(Matrix.matrixMult(Matrix.transpose(weightList[i]), error));

            weightList[i].add(delta);
        }

        return new double[1];
    }

    public void printWeightList() {
        for(Matrix m : weightList)
            m.print();
    }

    public void printWeightListSize() {
        for(Matrix m : weightList)
            m.printSize();
    }

    public void printSize() {
        System.out.print(" | ");
        for(int i : neuronNum)
            System.out.print(i + " | ");
        System.out.println();
    }

    public void addBias(double[] array) {
        double[] retVal = new double[array.length + 1];
        for(int i = 0; i < array.length; i++)
            retVal[i] = array[i];
        retVal[retVal.length - 1] = 1;
    }

    public Matrix addBias(Matrix m) {
        Matrix retVal = new Matrix(m.width, m.height + 1);
        for(int x = 0; x < m.width; x++)
            for(int y = 0; y < m.height; y++)
                retVal.set(x, y, m.get(x, y));
            for(int x = 0; x < m.width; x++)
                retVal.set(x, retVal.height - 1, 1);
            return retVal;
    }

}

class Matrix {

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