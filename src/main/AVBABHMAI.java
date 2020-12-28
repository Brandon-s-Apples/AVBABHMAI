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
        AVBABHMAI network = new AVBABHMAI(3, 15, 5);
        double[] input = {1, 2, 3};
        double[] output = {0.1, 0.2, 0.3, 0.4, 0.5};
        network.test(input, output);
    }

    final int inputNum, hiddenNum, outputNum;

    double learningRate = 0.1;

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
        if(input.length != inputNum)
            throw new RuntimeException("Incorrect number of inputs");
        if(target.length != outputNum)
            throw new RuntimeException("Incorrect number of output");

        /*for(double x : feedForward(new double[] {5, 5, 5}))
            System.out.println(x);
        System.out.println();*/

        // Calculate guess - feedForward function
        Matrix hiddenGuess = Matrix.matrixMult(hiddenWeight, new Matrix(addBiasToArray(input)));
        hiddenGuess.sigmoid();
        Matrix outputGuess = Matrix.matrixMult(outputWeight, new Matrix(addBiasToArray(hiddenGuess.toArray())));
        outputGuess.sigmoid();
        //

        // Convert funtion inputs
        Matrix trueOutput = new Matrix(target);
        //

        Matrix error = Matrix.sub(trueOutput, outputGuess);

        /*Matrix t_outputWeight = Matrix.transpose(outputWeight);
        Matrix hiddenError = Matrix.matrixMult(t_outputWeight, error);*/

        /*Matrix t_hiddenWeight = Matrix.transpose(hiddenWeight);
        Matrix inputError = Matrix.matrixMult(t_hiddenWeight, new Matrix(removeBiasFromArray(hiddenError.toArray())));*/

        Matrix outputChange = new Matrix(outputGuess);
        outputChange.notReallyDSigmoid();
        outputChange = Matrix.mult(outputChange, error);
        outputChange = Matrix.mult(outputChange, learningRate);

        Matrix t_hiddenGuess = Matrix.transpose(new Matrix(addBiasToArray(hiddenGuess.toArray())));
        Matrix outputDeltas = Matrix.matrixMult(outputChange, t_hiddenGuess);
        outputWeight = Matrix.add(outputWeight, outputDeltas);

        /*Matrix hiddenChange = new Matrix(hiddenGuess);
        hiddenChange.notReallyDSigmoid();
        hiddenChange = Matrix.mult(hiddenChange, hiddenError);
        hiddenChange = Matrix.mult(outputChange, learningRate);

        Matrix t_input = Matrix.transpose(trueInput);
        Matrix hiddenDeltas = Matrix.matrixMult(hiddenChange, t_input);
        hiddenWeight = Matrix.add(hiddenWeight, hiddenDeltas);*/
    }

    public double[] addBiasToArray(double[] array) {
        double[] retVal = new double[array.length + 1];
        System.arraycopy(array, 0, retVal, 0, array.length);
        retVal[array.length] = 1;
        return retVal;
    }

    public double[] removeBiasFromArray(double[] array) {
        double[] retVal = new double[array.length - 1];
        for(int i = 0; i < retVal.length; i++)
            retVal[i] = array[i];
        return retVal;
    }

}
