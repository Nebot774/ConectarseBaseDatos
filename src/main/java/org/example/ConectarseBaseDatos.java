package org.example;

import java.sql.*;


import java.sql.*;
import java.time.LocalDate;

public class ConectarseBaseDatos {
    public static void main(String[] args) {
        // Definición de las credenciales de la base de datos
        String url = "..."; // URL de conexión a la base de datos
        String user = "postgres"; // Usuario de la base de datos
        String password = "Secreto!2023"; // Contraseña de la base de datos

        Connection conn = null;
        try {
            // Establecer la conexión con la base de datos
            conn = DriverManager.getConnection(url, user, password);
            // Desactivar auto-commit para manejar transacciones manualmente
            conn.setAutoCommit(false);
            System.out.println("Conectado a la base de datos");

            // Preparar la sentencia SQL para insertar en la tabla constructors
            String sqlInsertarConstructor = "...";
            int constructorId = -1;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsertarConstructor, Statement.RETURN_GENERATED_KEYS)) {
                // Establecer los valores de los parámetros en la sentencia
                pstmt.setInt(1, 15); // ... otros pstmt.setString/setInt ...

                // Ejecutar la sentencia y obtener el número de filas afectadas
                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    // Recuperar las claves generadas (ID del constructor)
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            constructorId = rs.getInt(1);
                            System.out.println("Equipo insertado con éxito. ID: " + constructorId);
                        }
                    }
                } else {
                    System.out.println("El equipo no se insertó o ya existía.");
                }
            }

            // Continuar con la inserción en la tabla drivers si la inserción anterior fue exitosa
            if (constructorId != -1) {
                // Preparar la sentencia SQL para insertar en la tabla drivers
                String sqlInsertDriver = "...";
                try (PreparedStatement pstmtDriver = conn.prepareStatement(sqlInsertDriver)) {
                    // Insertar el primer piloto
                    pstmtDriver.setInt(1, 39); // ... otros pstmt.setString/setInt ...
                    pstmtDriver.executeUpdate();
                    System.out.println("Carlos Sainz insertado con éxito.");

                    // Insertar el segundo piloto
                    pstmtDriver.setInt(1, 40); // ... otros pstmt.setString/setInt ...
                    pstmtDriver.executeUpdate();
                    System.out.println("Manuel Aloma insertado con éxito.");

                    // Confirmar todas las operaciones realizadas en la transacción
                    conn.commit();
                } catch (SQLException e) {
                    // Manejar errores al insertar pilotos y hacer rollback de la transacción
                    System.out.println("Error al insertar pilotos: " + e.getMessage());
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            // Manejar errores generales de conexión o de operaciones en la base de datos
            System.out.println("Error al conectar a la base de datos o al realizar operaciones: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Asegurarse de cerrar la conexión a la base de datos
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


