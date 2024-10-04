package org.dwescbm;

public class Youtuber {
    private String name;
    private String startDate;
    private int numVideos;
    private int numFollowers;

    public Youtuber(String name, String startDate, int numVideos, int numFollowers) {
        this.name = name;
        this.startDate = startDate;
        this.numVideos = numVideos;
        this.numFollowers = numFollowers;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getNumVideos() {
        return numVideos;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public double estimatedIncome() {
        return (numVideos * numFollowers / 2) * 0.002;
    }

    @Override
    public String toString() {
        return "Youtuber: " + name +
                ", \nFecha inicio: " + startDate +
                ", \nTotal vídeos: " + numVideos +
                ", \nTotal seguidores: " + numFollowers + ";";
    }
}

