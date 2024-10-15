<<<<<<< HEAD
package org.dwescbm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PokemonService {
    public static List<Pokemon> readAPI(){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        List<Pokemon> pokemons;
        try {
            JsonNode rootNode = objectMapper.readTree(new URL("https://pokeapi.co/api/v2/pokemon"));
            pokemons = objectMapper.readValue(rootNode.get("results").traverse(), new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pokemons;
    }
    public static List<Ability> getPokemoAbilities(Pokemon pokemon) {
        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
        List<Ability> pokemonAbilities = new ArrayList<>();
        try {
            JsonNode rootNode = om.readTree(new URL(pokemon.getUrl()));
            JsonNode abilitiesArray = om.readValue(rootNode.get("abilities").traverse(), new TypeReference<>() {});
            if (abilitiesArray.isArray()) {
                for (JsonNode abilityNode : abilitiesArray) {
                    JsonNode abilityObject = abilityNode.get("ability");
                    String name = abilityObject.get("name").asText();
                    String url = abilityObject.get("url").asText();

                    Ability ability = new Ability(name, url);
                    pokemonAbilities.add(ability);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pokemonAbilities;
    }

    public static String askName(Scanner in){
        System.out.println("Nombre del pokemon: ");
        return in.next();
    }
    public static Pokemon findPokemon(List<Pokemon> pokemonsList, Scanner in){
        Pokemon pokemon = new Pokemon();
        String nameToFind = askName(in);
        for (Pokemon p : pokemonsList) {
            if (p.getName().equalsIgnoreCase(nameToFind)) pokemon = p;
        }
        return pokemon;
    }
    public static void showPokemonsWithAbilities(List<Pokemon> pokemonList){
        for (Pokemon p : pokemonList) {
            System.out.println(p.toString());
            List<Ability> pokemonAbilities = getPokemoAbilities(p);
            pokemonAbilities.forEach(System.out::println);
        }
    }

    public static String askAbility(Scanner in){
        System.out.println("Que habilidad quieres: ");
        return in.next();
    }
    public static void showPokemonsGroupByAbilities(List<Pokemon> pokemonList, String abilityToFind) {
        List<Pokemon> pokemonsWithDesiredAbility = new ArrayList<>();

        for (Pokemon p : pokemonList) {
            List<Ability> pAbilities = getPokemoAbilities(p);
            for (Ability a : pAbilities) {
                if (a.getName().equalsIgnoreCase(abilityToFind)) {
                    pokemonsWithDesiredAbility.add(p);
                }
            }
        }

        if (pokemonsWithDesiredAbility.isEmpty()) {
            System.out.println("\nNo hay ningún pokemon con habilidad: " + abilityToFind);
        } else {
            System.out.println("\nPokemon/s con habilidad: " + abilityToFind);
            for (Pokemon p : pokemonsWithDesiredAbility){
                System.out.println("    · " + p.getName());
            }
        }
    }
}
=======
package org.dwescbm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PokemonService {
    public static List<Pokemon> readAPI(){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        List<Pokemon> pokemons;
        try {
            JsonNode rootNode = objectMapper.readTree(new URL("https://pokeapi.co/api/v2/pokemon"));
            pokemons = objectMapper.readValue(rootNode.get("results").traverse(), new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pokemons;
    }
    public static List<Ability> getPokemoAbilities(Pokemon pokemon) {
        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
        List<Ability> pokemonAbilities = new ArrayList<>();
        try {
            JsonNode rootNode = om.readTree(new URL(pokemon.getUrl()));
            JsonNode abilitiesArray = om.readValue(rootNode.get("abilities").traverse(), new TypeReference<>() {});
            if (abilitiesArray.isArray()) {
                for (JsonNode abilityNode : abilitiesArray) {
                    JsonNode abilityObject = abilityNode.get("ability");
                    String name = abilityObject.get("name").asText();
                    String url = abilityObject.get("url").asText();

                    Ability ability = new Ability(name, url);
                    pokemonAbilities.add(ability);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pokemonAbilities;
    }

    public static String askName(Scanner in){
        System.out.println("Nombre del pokemon: ");
        return in.next();
    }
    public static Pokemon findPokemon(List<Pokemon> pokemonsList, Scanner in){
        Pokemon pokemon = new Pokemon();
        String nameToFind = askName(in);
        for (Pokemon p : pokemonsList) {
            if (p.getName().equalsIgnoreCase(nameToFind)) pokemon = p;
        }
        return pokemon;
    }
    public static void showPokemonsWithAbilities(List<Pokemon> pokemonList){
        for (Pokemon p : pokemonList) {
            System.out.println(p.toString());
            List<Ability> pokemonAbilities = getPokemoAbilities(p);
            pokemonAbilities.forEach(System.out::println);
        }
    }

    public static String askAbility(Scanner in){
        System.out.println("Que habilidad quieres: ");
        return in.next();
    }
    public static void showPokemonsGroupByAbilities(List<Pokemon> pokemonList, String abilityToFind) {
        List<Pokemon> pokemonsWithDesiredAbility = new ArrayList<>();

        for (Pokemon p : pokemonList) {
            List<Ability> pAbilities = getPokemoAbilities(p);
            for (Ability a : pAbilities) {
                if (a.getName().equalsIgnoreCase(abilityToFind)) {
                    pokemonsWithDesiredAbility.add(p);
                }
            }
        }

        if (pokemonsWithDesiredAbility.isEmpty()) {
            System.out.println("\nNo hay ningún pokemon con habilidad: " + abilityToFind);
        } else {
            System.out.println("\nPokemon/s con habilidad: " + abilityToFind);
            for (Pokemon p : pokemonsWithDesiredAbility){
                System.out.println("    · " + p.getName());
            }
        }
    }
}
>>>>>>> a2359d1 (Listo)
