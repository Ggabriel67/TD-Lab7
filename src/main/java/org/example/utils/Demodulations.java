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

        int[] bits = convertToBits(c, N, Tbp);
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

        return convertToBits(c, N, Tbp);
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
        return convertToBits(c, N, Tbp);

//        compareBitVectors(Modulations.b, bits, bits.length, "porownanie_fsk");
    }

    public static int[] convertToBits(int[]c ,int N, int Tbp)
    {
        double db = 0;
        List<Integer> bits = new ArrayList<>();
        for(int i = 0; i < N; i++)
        {
            db += c[i];
            if((i % Tbp) == 0 && i != 0)
            {
                bits.add((db / Tbp) > 0.2 ? 1 : 0);
                db = 0;
            }
        }

        int[] b = new int[bits.size()];
        for(int i = 0; i < b.length; i++) b[i] = bits.get(i);

        return b;
    }
}
