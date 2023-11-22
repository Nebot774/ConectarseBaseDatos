package org.example;

import java.sql.*;


import java.sql.*;
import java.time.LocalDate;

public class ConectarseBaseDatos {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://accessodatosdb.cpfwt1hqt9da.us-east-1.rds.amazonaws.com:5432/f12006";
        String user = "postgres";
        String password = "Secreto!2023";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            System.out.println("Conectado a la base de datos");

            String sqlInsertarConstructor = "INSERT INTO constructors (constructorid, constructorref, name, nationality, url) "
                    + "VALUES (?, ?, ?, ?, ?) ON CONFLICT (constructorid) DO NOTHING RETURNING constructorid;";
            int constructorId = -1;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsertarConstructor, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, 15);
                pstmt.setString(2, "seat");
                pstmt.setString(3, "Seat F1");
                pstmt.setString(4, "España");
                pstmt.setString(5, "http://seatf1.example.com");

                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
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

            if (constructorId != -1) {
                String sqlInsertDriver = "INSERT INTO drivers (driverid, code, forename, surname, dob, nationality, constructorid, url) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                try (PreparedStatement pstmtDriver = conn.prepareStatement(sqlInsertDriver)) {
                    // Carlos Sainz
                    pstmtDriver.setInt(1, 39);
                    pstmtDriver.setString(2, "CSAI");
                    pstmtDriver.setString(3, "Carlos");
                    pstmtDriver.setString(4, "Sainz");
                    LocalDate fechaSainz = LocalDate.parse("1994-09-01");
                    pstmtDriver.setDate(5, Date.valueOf(fechaSainz)); // Convertir la fecha de String a java.sql.Date
                    pstmtDriver.setString(6, "Spanish");
                    pstmtDriver.setInt(7, constructorId);
                    pstmtDriver.setString(8, "http://carlossainz.example.com");
                    pstmtDriver.executeUpdate();
                    System.out.println("Carlos Sainz insertado con éxito.");

                    // Manuel Aloma
                    pstmtDriver.setInt(1, 40);
                    pstmtDriver.setString(2, "ALM");
                    pstmtDriver.setString(3, "Manuel");
                    pstmtDriver.setString(4, "Aloma");
                    LocalDate fechaAloma = LocalDate.parse("1990-01-01");
                    pstmtDriver.setDate(5, Date.valueOf(fechaAloma)); // Convertir la fecha de String a java.sql.Date
                    pstmtDriver.setString(6, "Spanish");
                    pstmtDriver.setInt(7, constructorId);
                    pstmtDriver.setString(8, "http://manuelaloma.example.com");
                    pstmtDriver.executeUpdate();
                    System.out.println("Manuel Aloma insertado con éxito.");

                    conn.commit();
                } catch (SQLException e) {
                    System.out.println("Error al insertar pilotos: " + e.getMessage());
                    conn.rollback();
                    // No lanzamos la excepción aquí para permitir que se cierre la conexión
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al realizar operaciones: " + e.getMessage());
            e.printStackTrace();
        } finally {
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

