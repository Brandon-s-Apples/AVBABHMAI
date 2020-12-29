package main;

public class AVBABHMAI_V2 {

    public final int[] neuronNum;
    Matrix[] weightList;

    private double learningRate = 0.1;

    public AVBABHMAI_V2(int numOfInput, int[] numsOfHidden, int numOfOutput) {
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
