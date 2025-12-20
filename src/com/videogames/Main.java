package com.videogames;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Семестровый проект: Анализ данных видеоигр ===\n");

        try {
            System.out.println("1. Парсинг CSV файла...");
            CSVParser parser = new CSVParser();
            List<Game> games = parser.parseCSV("resources/video_games.csv");

            if (games.isEmpty()) {
                System.err.println("Ошибка: не удалось загрузить данные из CSV файла");
                return;
            }

            System.out.println("Успешно загружено " + games.size() + " игр\n");

            System.out.println("2. Создание базы данных...");
            DatabaseManager dbManager = new DatabaseManager("video_games.db");

            System.out.println("3. Сохранение данных в базу данных...");
            dbManager.insertGames(games);

            System.out.println("\n4. Выполнение SQL запросов...");
            GameQueries queries = new GameQueries(dbManager.getConnection());

            System.out.println("\n" + "=".repeat(60));
            System.out.println("РЕЗУЛЬТАТЫ SQL-ЗАПРОСОВ");
            System.out.println("=".repeat(60));

            queries.query1();
            queries.query2();
            queries.query3();
            queries.query5();
            queries.query6();
            queries.query7();
            queries.query8();

            System.out.println("\n5. Визуализация данных...");

            List<GameQueries.PlatformData> platformData = queries.getPlatformSalesData();
            List<GameQueries.YearData> yearData = queries.getYearlySalesData();

            ChartVisualizer visualizer = new ChartVisualizer();

            visualizer.printTextChart(platformData, "Текстовый график: Продажи по платформам");

            try {
                System.out.println("\nСоздание графических диаграмм...");
                visualizer.createPlatformPieChart(platformData);
                visualizer.createYearlyBarChart(yearData);

                System.out.println("Диаграммы успешно созданы!");
                System.out.println("(Окна с диаграммами могут быть скрыты за другими окнами)");
            } catch (Exception e) {
                System.err.println("Не удалось создать графические диаграммы: " + e.getMessage());
                System.out.println("Используются только текстовые графики.");
            }

            dbManager.close();

            System.out.println("\n" + "=".repeat(60));
            System.out.println("ПРОЕКТ УСПЕШНО ВЫПОЛНЕН!");
            System.out.println("=".repeat(60));

            System.out.println("\nНажмите Enter для завершения...");
            try {
                System.in.read();
            } catch (Exception ignored) {}

        } catch (Exception e) {
            System.err.println("Критическая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

