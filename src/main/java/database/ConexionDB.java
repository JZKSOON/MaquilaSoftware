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

            // Tabla de clientes

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
                );
            """;
            stmt.execute(sqlClientes);

            // Tabla de maquilas

            String sqlMaquilas = """
                CREATE TABLE IF NOT EXISTS maquilas (
                    idM INTEGER PRIMARY KEY,
                    NombreMaquila TEXT,
                    AreaMaquila TEXT,
                    nombreM TEXT,
                    celularM TEXT,
                    direccionM TEXT,
                    estadoM TEXT,
                    municipioM TEXT,
                    cpM TEXT,
                    email TEXT,
                    telefono TEXT
                );
            """;
            stmt.execute(sqlMaquilas);

            // Tabla de cortes

            String sqlCortes = """
                CREATE TABLE IF NOT EXISTS cortes (
                    idCortes INTEGER PRIMARY KEY,
                    NumCorte TEXT,
                    Cantidad TEXT,
                    Marca TEXT,
                    Linea TEXT,
                    TipoTela TEXT
                );
            """;
            stmt.execute(sqlCortes);

            // Tabla de Mesa de corte

            String sqlCorte = """
                CREATE TABLE IF NOT EXISTS corte(
                    idCorte INTEGER PRIMARY KEY,
                    CorteMC TEXT,
                    EntregaEncogimientos TEXT,
                    Liberacion TEXT,
                    Fecha TEXT,
                    Precio TEXT,
                    Cantidad TEXT
                );
            """;
            stmt.execute(sqlCorte);

            //Tabla de Bordado

            String sqlBordado = """
            CREATE TABLE IF NOT EXISTS bordado(
            idBordado INTEGER PRIMARY KEY,
            MaquileroBordado TEXT,
            FechaEntradaBordado TEXT,
            FechaSalidaBordado TEXT,
            CostoMaquilaBordado TEXT,
            CantidadEntregada TEXT
                );
            """;
            stmt.execute(sqlBordado);

            //Tabla de Confeccion

            String sqlConfeccion = """
            CREATE TABLE IF NOT EXISTS confeccion(
            idConfeccion           INTEGER PRIMARY KEY,
            MaquileroConfeccion    TEXT,
            PrecioConfeccion       TEXT,
            CantidadAsignada       TEXT,
            FechaEntregaCorte      TEXT
            );
            """;
            stmt.execute(sqlConfeccion);

            //Tabla de Lavanderia

            String sqlLavanderia = """
            CREATE TABLE IF NOT EXISTS lavanderia(
            idLavanderia           INTEGER PRIMARY KEY,
            MaquileroLavanderia    TEXT,
            PrecioLavanderia       TEXT
            );
            """;
            stmt.execute(sqlLavanderia);

            //Tabla de empaque

            String sqlEmpaque = """
            CREATE TABLE IF NOT EXISTS empaque(
            idEmpaque           INTEGER PRIMARY KEY,
            maquileroEmpaque    TEXT,
            precioEmpaque       TEXT,
            cantidadEntregadaEC TEXT,
            restosPrimerasEntregadas TEXT,
            segundasEntregadas TEXT
            );
            """;
            stmt.execute(sqlEmpaque);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}