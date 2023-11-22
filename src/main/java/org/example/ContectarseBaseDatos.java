package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ContectarseBaseDatos {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://accessodatosdb.cpfwt1hqt9da.us-east-1.rds.amazonaws.com:5432/f12006";
        String user = "postgres";
        String password = "Secreto!2023";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado a la base de datos");
            // Aquí puedes ejecutar tus consultas


        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos");
            e.printStackTrace();
        }
    }
}
