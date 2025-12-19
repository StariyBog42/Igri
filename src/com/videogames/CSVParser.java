package com.videogames;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    public List<Game> parseCSV(String filePath) {
        List<Game> games = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                if (line.trim().isEmpty()) continue;

                String[] values = line.split(",");

                if (values.length < 11) {
                    continue;
                }

                try {
                    Game game = new Game();

                    String rankStr = values[0].trim();
                    game.setRank(Integer.parseInt(rankStr));

                    game.setName(values[1].trim());
                    game.setPlatform(values[2].trim());

                    String yearStr = values[3].trim();
                    if (yearStr.isEmpty()) {
                        game.setYear(0);
                    } else {
                        game.setYear(Integer.parseInt(yearStr));
                    }

                    game.setGenre(values[4].trim());
                    game.setPublisher(values[5].trim());

                    game.setNaSales(parseDoubleSafe(values[6].trim()));
                    game.setEuSales(parseDoubleSafe(values[7].trim()));
                    game.setJpSales(parseDoubleSafe(values[8].trim()));
                    game.setOtherSales(parseDoubleSafe(values[9].trim()));
                    game.setGlobalSales(parseDoubleSafe(values[10].trim()));

                    games.add(game);

                } catch (NumberFormatException e) {
                    continue;
                }
            }

            System.out.println("Успешно загружено игр: " + games.size());

        } catch (Exception e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            e.printStackTrace();
        }

        return games;
    }

    private double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}