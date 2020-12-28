package main;

public class AVBABHMAI {

    // A
    // Very
    // Bad
    // Attempt by
    // Brandon
    // Han to
    // Make an
    // Artificial
    // Intelligence

    public static void main(String[] args) {
        AVBABHMAI network = new AVBABHMAI(3, 2, 5);
        double[] input = {1, 2, 3};
        double[] output = {0.1, 0.2, 0.3, 0.4, 0.5};
        network.test(input, output);
    }

    final int inputNum, hiddenNum, outputNum;

    Matrix hiddenWeight;
    Matrix outputWeight;

    public AVBABHMAI(int numOfInput, int numOfHidden, int numOfOutput) {
        inputNum = numOfInput;
        hiddenNum = numOfHidden;
        outputNum = numOfOutput;

        hiddenWeight = new Matrix(inputNum + 1, hiddenNum);
        outputWeight = new Matrix(hiddenNum + 1, outputNum);

        hiddenWeight.randomize();
        outputWeight.randomize();
    }

    public double[] feedForward(double[] input) {
        if(input.length != inputNum)
            throw new RuntimeException("Incorrect number of inputs");

        Matrix o1 = Matrix.matrixMult(hiddenWeight, new Matrix(addBiasToArray(input)));
        o1.sigmoid();

        Matrix output = Matrix.matrixMult(outputWeight, new Matrix(addBiasToArray(o1.toArray())));
        output.sigmoid();

        return output.toArray();
    }

    public void test(double[] input, double[] target) {

        for(double x : feedForward(new double[] {5, 5, 5}))
            System.out.println(x);
        System.out.println();

        Matrix in = new Matrix(input);
        Matrix tar = new Matrix(target);
        Matrix guess = new Matrix(feedForward(input));

        Matrix error = Matrix.sub(tar, guess);

        tar.print();
        error.print();
    }

    public double[] addBiasToArray(double[] array) {
        double[] retVal = new double[array.length + 1];
        System.arraycopy(array, 0, retVal, 0, array.length);
        retVal[array.length] = 1;
        return retVal;
    }

}
