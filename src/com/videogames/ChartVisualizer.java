package com.videogames;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.List;

public class ChartVisualizer {

    public void createPlatformPieChart(List<GameQueries.PlatformData> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        int limit = Math.min(data.size(), 8);
        for (int i = 0; i < limit; i++) {
            GameQueries.PlatformData item = data.get(i);
            dataset.setValue(item.getPlatform(), item.getSales());
        }

        if (data.size() > 8) {
            double others = 0;
            for (int i = 8; i < data.size(); i++) {
                others += data.get(i).getSales();
            }
            dataset.setValue("Другие", others);
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Распределение продаж по платформам",
                dataset,
                true,   // легенда
                true,   // подсказки
                false   // URLs
        );

        displayChart(chart, "Продажи по платформам");
    }

    public void createYearlyBarChart(List<GameQueries.YearData> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (GameQueries.YearData item : data) {
            dataset.addValue(item.getEuSales(), "Европа", String.valueOf(item.getYear()));
            dataset.addValue(item.getGlobalSales(), "Глобальные", String.valueOf(item.getYear()));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Продажи по годам (2000-2006)",
                "Год",
                "Продажи (млн. копий)",
                dataset,
                PlotOrientation.VERTICAL,
                true,   // легенда
                true,   // подсказки
                false   // URLs
        );

        displayChart(chart, "Продажи по годам");
    }

    private void displayChart(JFreeChart chart, String title) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

            frame.setContentPane(chartPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public void printTextChart(List<GameQueries.PlatformData> data, String title) {
        System.out.println("\n" + title);
        System.out.println("=".repeat(50));

        double maxSales = data.stream()
                .mapToDouble(GameQueries.PlatformData::getSales)
                .max()
                .orElse(1.0);

        for (GameQueries.PlatformData item : data) {
            int barLength = (int) ((item.getSales() / maxSales) * 40);
            String bar = "█".repeat(Math.max(0, barLength));

            System.out.printf("%-10s | %-40s | %.2f млн.%n",
                    item.getPlatform(), bar, item.getSales());
        }
    }
}