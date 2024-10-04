package org.dwescbm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class YoutuberService {

    private List<Youtuber> youtubers;

    public YoutuberService() {
        this.youtubers = new ArrayList<>();
    }

    public void loadData(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(",");
            String name = data[0];
            String startDate = data[1];
            int numVideos = Integer.parseInt(data[2]);
            int numFollowers = Integer.parseInt(data[3]);

            Youtuber youtuber = new Youtuber(name, startDate, numVideos, numFollowers);  // Sin usar ruta completa
            youtubers.add(youtuber);
        }
    }

    public Youtuber getYoutuberWithTheMostFollowers() {
        return youtubers.stream()
                .max(Comparator.comparingInt(Youtuber::getNumFollowers))
                .orElse(null);
    }

    public double getAverageNumVideos() {
        return youtubers.stream()
                .mapToInt(Youtuber::getNumVideos)
                .average()
                .orElse(0);
    }

    public List<Youtuber> getYoutubersOf2013() {
        return youtubers.stream()
                .filter(y -> y.getStartDate().contains("2013"))
                .collect(Collectors.toList());
    }

    public void printYoutubers(List<Youtuber> youtubers) {
        youtubers.forEach(youtuber -> {
            System.out.println("Youtuber: " + youtuber.getName());
            System.out.println("Fecha inicio: " + youtuber.getStartDate());
            System.out.println("Total vídeos: " + youtuber.getNumVideos());
            System.out.println("Total seguidores: " + youtuber.getNumFollowers());
            System.out.println();
        });
    }

    public void printYoutubersOf2013() {
        List<Youtuber> youtubersOf2013 = getYoutubersOf2013();
        printYoutubers(youtubersOf2013);
    }

    public List<Youtuber> getTop3YoutubersWithTheHighestIncome() {
        return youtubers.stream()
                .sorted(Comparator.comparingDouble(Youtuber::estimatedIncome).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public void printYoutubersIncome(List<Youtuber> topYoutubers) {
        topYoutubers.forEach(youtuber ->
                System.out.println("Youtuber: " + youtuber.getName() + ", Ingresos estimados: " + youtuber.estimatedIncome())
        );
    }

    public double calculateTotalIncome(List<Youtuber> topYoutubers) {
        return topYoutubers.stream()
                .mapToDouble(Youtuber::estimatedIncome)
                .sum();
    }

    public Map<String, List<Youtuber>> groupYoutubersByYear() {
        return youtubers.stream()
                .collect(Collectors.groupingBy(y -> y.getStartDate().substring(0, 4)));
    }

    public void printYoutubersGroupedByYear() {
        Map<String, List<Youtuber>> groupedByYear = groupYoutubersByYear();

        groupedByYear.forEach((year, youtubersList) -> {
            System.out.println("\nAño: " + year);
            printYoutubers(youtubersList);
        });
    }
}
