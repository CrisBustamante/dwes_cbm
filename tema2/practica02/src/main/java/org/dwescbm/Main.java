package org.dwescbm;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AnimalService service = new AnimalService();
        Path animalXml = Paths.get("tema2/practica02/src/main/resources/Animals.xml");

        try {
            // Cargar lista de animales desde archivo XML
            AnimalShelter animalShelter = service.readXmlToList(animalXml);

            // Mostrar menú principal
            menu(service, animalShelter, animalXml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menu(AnimalService service, AnimalShelter animalShelter, Path animalXml) throws IOException {
        Scanner in = new Scanner(System.in);
        int option;

        do {
            showMenu();
            option = in.nextInt();

            switch (option) {
                case 1:
                    animalShelter = service.readXmlToList(animalXml);
                    break;

                case 2:
                    // Guardar lista de animales en el fichero
                    service.writeListToXml(animalShelter, animalXml);
                    break;

                case 3:
                    // Añadir animal
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
