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

    public static void main(String[] args) {
        AVBABHMAI network = new AVBABHMAI(2, 5, 1);
        double[] case1 = {1, 1};
        double[] case1output = {0};
        double[] case2 = {1, 0};
        double[] case2output = {1};
        double[] case3 = {0, 1};
        double[] case3output = {1};
        double[] case4 = {0, 0};
        double[] case4output = {0};

        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        JLabel c1 = new JLabel();
        c1.setBounds(0, 100, 600, 100);
        JLabel c2 = new JLabel();
        c2.setBounds(0, 200, 600, 100);
        JLabel c3 = new JLabel();
        c3.setBounds(0, 300, 600, 100);
        JLabel c4 = new JLabel();
        c4.setBounds(0, 400, 600, 100);
        frame.add(c1);
        frame.add(c2);
        frame.add(c3);
        frame.add(c4);

        frame.setVisible(true);

        while(true) {
            network.test(case1, case1output);
            network.test(case2, case2output);
            network.test(case3, case3output);
            network.test(case4, case4output);
            for(double x : network.feedForward(case1))
                c1.setText(x + "");
            for(double x : network.feedForward(case2))
                c2.setText(x + "");
            for(double x : network.feedForward(case3))
                c3.setText(x + "");
            for(double x : network.feedForward(case4))
                c4.setText(x + "");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
        }
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
        Matrix trueInput = new Matrix(input);
        Matrix trueOutput = new Matrix(target);
        //

        // Adjust for output weights
        Matrix error = Matrix.sub(trueOutput, outputGuess);
        Matrix outputChange = new Matrix(outputGuess);
        outputChange.notReallyDSigmoid();
        outputChange = Matrix.mult(outputChange, error);
        outputChange = Matrix.mult(outputChange, learningRate);
        Matrix t_hiddenGuess = Matrix.transpose(new Matrix(addBiasToArray(hiddenGuess.toArray())));
        Matrix outputDeltas = Matrix.matrixMult(outputChange, t_hiddenGuess);
        outputWeight = Matrix.add(outputWeight, outputDeltas);
        //

        // Adjust for hidden weights
        Matrix t_outputWeight = Matrix.transpose(outputWeight);
        Matrix hiddenError = Matrix.matrixMult(t_outputWeight, error);
        Matrix hiddenChange = new Matrix(addBiasToArray(hiddenGuess.toArray()));
        hiddenChange.notReallyDSigmoid();
        hiddenChange = Matrix.mult(hiddenChange, hiddenError);
        hiddenChange = Matrix.mult(hiddenChange, learningRate);
        Matrix t_input = Matrix.transpose(new Matrix(addBiasToArray(trueInput.toArray())));
        Matrix hiddenDeltas = Matrix.matrixMult(hiddenChange, t_input);
        hiddenDeltas = Matrix.removeLastRow(hiddenDeltas);
        hiddenWeight = Matrix.add(hiddenWeight, hiddenDeltas);
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
