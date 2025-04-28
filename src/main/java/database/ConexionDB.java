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
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {

            // 1) Tabla de clientes
            String sqlClientes = """
                CREATE TABLE IF NOT EXISTS clientes (
                    id INTEGER PRIMARY KEY,
                    razonSocial TEXT,
                    nombre TEXT,
                    celular TEXT,
                    direccion TEXT,
                    estado TEXT,
                    municipio TEXT,
                    cp TEXT,
                    email TEXT,
                    telefono TEXT
                )
                """;
            stmt.execute(sqlClientes);
            System.out.println("Tabla 'clientes' verificada o creada.");

            // 2) Tabla de maquilas
            String sqlMaquilas = """
                CREATE TABLE IF NOT EXISTS maquilas (
                    idM INTEGER PRIMARY KEY,
                    NombreMaquila TEXT,
                    nombreM TEXT,
                    celularM TEXT,
                    direccionM TEXT,
                    estadoM TEXT,
                    municipioM TEXT,
                    cpM TEXT,
                    email TEXT,
                    telefono TEXT
                )
                """;
            stmt.execute(sqlMaquilas);
            System.out.println("Tabla 'maquilas' verificada o creada.");

            // 2) Tabla de cortes
            String sqlCortes = """
                CREATE TABLE IF NOT EXISTS cortes (
                    idCortes INTEGER PRIMARY KEY,
                    NumCorte TEXT,
                    Cantidad TEXT,
                    Marca TEXT,
                    Linea TEXT,
                    FechTela TEXT,
                    LlegTela TEXT,
                    TipoTela TEXT,
                    Trazo TEXT,
                )
                """;
            stmt.execute(sqlCortes);
            System.out.println("Tabla 'cortes' verificada o creada.");

        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos:");
            e.printStackTrace();
        }
    }
}
