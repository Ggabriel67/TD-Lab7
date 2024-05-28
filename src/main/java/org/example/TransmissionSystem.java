package org.example;

import org.example.utils.Demodulations;
import org.example.utils.Hamming_7_4;
import org.example.utils.Modulations;

public class TransmissionSystem
{
    public static int[] encode(int[] bits)
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
                index++;
                j++;
            }
        }

        return encodedBits;
    }

    public static double[] modulate(int[] encodedBits, String modulationType)
    {
        Modulations m = new Modulations(encodedBits);
        double[][] modulations = Modulations.generateModulations();

        switch(modulationType)
        {
            case "ASK" -> {return modulations[0];}
            case "PSK" -> {return modulations[1];}
            case "FSK" -> {return modulations[2];}
        }

        return modulations[0]; // default
    }

    public static int[] demodulate(double[] modulatedBits, String demodulationType)
    {
        switch(demodulationType)
        {
            case "ASK" -> {return Demodulations.demodulateASK(modulatedBits, modulatedBits.length);}
            case "PSK" -> {return Demodulations.demodulatePSK(modulatedBits, modulatedBits.length);}
            case "FSK" -> {return Demodulations.demodulateFSK(modulatedBits, modulatedBits.length);}
        }

        return Demodulations.demodulateASK(modulatedBits, modulatedBits.length); // default
    }

    public static int[] decode(int[] demodulatedBits)
    {
        int subArrayCount = demodulatedBits.length / 7;
        int[] decodedBits = new int[demodulatedBits.length - (subArrayCount * 3)];

        int index = 0;
        for(int i = 0; i < demodulatedBits.length; i += 7)
        {
            int[] subArray = new int[7];
            for(int j = i, k = 0; j < i + 7; j++, k++)
            {
                subArray[k] = demodulatedBits[j];
            }
            int[] decodedSubArray = Hamming_7_4.decode(subArray);

            int j = 0;
            while(j < decodedSubArray.length)
            {
                decodedBits[index] = decodedSubArray[j];
                index++;
                j++;
            }
        }

        return decodedBits;
    }
}
