package org.example.utils;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class Modulations
{
    public static int Tc = 4;
    public static int fs = 1500;
    public static int N = Tc * fs;
    public static int[] b;
    public static int M;
    public static double Tb;
    public static int Tbp;
    public static int W = 1;
    public static double fn;
    public static double A1 = 1.0;
    public static double A2 = 0.5;
    public static double fn1;
    public static double fn2;

    public Modulations(int[] encodedBits)
    {
        b = encodedBits;
        M = b.length;
        Tb = (double) Tc/M;
        Tbp = (int) (Tb * fs);
        fn = 1/Tb * W;
        fn1 = (W + 1)/Tb;
        fn2 = (W + 2)/Tb;
    }

    public static double[][] generateModulations()
    {
        double[] zA = new double[N];
        double[] zP = new double[N];
        double[] zF = new double[N];

        double A = 0;
        int k = 0;
        for (int n = 0; n < N; n++)
        {
            double t = (double) n / fs;
            if (n % Tbp == 0  && k != b.length - 1)
            {
                A = b[k] == 0 ? A1 : A2;
                k++;
            }
            zA[n] = A * sin(2 * PI * fn * t);
            zP[n] = sin(2 * PI * fn * t + (b[k - 1] == 0 ? 0 : PI));
            zF[n] = sin(2 * PI * (b[k - 1] == 0 ? fn1 : fn2) * t);
        }

        return new double[][]{zA, zP, zF};
    }

    public static double[] generateSinus()
    {
        double[] sinus = new double[N];
        for (int n = 0; n < N; n++)
        {
            double t = (double) n / fs;
            sinus[n] = sin(2 * PI * fn * t);
        }

        return sinus;
    }

    public static double[] generateSinusFSK(double fn)
    {
        double[] sinus = new double[N];
        for (int n = 0; n < N; n++)
        {
            double t = (double) n / fs;
            sinus[n] = sin(2 * PI * fn * t);
        }

        return sinus;
    }
}
