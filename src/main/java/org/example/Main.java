package org.example;

import org.example.utils.PlotDrawer;

public class Main
{
    public static int[] bits = {
            1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1,
            1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1
    };

    public static void main(String[] args)
    {
        zadanie1();
    }

    public static void zadanie1()
    {
        System.out.println("Original data:");
        printBits(bits);

        int[] encodedBits = TransmissionSystem.encode(bits);
        System.out.println("Encoded data:");
        printBits(encodedBits);

        String modulationType = "ASK";
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

    }

    public static void zadanie3()
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
