package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ContectarseBaseDatos {
    public static void main(String[] args) {
        String url = "";
        String user = "";
        String password = "";

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
