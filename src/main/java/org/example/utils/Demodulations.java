package org.example.utils;

import org.jfree.chart.util.ArrayUtils;

import java.util.*;

import static org.example.utils.Modulations.*;
import static org.example.utils.PlotDrawer.*;

public class Demodulations
{
    public static int[] demodulateASK(double[] zA, int N)
    {
        double[] sinus = generateSinus();
        int Tbp = Modulations.Tbp;
        int[] c = new int[N];
        double[] x = new double[N];
        double[] p = new double[N];
        for(int n = 0; n < N; n++)
        {
            x[n] = zA[n] * sinus[n];
        }

        double s = 0;
        for(int n = 0; n < N; n++)
        {
            if(n % Tbp == 0) s = 0;
            s += x[n];
            p[n] = s;
        }

//        double h = (A1 / A2) * Tbp;
        double max = Arrays.stream(p).max().orElse(0);
        double h = max/2 + 1;

        for(int n = 0; n < N; n++)
        {
            c[n] = p[n] > h ? 1 : 0;
        }

//        drawSignalPlot(sinus, N, "sinus");
//        drawSignalPlot(zA, N, "ask_z");
//        drawSignalPlot(x, N, "ask_x");
//        drawSignalPlot(p, N, "ask_p");
//        drawBitSequence(c, N, "ask_c");

        int[] bits = convertToBits(c);
        for(int i = 0; i < bits.length; i++)
            bits[i] = bits[i] == 0 ? 1 : 0;

        return bits;
//        compareBitVectors(Modulations.b, bits, bits.length, "porownanie_ask");
    }

    public static int[] demodulatePSK(double[] zP, int N)
    {
        double[] sinus = generateSinus();
        int Tbp = Modulations.Tbp;
        int[] c = new int[N];
        double[] x = new double[N];
        double[] p = new double[N];

        for(int n = 0; n < N; n++)
        {
            x[n] = zP[n] * sinus[n];
        }

        double s = 0;
        for(int n = 0; n < N; n++)
        {
            if(n % Tbp == 0) s = 0;
            s += x[n];
            p[n] = s;
        }

        for(int n = 0; n < N; n++)
        {
            c[n] = p[n] < 0 ? 1 : 0;
        }

//        drawSignalPlot(zP, N, "psk_z");
//        drawSignalPlot(x, N, "psk_x");
//        drawSignalPlot(p, N, "psk_p");
//        drawBitSequence(c, N, "psk_c");

        return convertToBits(c);
//        compareBitVectors(Modulations.b, bits, bits.length, "porownanie_psk");
    }

    public static int[] demodulateFSK(double[] zF, int N)
    {
        double[] sinus1 = generateSinusFSK(Modulations.fn1);
        double[] sinus2 = generateSinusFSK(Modulations.fn2);

        int Tbp = Modulations.Tbp;
        int[] c = new int[N];
        double[] x1 = new double[N];
        double[] x2 = new double[N];
        double[] p1 = new double[N];
        double[] p2 = new double[N];

        for(int n = 0; n < N; n++)
        {
            x1[n] = zF[n] * sinus1[n];
            x2[n] = zF[n] * sinus2[n];
        }

        double s = 0;
        for(int n = 0; n < N; n++)
        {
            if(n % Tbp == 0) s = 0;
            s += x1[n];
            p1[n] = s;
        }

        s = 0;
        for(int n = 0; n < N; n++)
        {
            if(n % Tbp == 0) s = 0;
            s += x2[n];
            p2[n] = s;
        }

        double[] p = new double[N];
        for(int n = 0; n < N; n++)
        {
            p[n] = -p1[n] + p2[n];
        }

        for(int n = 0; n < N; n++)
        {
            c[n] = p[n] > 0 ? 1 : 0;
        }

//        drawSignalPlot(zF, N, "fsk_z");
//        drawSignalPlot(x1, N, "fsk_x1");
//        drawSignalPlot(x2, N, "fsk_x2");
//        drawSignalPlot(p1, N, "fsk_p1");
//        drawSignalPlot(p2, N, "fsk_p2");
//        drawSignalPlot(p, N, "fsk_p");
//        drawBitSequence(c, N, "fsk_c");
//
        return convertToBits(c);
//        compareBitVectors(Modulations.b, bits, bits.length, "porownanie_fsk");
    }

    public static int[] convertToBits(int[]c)
    {
        double db = 0;
        List<Integer> bits = new ArrayList<>();
        for(int i = 0; i < c.length; i++)
        {
            if(bits.size() >= M)
                break;

            db += c[i];
            if((i % Tbp) == 0 && i != 0)
            {
                bits.add((db / Tbp) > 0.3 ? 1 : 0);
                db = 0;
            }
        }

        return bits.stream().mapToInt(i -> i).toArray();
    }

    static public int[] ConvertBitStreamToBitStream(int[] bit_signal)
    {
        List<Integer> bit_stream = new ArrayList<>();
        int N = (int)(fs * Tb);

        int sum;
        int bit = -1;
        for (int b = 0; b < M; b++)
        {
            sum = 0;
            for (int n = 0; n < N; n++)
            {
                sum += (int)bit_signal[b * N + n];
                if (sum > 0)
                {
                    bit = 1;
                }
                else if (sum == 0)
                {
                    bit = 0;
                }
            }
            bit_stream.add(bit);
        }
        int[] b = new int[bit_stream.size()];
        for(int i = 0; i < b.length; i++) b[i] = bit_stream.get(i);

        return b;
    }
}
