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
//        zadanie2();
//        zadanie3();
        alphaBerTests();
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
        System.out.println("Original data:");
        printBits(bits);

        int[] encodedBits = TransmissionSystem.encode(bits);
        System.out.println("Encoded data:");
        printBits(encodedBits);

        final String ASK = "ASK";
        final String PSK = "PSK";
        final String FSK = "FSK";
        double[] modulationASK = TransmissionSystem.modulate(encodedBits, ASK);
        double[] modulationPSK = TransmissionSystem.modulate(encodedBits, PSK);
        double[] modulationFSK = TransmissionSystem.modulate(encodedBits, FSK);

        double alpha = 6; // alpha = [0 : 1]
        double[] noise = TransmissionSystem.generateNoise(modulationASK.length, alpha);
        double[] noisedASK = TransmissionSystem.addNoiseToSignal(modulationASK, noise);
        double[] noisedPSK = TransmissionSystem.addNoiseToSignal(modulationPSK, noise);
        double[] noisedFSK = TransmissionSystem.addNoiseToSignal(modulationFSK, noise);

        int[] demodulatedNoisedASK = TransmissionSystem.demodulate(noisedASK, ASK);
        int[] demodulatedNoisedPSK = TransmissionSystem.demodulate(noisedPSK, PSK);
        int[] demodulatedNoisedFSK = TransmissionSystem.demodulate(noisedFSK, FSK);

        int[] decodedNoisedASK = TransmissionSystem.decode(demodulatedNoisedASK);
        int[] decodedNoisedPSK = TransmissionSystem.decode(demodulatedNoisedPSK);
        int[] decodedNoisedFSK = TransmissionSystem.decode(demodulatedNoisedFSK);

        double BER_ASK = TransmissionSystem.compareBitVectors(bits, decodedNoisedASK);
        double BER_PSK = TransmissionSystem.compareBitVectors(bits, decodedNoisedPSK);
        double BER_FSK = TransmissionSystem.compareBitVectors(bits, decodedNoisedFSK);
        System.out.println("BER ASK: " + BER_ASK);
        System.out.println("BER PSK: " + BER_PSK);
        System.out.println("BER FSK: " + BER_FSK);

        System.out.println("Decoded noised ASK");
        printBits(decodedNoisedASK);
        System.out.println("Decoded noised PSK");
        printBits(decodedNoisedPSK);
        System.out.println("Decoded noised FSK");
        printBits(decodedNoisedFSK);
    }

    public static void zadanie3()
    {
        System.out.println("Original data:");
        printBits(bits);

        int[] encodedBits = TransmissionSystem.encode(bits);
        System.out.println("Encoded data:");
        printBits(encodedBits);

        final String ASK = "ASK";
        final String PSK = "PSK";
        final String FSK = "FSK";

        double[] modulationASK = TransmissionSystem.modulate(encodedBits, ASK);
        double[] modulationPSK = TransmissionSystem.modulate(encodedBits, PSK);
        double[] modulationFSK = TransmissionSystem.modulate(encodedBits, FSK);

        double beta = 10; // beta = [0 : 10]
        double[] damp = TransmissionSystem.generateDamping(modulationASK.length, beta);
        double[] dampedASK = TransmissionSystem.addDampToSignal(modulationASK, damp);
        double[] dampedPSK = TransmissionSystem.addDampToSignal(modulationPSK, damp);
        double[] dampedFSK = TransmissionSystem.addDampToSignal(modulationFSK, damp);

        int[] demodulatedDampedASK = TransmissionSystem.demodulate(dampedASK, ASK);
        int[] demodulatedDampedPSK = TransmissionSystem.demodulate(dampedPSK, PSK);
        int[] demodulatedDampedFSK = TransmissionSystem.demodulate(dampedFSK, FSK);

        int[] decodedDampedASK = TransmissionSystem.decode(demodulatedDampedASK);
        int[] decodedDampedPSK = TransmissionSystem.decode(demodulatedDampedPSK);
        int[] decodedDampedFSK = TransmissionSystem.decode(demodulatedDampedFSK);

        double BER_ASK = TransmissionSystem.compareBitVectors(bits, decodedDampedASK);
        double BER_PSK = TransmissionSystem.compareBitVectors(bits, decodedDampedPSK);
        double BER_FSK = TransmissionSystem.compareBitVectors(bits, decodedDampedFSK);
        System.out.println("BER ASK: " + BER_ASK);
        System.out.println("BER PSK: " + BER_PSK);
        System.out.println("BER FSK: " + BER_FSK);

        System.out.println("Decoded noised ASK");
        printBits(decodedDampedASK);
        System.out.println("Decoded noised PSK");
        printBits(decodedDampedPSK);
        System.out.println("Decoded noised FSK");
        printBits(decodedDampedFSK);
    }

    public static void zadanie4()
    {

    }

    public static void alphaBerTests() {
        int[] encodedBits = TransmissionSystem.encode(bits);

        final String ASK = "ASK";
        final String PSK = "PSK";
        final String FSK = "FSK";
        final String[] modulationTypes = {"ASK", "PSK", "FSK"};

        double[] alpha = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
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

        PlotDrawer.compareDoubles(alpha, BER[0], alpha.length, "alpha_ASK", "ASK");
        PlotDrawer.compareDoubles(alpha, BER[1], alpha.length, "alpha_PSK", "PSK");
        PlotDrawer.compareDoubles(alpha, BER[2], alpha.length, "alpha_FSK", "FSK");
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
