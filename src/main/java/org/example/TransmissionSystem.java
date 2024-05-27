package org.example;

import org.example.utils.Hamming_7_4;
import org.example.utils.Modulations;

public class TransmissionSystem
{
    public static int[] bits = {
            1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1,
            1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1
    };

    public static int[] encode()
    {
        int subArraysCount = bits.length / 4;
        int[] encodedBits = new int[subArraysCount * 7];

        int index = 0;
        for(int i = 0; i < bits.length; i += 4)
        {
            int[] subArray = new int[4];
            for(int j = i, k = 0; j < i + 4; j++, k++)
            {
                subArray[k] = bits[j];
            }
            int[] encodedSubArray = Hamming_7_4.encode(subArray);

            int j = 0;
            while(j < encodedSubArray.length)
            {
                encodedBits[index] = encodedSubArray[j];
                j++;
                index++;
            }
        }

        return encodedBits;
    }

    public static double[] modulate(int[] encodedBits)
    {
        Modulations modulations = new Modulations(encodedBits);
        double[][] signals = Modulations.generateModulations();

        return signals[0]; // ASK
    }

    public static int[] demodulate()
    {
        return null;
    }
}
