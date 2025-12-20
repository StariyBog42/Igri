package com.videogames;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameQueries {
    private Connection connection;

    public GameQueries(Connection connection) {
        this.connection = connection;
    }

    public void query1() {
        System.out.println("\n=== 1. Средние глобальные продажи (2000-2006) ===");

        String sql = "SELECT AVG(global_sales) as avg_sales FROM games WHERE year BETWEEN 2000 AND 2006";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                double avgSales = rs.getDouble("avg_sales");
                System.out.printf("Средние глобальные продажи за 2000-2006: %.2f млн. копий%n", avgSales);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса 1: " + e.getMessage());
        }
    }

    public void query2() {
        System.out.println("\n=== 2. Игра с самыми высокими продажами в Европе ===");

        String sql = "SELECT name, platform, year, eu_sales FROM games ORDER BY eu_sales DESC LIMIT 1";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                System.out.println("Игра: " + rs.getString("name"));
                System.out.println("Платформа: " + rs.getString("platform"));
                System.out.println("Год: " + rs.getInt("year"));
                System.out.printf("Продажи в Европе: %.2f млн.%n", rs.getDouble("eu_sales"));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса 2: " + e.getMessage());
        }
    }

    public void query3() {
        System.out.println("\n=== 3. Продажи по платформам за 2000 год ===");

        String sql = """
            SELECT platform, SUM(global_sales) as total_sales, COUNT(*) as game_count 
            FROM games WHERE year = 2000 
            GROUP BY platform 
            ORDER BY total_sales DESC
            """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-15s %-15s %s%n", "Платформа", "Всего продаж", "Кол-во игр");
            System.out.println("--------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15s %-15.2f %d%n",
                        rs.getString("platform"),
                        rs.getDouble("total_sales"),
                        rs.getInt("game_count"));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса 3: " + e.getMessage());
        }
    }

    public void query4() {
        System.out.println("\n=== 4. Спортивные игры (2000-2006) ===");

        String sql = """
            SELECT name, platform, year, eu_sales, global_sales 
            FROM games 
            WHERE genre LIKE '%Sport%' AND year BETWEEN 2000 AND 2006
            ORDER BY eu_sales DESC
            """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-30s %-10s %-6s %-10s %-10s%n",
                    "Игра", "Платформа", "Год", "Европа", "Глобально");
            System.out.println("--------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-30s %-10s %-6d %-10.2f %-10.2f%n",
                        rs.getString("name"),
                        rs.getString("platform"),
                        rs.getInt("year"),
                        rs.getDouble("eu_sales"),
                        rs.getDouble("global_sales"));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса 4: " + e.getMessage());
        }
    }

    public void query5() {
        System.out.println("\n=== 5. Топ-10 игр по продажам в Европе ===");

        String sql = "SELECT name, platform, year, eu_sales FROM games ORDER BY eu_sales DESC LIMIT 10";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int position = 1;
            System.out.printf("%-3s %-30s %-10s %-6s %-10s%n",
                    "#", "Игра", "Платформа", "Год", "Продажи в Европе");
            System.out.println("----------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-3d %-30s %-10s %-6d %-10.2f%n",
                        position++,
                        rs.getString("name"),
                        rs.getString("platform"),
                        rs.getInt("year"),
                        rs.getDouble("eu_sales"));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса 5: " + e.getMessage());
        }
    }

    public void query6() {
        System.out.println("\n=== 6. Продажи по годам (2000-2006) ===");

        String sql = """
            SELECT year, 
                   COUNT(*) as game_count,
                   SUM(eu_sales) as total_eu_sales,
                   SUM(global_sales) as total_global_sales
            FROM games 
            WHERE year BETWEEN 2000 AND 2006
            GROUP BY year
            ORDER BY year
            """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-6s %-10s %-15s %-15s%n",
                    "Год", "Кол-во игр", "Продажи в Европе", "Глобальные продажи");
            System.out.println("------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-6d %-10d %-15.2f %-15.2f%n",
                        rs.getInt("year"),
                        rs.getInt("game_count"),
                        rs.getDouble("total_eu_sales"),
                        rs.getDouble("total_global_sales"));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса 6: " + e.getMessage());
        }
    }

    public void query7() {
        System.out.println("\n=== 7. Платформы с наибольшими продажами ===");

        String sql = """
            SELECT platform, 
                   SUM(global_sales) as total_sales,
                   COUNT(*) as game_count
            FROM games 
            GROUP BY platform
            ORDER BY total_sales DESC
            LIMIT 5
            """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-10s %-15s %s%n", "Платформа", "Всего продаж", "Кол-во игр");
            System.out.println("--------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10s %-15.2f %d%n",
                        rs.getString("platform"),
                        rs.getDouble("total_sales"),
                        rs.getInt("game_count"));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса 7: " + e.getMessage());
        }
    }

    public void query8() {
        System.out.println("\n=== 8. Топ спортивных игр (2000-2006) по продажам в Японии ===");

        String sql = """
        SELECT 
            name, 
            platform, 
            year, 
            genre,
            jp_sales,
            eu_sales,
            na_sales,
            global_sales
        FROM games 
        WHERE genre LIKE '%Sport%' 
            AND year BETWEEN 2000 AND 2006
            AND jp_sales > 0
        ORDER BY jp_sales DESC
        LIMIT 10
        """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-30s %-10s %-6s %-10s %-10s %-10s %-10s%n",
                    "Игра", "Платформа", "Год", "Япония", "Европа", "США", "Глобально");
            System.out.println("--------------------------------------------------------------------------------------------");

            int position = 1;
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.printf("%-30s %-10s %-6d %-10.2f %-10.2f %-10.2f %-10.2f%n",
                        rs.getString("name"),
                        rs.getString("platform"),
                        rs.getInt("year"),
                        rs.getDouble("jp_sales"),
                        rs.getDouble("eu_sales"),
                        rs.getDouble("na_sales"),
                        rs.getDouble("global_sales"));
            }

            if (!found) {
                System.out.println("Спортивные игры за период 2000-2006 не найдены.");
            }

            // Дополнительно: вывод самой продаваемой игры
            String sqlTop1 = """
            SELECT name, platform, year, jp_sales
            FROM games 
            WHERE genre LIKE '%Sport%' 
                AND year BETWEEN 2000 AND 2006
            ORDER BY jp_sales DESC
            LIMIT 1
            """;

            try (Statement stmt2 = connection.createStatement();
                 ResultSet rs2 = stmt2.executeQuery(sqlTop1)) {

                if (rs2.next()) {
                    System.out.println("\n★ Самая продаваемая спортивная игра в Японии (2000-2006):");
                    System.out.printf("   %s (%s, %d год) - %.2f млн. копий%n",
                            rs2.getString("name"),
                            rs2.getString("platform"),
                            rs2.getInt("year"),
                            rs2.getDouble("jp_sales"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса 8: " + e.getMessage());
        }
    }

    public List<PlatformData> getPlatformSalesData() {
        List<PlatformData> data = new ArrayList<>();

        String sql = "SELECT platform, SUM(global_sales) as sales FROM games GROUP BY platform";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                data.add(new PlatformData(
                        rs.getString("platform"),
                        rs.getDouble("sales")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка получения данных для диаграммы: " + e.getMessage());
        }

        return data;
    }

    public List<YearData> getYearlySalesData() {
        List<YearData> data = new ArrayList<>();

        String sql = """
            SELECT year, 
                   SUM(eu_sales) as eu_sales,
                   SUM(global_sales) as global_sales
            FROM games 
            WHERE year BETWEEN 2000 AND 2006
            GROUP BY year
            ORDER BY year
            """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                data.add(new YearData(
                        rs.getInt("year"),
                        rs.getDouble("eu_sales"),
                        rs.getDouble("global_sales")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка получения данных для диаграммы: " + e.getMessage());
        }

        return data;
    }

    public static class PlatformData {
        private String platform;
        private double sales;

        public PlatformData(String platform, double sales) {
            this.platform = platform;
            this.sales = sales;
        }

        public String getPlatform() { return platform; }
        public double getSales() { return sales; }
    }

    public static class YearData {
        private int year;
        private double euSales;
        private double globalSales;

        public YearData(int year, double euSales, double globalSales) {
            this.year = year;
            this.euSales = euSales;
            this.globalSales = globalSales;
        }

        public int getYear() { return year; }
        public double getEuSales() { return euSales; }
        public double getGlobalSales() { return globalSales; }
    }
}
