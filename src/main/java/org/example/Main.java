package org.example;

import org.example.utils.PlotDrawer;
import org.jfree.chart.plot.Plot;

import java.io.FileWriter;
import java.io.IOException;

public class Main
{
    public static int[] bits = {
            1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1,
            1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1,     1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1,
            1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1
    };

    public static void main(String[] args)
    {
        zadanie1();
//        zadanie2();
//        zadanie3();
//        zadanie4();
    }

    public static void zadanie1()
    {
        System.out.println("Original data:");
        printBits(bits);

        int[] encodedBits = TransmissionSystem.encode(bits);
        System.out.println("Encoded data:");
        printBits(encodedBits);

        final String ASK = "ASK", PSK = "PSK", FSK = "FSK";
        double[] modulatedBitsASK = TransmissionSystem.modulate(encodedBits, ASK);
        double[] modulatedBitsPSK = TransmissionSystem.modulate(encodedBits, PSK);
        double[] modulatedBitsFSK = TransmissionSystem.modulate(encodedBits, FSK);

        int[] demodulatedBitsASK = TransmissionSystem.demodulate(modulatedBitsASK, ASK);
        int[] demodulatedBitsPSK = TransmissionSystem.demodulate(modulatedBitsPSK, PSK);
        int[] demodulatedBitsFSK = TransmissionSystem.demodulate(modulatedBitsFSK, FSK);

        System.out.println("Demodulated data (ASK):");
        printBits(demodulatedBitsASK);
        System.out.println("Demodulated data (PSK):");
        printBits(demodulatedBitsPSK);
        System.out.println("Demodulated data (FSK):");
        printBits(demodulatedBitsFSK);

        int[] decodedBitsASK = TransmissionSystem.decode(demodulatedBitsASK);
        int[] decodedBitsPSK = TransmissionSystem.decode(demodulatedBitsPSK);
        int[] decodedBitsFSK = TransmissionSystem.decode(demodulatedBitsFSK);

        double[] BER = new double[3];
        BER[0] = TransmissionSystem.compareBitVectors(bits, decodedBitsASK);
        BER[1] = TransmissionSystem.compareBitVectors(bits, decodedBitsPSK);
        BER[2] = TransmissionSystem.compareBitVectors(bits, decodedBitsFSK);

        System.out.println("Decoded data (ASK): BER = " + BER[0]);
        printBits(decodedBitsASK);
        System.out.println("Decoded data (PSK): BER = " + BER[1]);
        printBits(decodedBitsPSK);
        System.out.println("Decoded data (FSK): BER = " + BER[2]);
        printBits(decodedBitsFSK);
    }

    public static void zadanie2()
    {
        int[] encodedBits = TransmissionSystem.encode(bits);

        final String[] modulationTypes = {"ASK", "PSK", "FSK"};

        double step = 0.02;
        double[] alpha = new double[(int) Math.floor(1 / step)];
        alpha[0] = 0;
        for (int i = 1; i < alpha.length; i++)
            alpha[i] = alpha[i - 1] + step;

        double[][] BER = new double[3][alpha.length];
        double[][] modulations = new double[3][];
        double[][] noisedModulations = new double[3][];
        int[][] demodulations = new int[3][];
        int[][] decodedData = new int[3][];

        int modulationLength = (TransmissionSystem.modulate(bits, "ASK")).length;

        double[][] noise = new double[alpha.length][modulationLength];
        for(int i = 0; i < alpha.length; i++)
            noise[i] = TransmissionSystem.generateNoise(modulationLength, alpha[i]);

        for (int i = 0; i < alpha.length; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                modulations[j] = TransmissionSystem.modulate(encodedBits, modulationTypes[j]);
                noisedModulations[j] = TransmissionSystem.addNoiseToSignal(modulations[j], noise[i]);
                demodulations[j] = TransmissionSystem.demodulate(noisedModulations[j], modulationTypes[j]);
                decodedData[j] = TransmissionSystem.decode(demodulations[j]);
                BER[j][i] = TransmissionSystem.compareBitVectors(bits, decodedData[j]);
            }
        }

