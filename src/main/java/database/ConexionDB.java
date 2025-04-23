package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    private static final String URL = "jdbc:sqlite:clientes.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void inicializar() {
        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INTEGER PRIMARY KEY," +
                    "razonSocial TEXT," +
                    "nombre TEXT," +
                    "celular TEXT," +
                    "direccion TEXT," +
                    "estado TEXT," +
                    "municipio TEXT," +
                    "cp TEXT," +
                    "email TEXT," +
                    "telefono TEXT" +
                    ")";
            stmt.execute(sql);
            System.out.println("Tabla 'clientes' verificada o creada.");
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos:");
            e.printStackTrace();
        }
    }
}
