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

    }

    final int inputNum, hiddenNum, outputNum;

    Matrix hiddenWeight;
    Matrix outputWeight;

    public AVBABHMAI(int numOfInput, int numOfHidden, int numOfOutput) {
        inputNum = numOfInput;
        hiddenNum = numOfHidden;
        outputNum = numOfOutput;

        hiddenWeight = new Matrix(hiddenNum, inputNum);
        outputWeight = new Matrix(outputNum, hiddenNum);

    }

}