        PlotDrawer.compareDoubles(alpha, BER[0], alpha.length, "alpha", "Alpha and BER dependency (ASK)", "alpha_ASK");
        PlotDrawer.compareDoubles(alpha, BER[1], alpha.length, "alpha", "Alpha and BER dependency (PSK)", "alpha_PSK");
        PlotDrawer.compareDoubles(alpha, BER[2], alpha.length, "alpha", "Alpha and BER dependency (FSK)", "alpha_FSK");
    }

    public static void zadanie3()
    {
        int[] encodedBits = TransmissionSystem.encode(bits);

        final String[] modulationTypes = {"ASK", "PSK", "FSK"};

        double step = 0.2;
        int maxValue = 10;
        double[] beta = new double[(int) Math.floor(maxValue / step)];
        beta[0] = 0;
        for (int i = 1; i < beta.length; i++)
            beta[i] = beta[i - 1] + step;

        double[][] BER = new double[3][beta.length];
        double[][] modulations = new double[3][];
        double[][] dampedModulations = new double[3][];
        int[][] demodulations = new int[3][];
        int[][] decodedData = new int[3][];

        int modulationLength = (TransmissionSystem.modulate(bits, "ASK")).length;

        double[][] damp = new double[beta.length][modulationLength];
        for(int i = 0; i < damp.length; i++)
            damp[i] = TransmissionSystem.generateDamping(modulationLength, beta[i]);

        for (int i = 0; i < beta.length; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                modulations[j] = TransmissionSystem.modulate(encodedBits, modulationTypes[j]);
                dampedModulations[j] = TransmissionSystem.addDampToSignal(modulations[j], damp[i]);
                demodulations[j] = TransmissionSystem.demodulate(dampedModulations[j], modulationTypes[j]);
                decodedData[j] = TransmissionSystem.decode(demodulations[j]);
                BER[j][i] = TransmissionSystem.compareBitVectors(bits, decodedData[j]);
            }
        }

        PlotDrawer.compareDoubles(beta, BER[0], beta.length, "beta", "Beta and BER dependency (ASK)", "beta_ASK");
        PlotDrawer.compareDoubles(beta, BER[1], beta.length, "beta", "Beta and BER dependency (PSK)", "beta_PSK");
        PlotDrawer.compareDoubles(beta, BER[2], beta.length, "beta", "Beta and BER dependency (FSK)", "beta_FSK");
    }

    public static void zadanie4()
    {
        int[] encodedBits = TransmissionSystem.encode(bits);

        final String[] modulationTypes = {"ASK", "PSK", "FSK"};

        double alphaStep = 0.02;
        double[] alpha = new double[(int) Math.floor(1 / alphaStep)];
        alpha[0] = 0;
        for (int i = 1; i < alpha.length; i++)
            alpha[i] = alpha[i - 1] + alphaStep;

        double betaStep = 0.2;
        int maxValue = 10;
        double[] beta = new double[(int) Math.floor(maxValue / betaStep)];
        beta[0] = 0;
        for (int i = 1; i < beta.length; i++)
            beta[i] = beta[i - 1] + betaStep;

        int alphaLength = alpha.length;
        int betaLength = beta.length;

        double[][] BER_I_II_ASK = new double[alphaLength][betaLength];
        double[][] BER_II_I_ASK = new double[alphaLength][betaLength];
        double[][] BER_I_II_PSK = new double[alphaLength][betaLength];
        double[][] BER_II_I_PSK = new double[alphaLength][betaLength];
        double[][] BER_I_II_FSK = new double[alphaLength][betaLength];
        double[][] BER_II_I_FSK = new double[alphaLength][betaLength];
        double[][] modulations = new double[3][];
        double[][] noisedModulations = new double[3][];
        double[][] dampedModulations = new double[3][];
        int[][] demodulations = new int[3][];
        int[][] decodedData = new int[3][];

        int modulationLength = (TransmissionSystem.modulate(bits, "ASK")).length;

        double[][] noise = new double[alphaLength][modulationLength];
        double[][] damp = new double[betaLength][modulationLength];
        for(int i = 0; i < alphaLength; i++)
            noise[i] = TransmissionSystem.generateDamping(modulationLength, alpha[i]);

        for(int i = 0; i < betaLength; i++)
            damp[i] = TransmissionSystem.generateDamping(modulationLength, beta[i]);

        for(int i = 0; i < 3; i++)
        {
            for (int j = 0; j < alphaLength; j++)
            {
                for (int k = 0; k < betaLength; k++)
                {
                    modulations[i] = TransmissionSystem.modulate(encodedBits, modulationTypes[i]);
                    noisedModulations[i] = TransmissionSystem.addNoiseToSignal(modulations[i], noise[j]);
                    dampedModulations[i] = TransmissionSystem.addDampToSignal(noisedModulations[i], damp[k]);
                    demodulations[i] = TransmissionSystem.demodulate(dampedModulations[i], modulationTypes[i]);
                    decodedData[i] = TransmissionSystem.decode(demodulations[i]);
                    switch(i)
                    {
                        case 0 -> BER_I_II_ASK[j][k] = TransmissionSystem.compareBitVectors(bits, decodedData[i]);
                        case 1 -> BER_I_II_PSK[j][k] = TransmissionSystem.compareBitVectors(bits, decodedData[i]);
                        case 2 -> BER_I_II_FSK[j][k] = TransmissionSystem.compareBitVectors(bits, decodedData[i]);
                    }
                }
            }
        }

        for(int i = 0; i < 3; i++)
        {
            for (int j = 0; j < alphaLength; j++)
            {
                for (int k = 0; k < betaLength; k++)
                {
                    modulations[i] = TransmissionSystem.modulate(encodedBits, modulationTypes[i]);
                    noisedModulations[i] = TransmissionSystem.addDampToSignal(modulations[i], noise[k]);
                    dampedModulations[i] = TransmissionSystem.addNoiseToSignal(noisedModulations[i], damp[j]);
                    demodulations[i] = TransmissionSystem.demodulate(dampedModulations[i], modulationTypes[i]);
                    decodedData[i] = TransmissionSystem.decode(demodulations[i]);
                    switch(i)
                    {
                        case 0 -> BER_II_I_ASK[j][k] = TransmissionSystem.compareBitVectors(bits, decodedData[i]);
                        case 1 -> BER_II_I_PSK[j][k] = TransmissionSystem.compareBitVectors(bits, decodedData[i]);
                        case 2 -> BER_II_I_FSK[j][k] = TransmissionSystem.compareBitVectors(bits, decodedData[i]);
                    }
                }
            }
        }

        try {
            FileWriter ASK_I_II = new FileWriter("ber_files/ASK_I_II.csv");
            FileWriter PSK_I_II = new FileWriter("ber_files/PSK_I_II.csv");
            FileWriter FSK_I_II = new FileWriter("ber_files/FSK_I_II.csv");
            FileWriter ASK_II_I = new FileWriter("ber_files/ASK_II_I.csv");
            FileWriter PSK_II_I = new FileWriter("ber_files/PSK_II_I.csv");
            FileWriter FSK_II_I = new FileWriter("ber_files/FSK_II_I.csv");

            FileWriter ALPHA = new FileWriter("ber_files/alpha.csv");
            FileWriter BETA = new FileWriter("ber_files/beta.csv");

            int N = BER_I_II_ASK[0].length;

            System.out.println("N = " + N);

            for(int i = 0; i < N; i++)
            {
                if(i == N - 1)
                {
                    ALPHA.write(alpha[i] + "\n");
                    BETA.write(beta[i] + "\n");
                }
                else
                {
                    ALPHA.write(alpha[i] + ",");
                    BETA.write(beta[i] + ",");
                }
            }
            ALPHA.close();
            BETA.close();

            for(int i = 0; i < N; i++)
            {
                for(int j = 0; j < N; j++)
                {
                    if(j == N - 1)
                    {
                        ASK_I_II.write(BER_I_II_ASK[i][j] + "\n");
                        PSK_I_II.write(BER_I_II_PSK[i][j] + "\n");
                        FSK_I_II.write(BER_I_II_FSK[i][j] + "\n");
                        ASK_II_I.write(BER_II_I_ASK[i][j] + "\n");
                        PSK_II_I.write(BER_II_I_PSK[i][j] + "\n");
                        FSK_II_I.write(BER_II_I_FSK[i][j] + "\n");
                    }
                    else
                    {
                        ASK_I_II.write(BER_I_II_ASK[i][j] + ",");
                        PSK_I_II.write(BER_I_II_PSK[i][j] + ",");
                        FSK_I_II.write(BER_I_II_FSK[i][j] + ",");
                        ASK_II_I.write(BER_II_I_ASK[i][j] + ",");
                        PSK_II_I.write(BER_II_I_PSK[i][j] + ",");
                        FSK_II_I.write(BER_II_I_FSK[i][j] + ",");
                    }
                }
            }
            ASK_I_II.close();
            PSK_I_II.close();
            FSK_I_II.close();
            ASK_II_I.close();
            PSK_II_I.close();
            FSK_II_I.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("KONIEC");
    }

    public static void printBits(int[] data)
    {
        for(int i : data)
            System.out.print(i);

        System.out.println();
        System.out.println();
    }
}
