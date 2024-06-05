package org.example;

import org.example.utils.PlotDrawer;
import org.jfree.chart.plot.Plot;

public class Main
{
    public static int[] bits = {
            1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1,
            1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1
    };

    public static void main(String[] args)
    {
//        zadanie1();
        zadanie2();
        zadanie3();
//        zadanie4();
    }

    public static void zadanie1()
    {
        System.out.println("Original data:");
        printBits(bits);

        int[] encodedBits = TransmissionSystem.encode(bits);
        System.out.println("Encoded data:");
        printBits(encodedBits);

        String modulationType = "FSK";
        System.out.println("Modulation type: " + modulationType);
        double[] modulatedBits = TransmissionSystem.modulate(encodedBits, modulationType);

        int[] demodulatedBits = TransmissionSystem.demodulate(modulatedBits, modulationType);
        System.out.println("Demodulated data:");
        printBits(demodulatedBits);

        int[] decodedBits = TransmissionSystem.decode(demodulatedBits);
        System.out.println("Decoded data:");
        printBits(decodedBits);
    }

    public static void zadanie2()
    {
        int[] encodedBits = TransmissionSystem.encode(bits);

        final String[] modulationTypes = {"ASK", "PSK", "FSK"};

        double step = 0.01;
        double[] alpha = new double[(int) Math.floor(1 / step)];
        alpha[0] = 0;
        for (int i = 1; i < alpha.length; i++)
            alpha[i] = alpha[i - 1] + step;

        double[][] BER = new double[3][alpha.length];
        double[][] modulations = new double[3][];
        double[][] noisedModulations = new double[3][];
        int[][] demodulations = new int[3][];
        int[][] decodedData = new int[3][];

        double[] testModulation = TransmissionSystem.modulate(bits, "ASK");
        for (int i = 0; i < alpha.length; i++)
        {
            double[] noise = TransmissionSystem.generateNoise(testModulation.length, alpha[i]);
            for (int j = 0; j < 3; j++)
            {
                modulations[j] = TransmissionSystem.modulate(encodedBits, modulationTypes[j]);
                noisedModulations[j] = TransmissionSystem.addNoiseToSignal(modulations[j], noise);
                demodulations[j] = TransmissionSystem.demodulate(noisedModulations[j], modulationTypes[j]);
                decodedData[j] = TransmissionSystem.decode(demodulations[j]);
                BER[j][i] = TransmissionSystem.compareBitVectors(bits, decodedData[j]);
            }
        }

        PlotDrawer.compareDoubles(alpha, BER[0], alpha.length, "Alpha and BER dependency (ASK)", "alpha_ASK");
        PlotDrawer.compareDoubles(alpha, BER[1], alpha.length, "Alpha and BER dependency (PSK)", "alpha_PSK");
        PlotDrawer.compareDoubles(alpha, BER[2], alpha.length, "Alpha and BER dependency (FSK)", "alpha_FSK");
    }

    public static void zadanie3()
    {
        int[] encodedBits = TransmissionSystem.encode(bits);

        final String[] modulationTypes = {"ASK", "PSK", "FSK"};

        double step = 0.05;
        int maxValue = 100;
        double[] beta = new double[(int) Math.floor(maxValue / step)];
        beta[0] = 0;
        for (int i = 1; i < beta.length; i++)
            beta[i] = beta[i - 1] + step;

        double[][] BER = new double[3][beta.length];
        double[][] modulations = new double[3][];
        double[][] noisedModulations = new double[3][];
        int[][] demodulations = new int[3][];
        int[][] decodedData = new int[3][];

        int modulationLength = (TransmissionSystem.modulate(bits, "ASK")).length;
        for (int i = 0; i < beta.length; i++)
        {
            double[] noise = TransmissionSystem.generateDamping(modulationLength, beta[i]);
            for (int j = 0; j < 3; j++)
            {
                modulations[j] = TransmissionSystem.modulate(encodedBits, modulationTypes[j]);
                noisedModulations[j] = TransmissionSystem.addDampToSignal(modulations[j], noise);
                demodulations[j] = TransmissionSystem.demodulate(noisedModulations[j], modulationTypes[j]);
                decodedData[j] = TransmissionSystem.decode(demodulations[j]);
                BER[j][i] = TransmissionSystem.compareBitVectors(bits, decodedData[j]);
            }
        }

        PlotDrawer.compareDoubles(beta, BER[0], beta.length, "Beta and BER dependency (ASK)", "beta_ASK");
        PlotDrawer.compareDoubles(beta, BER[1], beta.length, "Beta and BER dependency (PSK)", "beta_PSK");
        PlotDrawer.compareDoubles(beta, BER[2], beta.length, "Beta and BER dependency (FSK)", "beta_FSK");
    }

    public static void zadanie4()
    {

    }

    public static void printBits(int[] data)
    {
        for(int i : data)
            System.out.print(i);

        System.out.println();
        System.out.println();
    }

    // Utils
    public static void printData(double[] data)
    {
        for(double i : data)
            System.out.print(i + " ");

        System.out.println();
        System.out.println();
    }

    public static int[] stringToBits(String input)
    {
        int[] bitArray = new int[input.length() * 8];

        int index = 0;
        for (char c : input.toCharArray())
            for (int i = 7; i >= 0; i--)
                bitArray[index++] = ((int) c >> i) & 1;

        return bitArray;
    }
}
