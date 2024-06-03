package org.example.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlotDrawer extends JFrame
{
    public PlotDrawer(double[] data, int N, String name)
    {
        super(name);

        int width = 800;
        int height = 600;

        XYSeries series = new XYSeries("signal");

        for (int i = 0; i < N; i++)
        {
            series.add(i + 1, data[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(name, "X", "Y", dataset);
        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(width, height));

        getContentPane().add(chartPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        saveChartAsPNG(chart, name, width, height);
    }

    public PlotDrawer(double[] data1, double[] data2, int N, String filename, String modulationType)
    {
        super(filename);

        int width = 800;
        int height = 600;

        XYSeries series = new XYSeries(modulationType);

        for (int i = 0; i < N; i++)
            series.add(data1[i], data2[i]);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(filename, "alpha", "BER", dataset);

        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(width, height));

        getContentPane().add(chartPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        saveChartAsPNG(chart, filename, width, height);
    }

    public PlotDrawer(int[] data, int N, String name)
    {
        super(name);

        int width = 800;
        int height = 600;

        XYSeries series = new XYSeries("bits");

        for (int i = 0; i < N; i++)
        {
            series.add(i + 1, data[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(name, "X", "Y", dataset);
        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(width, height));

        getContentPane().add(chartPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        saveChartAsPNG(chart, name, width, height);
    }

//    public PlotDrawer(int[] data1, int[] data2, int N, String name)
//    {
//        super(name);
//
//        int width = 1000;
//        int height = 400;
//
//        XYSeries series1 = new XYSeries("data1");
//        XYSeries series2 = new XYSeries("data2");
//
//        for (int i = 0; i < N; i++) {
//            series1.add(i + 1, data1[i]);
//            series2.add(i + 1, data2[i]);
//        }
//
//        XYSeriesCollection dataset1 = new XYSeriesCollection(series1);
//        XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
//
//        JFreeChart chart1 = ChartFactory.createXYLineChart("Modulation", "x", "", dataset1);
//        JFreeChart chart2 = ChartFactory.createXYLineChart("Demodulation", "", "", dataset2);
//
//        chart1.setBackgroundPaint(Color.white);
//        chart2.setBackgroundPaint(Color.white);
//
//        ChartPanel chartPanel1 = new ChartPanel(chart1);
//        ChartPanel chartPanel2 = new ChartPanel(chart2);
//
//        chartPanel1.setPreferredSize(new Dimension(width, height));
//        chartPanel2.setPreferredSize(new Dimension(width, height));
//
//        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
//
//        getContentPane().add(chartPanel1);
//        getContentPane().add(chartPanel2);
//
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//
//        saveChartsAsPNG(chart1, chart2, name, width, height * 2);
//    }

    public PlotDrawer(int[] data1, int[] data2, int N, String name) {
        super(name);

        int width = 800;
        int height = 300;

        XYSeries series1 = new XYSeries("modulation");
        XYSeries series2 = new XYSeries("demodulation");

        for (int i = 0; i < N; i++)
        {
            series1.add(i, data1[i]);
            series1.add(i + 1, data1[i]);
            series2.add(i, data2[i]);
            series2.add(i + 1, data2[i]);
        }

        XYSeriesCollection dataset1 = new XYSeriesCollection(series1);
        XYSeriesCollection dataset2 = new XYSeriesCollection(series2);

        JFreeChart chart1 = ChartFactory.createXYStepChart("Modulation", "x", "", dataset1);
        JFreeChart chart2 = ChartFactory.createXYStepChart("Demodulation", "", "", dataset2);

        chart1.setBackgroundPaint(Color.white);
        chart2.setBackgroundPaint(Color.white);

        ChartPanel chartPanel1 = new ChartPanel(chart1);
        ChartPanel chartPanel2 = new ChartPanel(chart2);

        chartPanel1.setPreferredSize(new Dimension(width, height));
        chartPanel2.setPreferredSize(new Dimension(width, height));

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        getContentPane().add(chartPanel1);
        getContentPane().add(chartPanel2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        saveChartsAsPNG(chart1, chart2, name, width, height * 2);
    }

    public void saveChartAsPNG(JFreeChart chart, String filename, int width, int height)
    {
        String path = String.format("plots/%s.png", filename);
        try {
            ChartUtils.saveChartAsPNG(new File(path), chart, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveChartsAsPNG(JFreeChart chart1, JFreeChart chart2, String filename, int width, int height)
    {
        String path = String.format("plots/%s.png", filename);
        try {
            CombinedDomainXYPlot combinedPlot = new CombinedDomainXYPlot();

            combinedPlot.setOrientation(PlotOrientation.VERTICAL);

            combinedPlot.add((XYPlot) chart1.getPlot());
            combinedPlot.add((XYPlot) chart2.getPlot());

            JFreeChart combinedChart = new JFreeChart("Porownanie wektorow bitowych", JFreeChart.DEFAULT_TITLE_FONT, combinedPlot, true);

            ChartUtils.saveChartAsPNG(new File(path), combinedChart, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void drawSignalPlot(double[] data, int N, String name)
    {
        SwingUtilities.invokeLater(() -> new PlotDrawer(data, N, name));
    }

//    public static void drawSpectrumPlot(double[] f, double[] MPrime, int N, String name)
//    {
//        SwingUtilities.invokeLater(() -> new PlotDrawer(f, MPrime, N, name));
//    }

    public static void drawBitSequence(int[] data, int N, String name)
    {
        SwingUtilities.invokeLater(() -> new PlotDrawer(data, N, name));
    }

    public static void compareBitVectors(int[] data1, int[] data2, int N, String name)
    {
        SwingUtilities.invokeLater(() -> new PlotDrawer(data1, data2, N, name));
    }

    public static void compareDoubles(double[] data1,double[] data2, int N, String name, String modulationType)
    {
        SwingUtilities.invokeLater(() -> new PlotDrawer(data1, data2, N, name, modulationType));
    }
}
