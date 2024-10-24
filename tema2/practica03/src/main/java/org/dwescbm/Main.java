package org.dwescbm;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AnimalService service = new AnimalService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce el path (FROM REPOSITORY ROOT) del archivo de datos (JSON o XML): ");
        String filePath = scanner.nextLine();
        Path animalDataPath = Paths.get(filePath);

        try {
            // Cargar la lista de animales del archivo (JSON o XML)
            AnimalShelter animalShelter = service.loadAnimalData(animalDataPath);

            // Mostrar el menú de opciones
            menu(service, animalShelter, animalDataPath, scanner);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menu(AnimalService service, AnimalShelter animalShelter, Path animalDataPath, Scanner in) throws IOException {
        int option;

        do {
            showMenu();
            option = in.nextInt();

            switch (option) {
                case 1:
                    animalShelter = service.loadAnimalData(animalDataPath);
                    break;
                case 2:
                    service.saveAnimalData(animalShelter, animalDataPath);
                    break;
                case 3:
                    service.addAnimal(animalShelter, in);
                    break;
                case 4:
                    service.removeAnimal(animalShelter, in);
                    break;
                case 5:
                    System.out.println(service.consultAnimal(animalShelter, in));
                    break;
                case 6:
                    service.showAllAnimals(animalShelter);
                    break;
                case 7:
                    System.out.println("\nHas salido del programa.");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción del 1 al 7.");
                    break;
            }
        } while (option != 7);
    }

    public static void showMenu() {
        System.out.println("\nMENU PROTECTORA ANIMALES:");
        System.out.println("1. Cargar lista de animales desde el fichero");
        System.out.println("2. Guardar lista de animales en el fichero");
        System.out.println("3. Añadir animal");
        System.out.println("4. Borrar animal");
        System.out.println("5. Consultar animal");
        System.out.println("6. Mostrar todos los animales");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }
}
