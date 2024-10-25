package org.dwescbm;

import java.sql.*;

public class HogwartsServiceQueries {

    // 1. Consulta de estudiantes y sus casas
    public static void getStudentsAndHouses(Connection connection) throws SQLException {
        String query = "SELECT e.nombre, e.apellido, c.nombre_casa FROM Estudiante e JOIN Casa c ON e.id_casa = c.id_casa";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nEstudiantes y sus casas:");
            while (rs.next()) {
                System.out.printf("Estudiante: %s %s - Casa: %s%n", rs.getString("nombre"), rs.getString("apellido"), rs.getString("nombre_casa"));
            }
        }
    }

    // 2. Consulta de estudiantes y sus mascotas
    public static void getStudentsAndPets(Connection connection) throws SQLException {
        String query = "SELECT e.nombre, e.apellido, COALESCE(m.nombre_mascota, 'Sin Mascota') /* COALESCE crea si no existe para que no tire null */ AS mascota FROM Estudiante e LEFT JOIN Mascota m ON e.id_estudiante = m.id_estudiante";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nEstudiantes y sus mascotas:");
            while (rs.next()) {
                System.out.printf("Estudiante: %s %s - Mascota: %s%n", rs.getString("nombre"), rs.getString("apellido"), rs.getString("mascota"));
            }
        }
    }

    // 3. Consulta de estudiantes con mascotas
    public static void getStudentsWithPets(Connection connection) throws SQLException {
        String query = "SELECT e.nombre, e.apellido, m.nombre_mascota FROM Estudiante e JOIN Mascota m ON e.id_estudiante = m.id_estudiante";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nEstudiantes con mascotas:");
            while (rs.next()) {
                System.out.printf("Estudiante: %s %s - Mascota: %s%n", rs.getString("nombre"), rs.getString("apellido"), rs.getString("nombre_mascota"));
            }
        }
    }

    // 4. Consulta de estudiantes sin mascotas
    public static void getStudentsWithoutPets(Connection connection) throws SQLException {
        String query = "SELECT e.nombre, e.apellido FROM Estudiante e LEFT JOIN Mascota m ON e.id_estudiante = m.id_estudiante WHERE m.id_estudiante IS NULL";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nEstudiantes sin mascotas:");
            while (rs.next()) {
                System.out.printf("Estudiante: %s %s%n", rs.getString("nombre"), rs.getString("apellido"));
            }
        }
    }

    // 5. Notas promedio por estudiante
    public static void getAverageGradesPerStudent(Connection connection) throws SQLException {
        String query = "SELECT e.nombre, e.apellido, AVG(ea.calificacion) AS promedio FROM Estudiante e JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante GROUP BY e.id_estudiante";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nNotas promedio por estudiante:");
            while (rs.next()) {
                System.out.printf("Estudiante: %s %s - Promedio: %.2f%n", rs.getString("nombre"), rs.getString("apellido"), rs.getDouble("promedio"));
            }
        }
    }

    // 6. Número de estudiantes en quinto año por casa
    public static void getStudentsInFifthYear(Connection connection) throws SQLException {
        String query = "SELECT c.nombre_casa, COUNT(e.id_estudiante) AS cantidad FROM Estudiante e JOIN Casa c ON e.id_casa = c.id_casa WHERE e.año_curso = 5 GROUP BY c.nombre_casa";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nNúmero de estudiantes en quinto año por casa:");
            while (rs.next()) {
                System.out.printf("Casa: %s - Estudiantes en 5to año: %d%n", rs.getString("nombre_casa"), rs.getInt("cantidad"));
            }
        }
    }

    // 7. Consulta de las mejores calificaciones en una asignatura específica
    public static void getBestGradesForSubject(Connection connection, String subjectName) throws SQLException {
        String query = "SELECT e.nombre, e.apellido, ea.calificacion FROM Estudiante e JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante JOIN Asignatura a ON ea.id_asignatura = a.id_asignatura WHERE a.nombre_asignatura = ? ORDER BY ea.calificacion DESC LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subjectName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.printf("Mejor estudiante en %s: %s %s - Calificación: %.2f%n", subjectName, rs.getString("nombre"), rs.getString("apellido"), rs.getDouble("calificacion"));
                } else {
                    System.out.println("No se encontró la asignatura o no hay calificaciones.");
                }
            }
        }
    }

    // 8. Promedio de calificaciones por casa en una asignatura
    public static void getAverageGradeByHouseForSubject(Connection connection, String subjectName) throws SQLException {
        String query = "SELECT c.nombre_casa, AVG(ea.calificacion) AS promedio FROM Estudiante e JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante JOIN Asignatura a ON ea.id_asignatura = a.id_asignatura JOIN Casa c ON e.id_casa = c.id_casa WHERE a.nombre_asignatura = ? GROUP BY c.nombre_casa";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subjectName);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.printf("\nPromedio de calificaciones por casa en la asignatura %s:%n", subjectName);
                while (rs.next()) {
                    System.out.printf("Casa: %s - Promedio: %.2f%n", rs.getString("nombre_casa"), rs.getDouble("promedio"));
                }
            }
        }
    }

    // 9. Incrementar calificaciones un 10% para estudiantes en su último año
    public static void updateFinalYearGrades(Connection connection) throws SQLException {
        String query = "UPDATE Estudiante_Asignatura SET calificacion = calificacion * 1.10 WHERE id_estudiante IN (SELECT id_estudiante FROM Estudiante WHERE año_curso = 7)";
        try (Statement stmt = connection.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(query);
            System.out.printf("Se han actualizado las calificaciones de %d estudiantes del último año.%n", rowsUpdated);
        }
    }

    // 10. Eliminar asignaturas optativas de estudiantes con calificaciones inferiores a 5
    public static void deregisterStudentsWithLowGrades(Connection connection) throws SQLException {
        String query = "DELETE FROM Estudiante_Asignatura WHERE id_asignatura IN (SELECT id_asignatura FROM Asignatura WHERE obligatoria = FALSE) AND calificacion < 5";
        try (Statement stmt = connection.createStatement()) {
            int rowsDeleted = stmt.executeUpdate(query);
            System.out.printf("Se han eliminado %d asignaturas optativas de estudiantes con calificaciones inferiores a 5.%n", rowsDeleted);
        }
    }
}
