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
        Matrix[] inputs = new Matrix[weightList.length + 1];
        inputs[0] = new Matrix(input);
        Matrix prevOutput = new Matrix(addBiasToArray(input));
        for(int index = 1; index < inputs.length; index++) {
            inputs[index] = Matrix.matrixMult(weightList[index + 1], prevOutput);
            inputs[index].sigmoid();
            prevOutput = new Matrix(addBiasToArray(inputs[index].toArray()));
        }

        // Convert function inputs
        Matrix givenOutput = new Matrix(target);

        // Calculate transposed matrices
        Matrix[] t_weightList = new Matrix[weightList.length];
        for(int index = 0; index < weightList.length; index++)
            t_weightList[index] = Matrix.transpose(weightList[index]);
        Matrix[] t_guess = new Matrix[inputs.length];
        for(int index = 0; index < inputs.length; index++)
            t_guess[index] = Matrix.transpose(new Matrix(addBiasToArray(inputs[index].toArray())));

        Matrix t_input = Matrix.transpose(new Matrix(addBiasToArray(givenInput.toArray())));

        Matrix error = Matrix.sub(givenOutput, inputs[1]);
        Matrix nextInput = givenInput;

        for(int index = weightList.length - 1; index >= 0; index++) {
            Matrix currentError = error;
            Matrix currentWeight = weightList[index];
            Matrix currentInput = nextInput;

            Matrix change = new Matrix(inputs[1]);
            change.notReallyDSigmoid();
            change = Matrix.mult(change, error);
            change = Matrix.mult(change, learningRate);
            Matrix outputDeltas = Matrix.matrixMult(change, t_guess[0]);
            weightList[1] = Matrix.add(weightList[1], outputDeltas);
        }

        // Adjust for output weights
        Matrix outputChange = new Matrix(inputs[1]);
        outputChange.notReallyDSigmoid();
        outputChange = Matrix.mult(outputChange, error);
        outputChange = Matrix.mult(outputChange, learningRate);
        Matrix outputDeltas = Matrix.matrixMult(outputChange, t_guess[0]);
        weightList[1] = Matrix.add(weightList[1], outputDeltas);
        //

        // Adjust for hidden weights
        error = Matrix.matrixMult(t_weightList[1], error);
        Matrix hiddenChange = new Matrix(addBiasToArray(inputs[0].toArray()));
        hiddenChange.notReallyDSigmoid();
        hiddenChange = Matrix.mult(hiddenChange, error);
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
