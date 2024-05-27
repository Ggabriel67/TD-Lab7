package org.example.utils;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import static java.lang.Math.*;

public class Utils
{
    public static void transform(Complex[] arr, int N, String dirName, String filename, int fs)
    {
        double[] M = calculateAmplitudeSpectrum(arr, N);
        double[] MPrime = calculateDecibelScale(M, N);
        double[] f = calculateFrequencyScale(N, fs);
        PlotDrawer.drawSpectrumPlot(f, MPrime, N, filename);
    }

    public static Complex[] calculateDFT(double[] X, int N)
    {
        Complex[] dft = new Complex[N];

        for (int k = 0; k < N; k++)
        {
            Complex sum = new Complex(0, 0);
            for (int n = 0; n < N; n++)
            {
                double realPart = X[n] * cos(-(2 * PI * k * n) / N);
                double imagPart = X[n] * sin(-(2 * PI * k * n) / N);
                Complex term = new Complex(realPart, imagPart);
                sum = sum.add(term);
            }
            dft[k] = sum;
        }
        return dft;
    }

    public static Complex[] calculateFFT(double[] X, int N)
    {
        int paddedLength = 1;
        while (paddedLength < N) {
            paddedLength *= 2;
        }

        double[] paddedX = new double[paddedLength];
        System.arraycopy(X, 0, paddedX, 0, N);

        return new FastFourierTransformer(DftNormalization.STANDARD)
                .transform(paddedX, TransformType.FORWARD);
    }

    public static double[] calculateAmplitudeSpectrum(Complex[] dft, int N)
    {
        double[] M = new double[N/2 - 1];

        for(int k = 0; k < N/2 - 1; k++)
        {
            double realPart = dft[k].getReal();
            double imagPart = dft[k].getImaginary();
            M[k] = sqrt(pow(realPart, 2) + pow(imagPart, 2));
        }
        return M;
    }

    public static double[] calculateDecibelScale(double[] M, int N)
    {
        double[] MPrime = new double[M.length];

        for(int k = 0; k < M.length; k++)
            MPrime[k] = 10 * log10(M[k]);

        return MPrime;
    }

    public static double[] calculateFrequencyScale(int N, double fs)
    {
        double[] f = new double[N];

        for(int k = 0; k < N/2 - 1; k++)
            f[k] = k * (fs / N);

        return f;
    }
}
