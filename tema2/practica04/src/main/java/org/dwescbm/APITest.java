package org.dwescbm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class APITest {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce el Challenge Rating para buscar monstruos (ejemplo: 5): ");
        String challengeRating = scanner.nextLine();

        // Llamar al metodo que busca monstruos por Challenge Rating
        List<Monster> monsters = findMonstersByChallengeRating(objectMapper, challengeRating);

        // Mostrar los monstruos obtenidos
        if (monsters != null && !monsters.isEmpty()) {
            monsters.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron monstruos con Challenge Rating: " + challengeRating);
        }
    }

    private static List<Monster> findMonstersByChallengeRating(ObjectMapper objectMapper, String challengeRating) {
        try {
            // URL de la API de D&D 5e para obtener todos los monstruos
            String url = "https://www.dnd5eapi.co/api/monsters";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer los resultados si la respuesta es 200 (OK)
                JsonNode rootNode = objectMapper.readTree(connection.getInputStream());
                JsonNode resultsNode = rootNode.get("results");

                if (resultsNode != null && resultsNode.isArray()) {
                    List<Monster> monsters = objectMapper.readValue(resultsNode.traverse(), new TypeReference<List<Monster>>() {});
                    // Filtrar por Challenge Rating
                    List<Monster> filteredMonsters = new ArrayList<>();
                    for (Monster monster : monsters) {
                        if (monster.getChallengeRating().equals(challengeRating)) {
                            filteredMonsters.add(monster);
                        }
                    }
                    return filteredMonsters;
                } else {
                    throw new RuntimeException("No se encontraron resultados en la respuesta.");
                }
            } else {
                System.err.println("Error: El servidor respondió con el código " + responseCode);
            }

        } catch (IOException e) {
            System.err.println("Error al conectarse con la API de D&D 5e: " + e.getMessage());
        }
        return null;
    }
}
