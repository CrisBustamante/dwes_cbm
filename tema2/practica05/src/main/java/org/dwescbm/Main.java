package org.dwescbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String DatabaseUrl = "jdbc:postgresql://practica05.chiqqxoyuuaa.us-east-1.rds.amazonaws.com:5432/f12006";
        String user = "postgres";
        String password = "practica05bbdd";

        try (Connection conection = DriverManager.getConnection(DatabaseUrl, user, password)){
            System.out.println("CONECTADO A LA BASE DE DATOS");
        } catch (SQLException exc01) {
            System.out.println("ERROR01: NO SE HA PODIDO CONECTAR A LA BASE DE DATOS");
            System.err.println(exc01.getClass().getName() + ": " + exc01.getMessage());
        }
    }
}