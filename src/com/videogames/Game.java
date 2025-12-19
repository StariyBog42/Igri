package com.videogames;

public class Game {
    private int rank;
    private String name;
    private String platform;
    private int year;
    private String genre;
    private String publisher;
    private double naSales;
    private double euSales;
    private double jpSales;
    private double otherSales;
    private double globalSales;

    public Game() {}

    public Game(int rank, String name, String platform, int year, String genre,
                String publisher, double naSales, double euSales, double jpSales,
                double otherSales, double globalSales) {
        this.rank = rank;
        this.name = name;
        this.platform = platform;
        this.year = year;
        this.genre = genre;
        this.publisher = publisher;
        this.naSales = naSales;
        this.euSales = euSales;
        this.jpSales = jpSales;
        this.otherSales = otherSales;
        this.globalSales = globalSales;
    }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public double getNaSales() { return naSales; }
    public void setNaSales(double naSales) { this.naSales = naSales; }

    public double getEuSales() { return euSales; }
    public void setEuSales(double euSales) { this.euSales = euSales; }

    public double getJpSales() { return jpSales; }
    public void setJpSales(double jpSales) { this.jpSales = jpSales; }

    public double getOtherSales() { return otherSales; }
    public void setOtherSales(double otherSales) { this.otherSales = otherSales; }

    public double getGlobalSales() { return globalSales; }
    public void setGlobalSales(double globalSales) { this.globalSales = globalSales; }

    @Override
    public String toString() {
        return String.format("Game{rank=%d, name='%s', platform='%s', year=%d, " +
                        "genre='%s', publisher='%s', globalSales=%.2f}",
                rank, name, platform, year, genre, publisher, globalSales);
    }
}