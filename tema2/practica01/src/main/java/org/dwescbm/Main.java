package org.dwescbm;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        YoutuberService service = new YoutuberService();

        try {
            // Cargar datos del archivo CSV
            service.loadData("/home/cribusmar/Documents/DESA_SERVIDOR/dwescbm/tema02/practica01/src/main/resources/youtubers.csv");

            // Mostrar youtuber con más seguidores
            System.out.println("YOUTUBER CON MÁS SEGUIDORES: \n" + service.getYoutuberWithTheMostFollowers());

            // Mostrar media de videos
            System.out.println("\nMEDIA TOTAL DE VÍDEOS: \n" + service.getAverageNumVideos());

            // Mostrar youtubers de 2013
            System.out.println("\nYOUTUBERS DE 2013: ");
            service.printYoutubersOf2013();

            // Mostrar top 3 youtubers con mayores ingresos
            System.out.println("INGRESOS DE LOS 3 YOUTUBERS MAS EXITOSOS:");
            service.printYoutubersIncome(service.getTop3YoutubersWithTheHighestIncome());
            System.out.println("Suma de los ingresos del top 3: " + service.calculateTotalIncome(service.getTop3YoutubersWithTheHighestIncome()));

            // Agrupar youtubers por año
            System.out.println("\nYOUTUBERS AGRUPADOS POR AÑO: " );
            service.printYoutubersGroupedByYear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
