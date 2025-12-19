package com.videogames;

import java.sql.*;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String dbName) {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            System.out.println("Подключение к БД установлено: " + dbName);

            createTables();

        } catch (Exception e) {
            System.err.println("Ошибка подключения к БД: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createTables() {
        String sql = """
            CREATE TABLE IF NOT EXISTS games (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                rank INTEGER,
                name TEXT NOT NULL,
                platform TEXT,
                year INTEGER,
                genre TEXT,
                publisher TEXT,
                na_sales REAL,
                eu_sales REAL,
                jp_sales REAL,
                other_sales REAL,
                global_sales REAL
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблица 'games' создана или уже существует");
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы: " + e.getMessage());
        }
    }

    public void insertGames(List<Game> games) {
        String sql = """
            INSERT INTO games (rank, name, platform, year, genre, publisher, 
                             na_sales, eu_sales, jp_sales, other_sales, global_sales)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            for (Game game : games) {
                pstmt.setInt(1, game.getRank());
                pstmt.setString(2, game.getName());
                pstmt.setString(3, game.getPlatform());
                pstmt.setInt(4, game.getYear());
                pstmt.setString(5, game.getGenre());
                pstmt.setString(6, game.getPublisher());
                pstmt.setDouble(7, game.getNaSales());
                pstmt.setDouble(8, game.getEuSales());
                pstmt.setDouble(9, game.getJpSales());
                pstmt.setDouble(10, game.getOtherSales());
                pstmt.setDouble(11, game.getGlobalSales());

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            connection.commit();

            System.out.println("Успешно добавлено записей в БД: " + games.size());

        } catch (SQLException e) {
            System.err.println("Ошибка вставки данных: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Ошибка отката транзакции: " + ex.getMessage());
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка закрытия соединения: " + e.getMessage());
        }
    }
}