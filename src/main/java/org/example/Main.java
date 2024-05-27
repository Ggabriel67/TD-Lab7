package org.example;

import org.example.utils.PlotDrawer;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Bit vector:");
        printBits(TransmissionSystem.bits);

        int[] encodedBits = TransmissionSystem.encode();
        System.out.println("Encoded bits");
        printBits(encodedBits);

        double[] modulatedBits = TransmissionSystem.modulate(encodedBits);
        printData(modulatedBits);
        PlotDrawer.drawSignalPlot(modulatedBits, modulatedBits.length, "testASK");
    }

    public static void printBits(int[] data)
    {
        for(int i : data)
            System.out.print(i);

        System.out.println();
        System.out.println();
    }

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


