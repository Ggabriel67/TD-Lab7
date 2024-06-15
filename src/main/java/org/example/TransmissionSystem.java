package org.example;

import org.example.utils.Demodulations;
import org.example.utils.Hamming_7_4;
import org.example.utils.Modulations;

import java.util.Random;
import static java.lang.Math.*;

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
                subArray[k] = bits[j];

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
                subArray[k] = demodulatedBits[j];

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

    public static double[] addNoiseToSignal(double[] modulatedSignal, double[] noise)
    {
        int N = modulatedSignal.length;
        double[] noisedSignal = new double[N];

        for(int i = 0; i < N; i++)
            noisedSignal[i] = modulatedSignal[i] + noise[i];

        return noisedSignal;
    }

    public static double[] addDampToSignal(double[] modulatedSignal, double[] damp)
    {
        int N = modulatedSignal.length;
        double[] noisedSignal = new double[N];

        for(int i = 0; i < N; i++)
            noisedSignal[i] = modulatedSignal[i] * damp[i];

        return noisedSignal;
    }

    public static double compareBitVectors(int[] originalData, int[] alteredData)
    {
        int N = originalData.length;
        int E = 0;

        for(int i = 0; i < N; i++)
            if(originalData[i] != alteredData[i])
                E++;

        return (double) E / N;
    }

    public static double[] generateNoise(int length, double alpha)
    {
        double[] noise = new double[length];
        Random rand = new Random();

        for (int i = 0; i < length; i++)
            noise[i] = rand.nextDouble(-10, 10) * alpha;

        return noise;
    }

    public static double[] generateDamping(int length, double beta)
    {
        double[] damp = new double[length];

        for (int i = 0; i < length; i++)
        {
            double t = (double) i / Modulations.fs;
            damp[i] = exp(-beta * t);
        }

        return damp;
    }
}
