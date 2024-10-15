<<<<<<< HEAD
package org.dwescbm;

import java.util.List;
import java.util.Scanner;

public class Main extends PokemonService {
    public static void main(String[] args) {
        menu();
    }
    public static void showMenu(){
        System.out.print("\n   -*- MENÚ -*-    \n" +
                "1. Ver un pokemon.\n" +
                "2. Ver stats de habilidades de un pokemon.\n" +
                "3. Ver listado de pokemon (nombre + url).\n" +
                "4. Ver listado de pokemon con habilidades.\n" +
                "5. Buscar pokemon por habilidad.\n" +
                "6. Salir.\n" +
                "Que quieres hacer? ");
    }
    public static void menu(){
        Scanner in = new Scanner(System.in);
        int option;
        List<Pokemon> pokemonList = readAPI();
        do {
            showMenu();
            option = in.nextInt();
            switch (option){
                case 1 -> System.out.println(findPokemon(pokemonList, in).toString());
                case 2 -> System.out.println(getPokemoAbilities(findPokemon(pokemonList, in)));
                case 3 -> pokemonList.forEach(System.out::println);
                case 4 -> showPokemonsWithAbilities(pokemonList);
                case 5 -> showPokemonsGroupByAbilities(pokemonList, askAbility(in));
                case 6 -> System.out.println("\nHas salido del programa.\n");
                default -> System.out.println("Por favor selecciona una opción disponible.");
            }
        } while (option != 6);
    }
=======
package org.dwescbm;

import java.util.List;
import java.util.Scanner;

public class Main extends PokemonService {
    public static void main(String[] args) {
        menu();
    }
    public static void showMenu(){
        System.out.print("\n   -*- MENÚ -*-    \n" +
                "1. Ver un pokemon.\n" +
                "2. Ver stats de habilidades de un pokemon.\n" +
                "3. Ver listado de pokemon (nombre + url).\n" +
                "4. Ver listado de pokemon con habilidades.\n" +
                "5. Buscar pokemon por habilidad.\n" +
                "6. Salir.\n" +
                "Que quieres hacer? ");
    }
    public static void menu(){
        Scanner in = new Scanner(System.in);
        int option;
        List<Pokemon> pokemonList = readAPI();
        do {
            showMenu();
            option = in.nextInt();
            switch (option){
                case 1 -> System.out.println(findPokemon(pokemonList, in).toString());
                case 2 -> System.out.println(getPokemoAbilities(findPokemon(pokemonList, in)));
                case 3 -> pokemonList.forEach(System.out::println);
                case 4 -> showPokemonsWithAbilities(pokemonList);
                case 5 -> showPokemonsGroupByAbilities(pokemonList, askAbility(in));
                case 6 -> System.out.println("\nHas salido del programa.\n");
                default -> System.out.println("Por favor selecciona una opción disponible.");
            }
        } while (option != 6);
    }
>>>>>>> a2359d1 (Listo)
}