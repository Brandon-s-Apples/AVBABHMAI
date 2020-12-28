package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        AVBABHMAI network = new AVBABHMAI(2, 300, 1);
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
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
                c1.setText("c1: " + x);
            for(double x : network.feedForward(case2))
                c2.setText("c2: " + x);
            for(double x : network.feedForward(case3))
                c3.setText("c3: " + x);
            for(double x : network.feedForward(case4))
                c4.setText("c4: " + x);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {}
        }
    }

}
