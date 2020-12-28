package main;

import javax.swing.*;

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

    final int inputNum, outputNum;
    final int[] hiddenNum;

    Matrix[] weightList;

    double learningRate = 0.1;

    public AVBABHMAI(int numOfInput, int[] numOfHidden, int numOfOutput) {
        inputNum = numOfInput;
        hiddenNum = new int[numOfHidden.length];
        for(int index = 0; index < hiddenNum.length; index++)
            hiddenNum[index] = numOfHidden[index];
        outputNum = numOfOutput;

        weightList = new Matrix[2];
        weightList[0] = new Matrix(inputNum + 1, hiddenNum[0]);
        weightList[1] = new Matrix(hiddenNum[0] + 1, outputNum);

        weightList[0].randomize();
        weightList[1].randomize();
    }

    public double[] feedForward(double[] input) {
        if(input.length != inputNum)
            throw new RuntimeException("Incorrect number of inputs");

        Matrix[] guess = new Matrix[weightList.length];
        Matrix prevOutput = new Matrix(addBiasToArray(input));
        for(int index = 0; index < weightList.length; index++) {
            guess[index] = Matrix.matrixMult(weightList[index], prevOutput);
            guess[index].sigmoid();
            prevOutput = new Matrix(addBiasToArray(guess[index].toArray()));
        }

        return guess[guess.length - 1].toArray();
    }

    public void test(double[] input, double[] target) {
        if(input.length != inputNum)
            throw new RuntimeException("Incorrect number of inputs");
        if(target.length != outputNum)
            throw new RuntimeException("Incorrect number of output");

        // Calculate guess - feedForward function
        Matrix[] guess = new Matrix[weightList.length];
        guess[0] = Matrix.matrixMult(weightList[0], new Matrix(addBiasToArray(input)));
        guess[0].sigmoid();
        guess[1] = Matrix.matrixMult(weightList[1], new Matrix(addBiasToArray(guess[0].toArray())));
        guess[1].sigmoid();

        // Convert function inputs
        Matrix trueInput = new Matrix(input);
        Matrix trueOutput = new Matrix(target);

        // Calculate transposed matrices
        Matrix t_outputWeight = Matrix.transpose(weightList[1]);
        Matrix t_hiddenGuess = Matrix.transpose(new Matrix(addBiasToArray(guess[0].toArray())));
        Matrix t_input = Matrix.transpose(new Matrix(addBiasToArray(trueInput.toArray())));

        // Adjust for output weights
        Matrix error = Matrix.sub(trueOutput, guess[1]);
        Matrix outputChange = new Matrix(guess[1]);
        outputChange.notReallyDSigmoid();
        outputChange = Matrix.mult(outputChange, error);
        outputChange = Matrix.mult(outputChange, learningRate);
        Matrix outputDeltas = Matrix.matrixMult(outputChange, t_hiddenGuess);
        weightList[1] = Matrix.add(weightList[1], outputDeltas);
        //

        // Adjust for hidden weights
        Matrix hiddenError = Matrix.matrixMult(t_outputWeight, error);
        Matrix hiddenChange = new Matrix(addBiasToArray(guess[0].toArray()));
        hiddenChange.notReallyDSigmoid();
        hiddenChange = Matrix.mult(hiddenChange, hiddenError);
        hiddenChange = Matrix.mult(hiddenChange, learningRate);
        Matrix hiddenDeltas = Matrix.matrixMult(hiddenChange, t_input);
        hiddenDeltas = Matrix.removeLastRow(hiddenDeltas);
        weightList[0] = Matrix.add(weightList[0], hiddenDeltas);
        //

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
