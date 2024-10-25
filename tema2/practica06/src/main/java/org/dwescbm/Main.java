package org.dwescbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:postgresql://practica05.chiqqxoyuuaa.us-east-1.rds.amazonaws.com:5432/f12006";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "practica05bbdd";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("CONECTADO A LA BASE DE DATOS EXITOSAMENTE.");

            int option;
            do {
                printMenu();
                option = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (option) {
                    case 1:
                        HogwartsServiceQueries.getStudentsAndHouses(connection);
                        break;
                    case 2:
                        HogwartsServiceQueries.getStudentsAndPets(connection);
                        break;
                    case 3:
                        HogwartsServiceQueries.getStudentsWithPets(connection);
                        break;
                    case 4:
                        HogwartsServiceQueries.getStudentsWithoutPets(connection);
                        break;
                    case 5:
                        HogwartsServiceQueries.getAverageGradesPerStudent(connection);
                        break;
                    case 6:
                        HogwartsServiceQueries.getStudentsInFifthYear(connection);
                        break;
                    case 7:
                        System.out.print("Introduce el nombre de la asignatura: ");
                        String subjectName = scanner.nextLine();
                        HogwartsServiceQueries.getBestGradesForSubject(connection, subjectName);
                        break;
                    case 8:
                        System.out.print("Introduce el nombre de la asignatura: ");
                        String subjectNameForAvg = scanner.nextLine();
                        HogwartsServiceQueries.getAverageGradeByHouseForSubject(connection, subjectNameForAvg);
                        break;
                    case 9:
                        HogwartsServiceQueries.updateFinalYearGrades(connection);
                        break;
                    case 10:
                        HogwartsServiceQueries.deregisterStudentsWithLowGrades(connection);
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } while (option != 0);

        } catch (SQLException ex) {
            System.err.println("Error al conectar a la base de datos.");
            System.err.println(ex.getMessage());
        }
    }

    // Metodo para imprimir el menú
    private static void printMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Consulta de estudiantes y sus casas");
        System.out.println("2. Consulta de estudiantes y sus mascotas");
        System.out.println("3. Consulta de estudiantes con mascotas");
        System.out.println("4. Consulta de estudiantes sin mascotas");
        System.out.println("5. Notas promedio por estudiante");
        System.out.println("6. Número de estudiantes en quinto año por casa");
        System.out.println("7. Consulta de las mejores calificaciones en una asignatura específica");
        System.out.println("8. Promedio de calificaciones por casa en una asignatura");
        System.out.println("9. Incrementar las calificaciones un 10% para los estudiantes en su último año");
        System.out.println("10. Desmatricular estudiantes: eliminar asignaturas optativas de los estudiantes con calificaciones inferiores a 5");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }
}
