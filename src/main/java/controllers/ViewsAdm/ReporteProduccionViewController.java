package controllers.ViewsAdm;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import model.ReporteProduccion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

public class ReporteProduccionViewController {

    @FXML private TableView<ReporteProduccion> reporteTable;

    @FXML private TableColumn<ReporteProduccion, String> clienteColumn;
    @FXML private TableColumn<ReporteProduccion, String> corteColumn;
    @FXML private TableColumn<ReporteProduccion, String> cantidadProgramadaColumn;
    @FXML private TableColumn<ReporteProduccion, String> marcaColumn;
    @FXML private TableColumn<ReporteProduccion, String> lineaColumn;
    @FXML private TableColumn<ReporteProduccion, String> tipoTelaColumn;
    @FXML private TableColumn<ReporteProduccion, String> mesaCorteColumn;
    @FXML private TableColumn<ReporteProduccion, String> entregaEncogimientosColumn;
    @FXML private TableColumn<ReporteProduccion, String> liberacionColumn;
    @FXML private TableColumn<ReporteProduccion, String> fechaCorteColumn;
    @FXML private TableColumn<ReporteProduccion, String> cantidadCortadaColumn;
    @FXML private TableColumn<ReporteProduccion, String> bordadoColumn;
    @FXML private TableColumn<ReporteProduccion, String> confeccionColumn;
    @FXML private TableColumn<ReporteProduccion, String> cantidadAsignadaConfeccionColumn;
    @FXML private TableColumn<ReporteProduccion, String> cantidadEntregadaConfeccionColumn;
    @FXML private TableColumn<ReporteProduccion, String> faltantesConfeccionColumn;
    @FXML private TableColumn<ReporteProduccion, String> porcentajeConfeccionColumn;
    @FXML private TableColumn<ReporteProduccion, String> lavanderiaColumn;
    @FXML private TableColumn<ReporteProduccion, String> cantidadAsignadaLavanderiaColumn;
    @FXML private TableColumn<ReporteProduccion, String> cantidadEntregadaLavanderiaColumn;
    @FXML private TableColumn<ReporteProduccion, String> faltantesLavanderiaColumn;
    @FXML private TableColumn<ReporteProduccion, String> porcentajeLavanderiaColumn;
    @FXML private TableColumn<ReporteProduccion, String> empaqueColumn;
    @FXML private TableColumn<ReporteProduccion, String> cantidadAsignadaEmpaqueColumn;
    @FXML private TableColumn<ReporteProduccion, String> cantidadEntregadaClientesColumn;
    @FXML private TableColumn<ReporteProduccion, String> restosPrimerasColumn;
    @FXML private TableColumn<ReporteProduccion, String> segundasEntregadasColumn;
    @FXML private TableColumn<ReporteProduccion, String> totalEmpaqueColumn;
    @FXML private TableColumn<ReporteProduccion, String> porcentajeEmpaqueColumn;

    private final ObservableList<ReporteProduccion> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // 1. Configurar columnas
        clienteColumn                    .setCellValueFactory(new PropertyValueFactory<>("cliente"));
        corteColumn                      .setCellValueFactory(new PropertyValueFactory<>("corte"));
        cantidadProgramadaColumn         .setCellValueFactory(new PropertyValueFactory<>("cantidadProgramada"));
        marcaColumn                      .setCellValueFactory(new PropertyValueFactory<>("marca"));
        lineaColumn                      .setCellValueFactory(new PropertyValueFactory<>("linea"));
        tipoTelaColumn                   .setCellValueFactory(new PropertyValueFactory<>("tipoTela"));
        mesaCorteColumn                  .setCellValueFactory(new PropertyValueFactory<>("mesaCorte"));
        entregaEncogimientosColumn       .setCellValueFactory(new PropertyValueFactory<>("entregaEncogimientos"));
        liberacionColumn                 .setCellValueFactory(new PropertyValueFactory<>("liberacion"));
        fechaCorteColumn                 .setCellValueFactory(new PropertyValueFactory<>("fechaCorte"));
        cantidadCortadaColumn            .setCellValueFactory(new PropertyValueFactory<>("cantidadCortada"));
        bordadoColumn                    .setCellValueFactory(new PropertyValueFactory<>("estadoBordado"));
        confeccionColumn                 .setCellValueFactory(new PropertyValueFactory<>("confeccion"));
        confeccionColumn                 .setCellValueFactory(new PropertyValueFactory<>("confeccion"));
        cantidadAsignadaConfeccionColumn .setCellValueFactory(new PropertyValueFactory<>("cantidadAsignadaConfeccion"));
        cantidadEntregadaConfeccionColumn.setCellValueFactory(new PropertyValueFactory<>("cantidadEntregadaConfeccion"));
        faltantesConfeccionColumn        .setCellValueFactory(new PropertyValueFactory<>("faltantesConfeccion"));
        porcentajeConfeccionColumn       .setCellValueFactory(new PropertyValueFactory<>("porcentajeConfeccion"));
        lavanderiaColumn                 .setCellValueFactory(new PropertyValueFactory<>("lavanderia"));
        cantidadAsignadaLavanderiaColumn .setCellValueFactory(new PropertyValueFactory<>("cantidadAsignadaLavanderia"));
        cantidadEntregadaLavanderiaColumn.setCellValueFactory(new PropertyValueFactory<>("cantidadEntregadaLavanderia"));
        faltantesLavanderiaColumn        .setCellValueFactory(new PropertyValueFactory<>("faltantesLavanderia"));
        porcentajeLavanderiaColumn       .setCellValueFactory(new PropertyValueFactory<>("porcentajeLavanderia"));
        empaqueColumn                    .setCellValueFactory(new PropertyValueFactory<>("empaque"));
        cantidadAsignadaEmpaqueColumn    .setCellValueFactory(new PropertyValueFactory<>("cantidadAsignadaEmpaque"));
        cantidadEntregadaClientesColumn  .setCellValueFactory(new PropertyValueFactory<>("cantidadEntregadaClientes"));
        restosPrimerasColumn             .setCellValueFactory(new PropertyValueFactory<>("restosPrimeras"));
        segundasEntregadasColumn         .setCellValueFactory(new PropertyValueFactory<>("segundasEntregadas"));
        totalEmpaqueColumn               .setCellValueFactory(new PropertyValueFactory<>("totalEmpaque"));
        porcentajeEmpaqueColumn          .setCellValueFactory(new PropertyValueFactory<>("porcentajeEmpaque"));

        // 2. Verificar si hay datos en las tablas
        verificarDatos();

        // 3. Cargar datos
        ObservableList<ReporteProduccion> tempData = generarReporte();

        // 5. Asignar datos a la tabla
        reporteTable.setItems(tempData);
    }



    private ObservableList<ReporteProduccion> generarReporte() {
        ObservableList<ReporteProduccion> lista = FXCollections.observableArrayList();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");


        // Consulta principal para obtener datos básicos
        String sql = """
        SELECT 
          cor.NumCorte as corte, 
          cor.Cantidad as cantidadProgramada,
          cor.Marca, 
          cor.Linea, 
          cor.TipoTela,
          co.EntregaEncogimientos,
          co.Liberacion,
          co.Fecha as fechaCorte,
          co.Cantidad as cantidadCortada
        FROM cortes cor
        LEFT JOIN corte co ON co.CorteMC = cor.NumCorte
    """;

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ReporteProduccion r = new ReporteProduccion();

                // Obtener datos básicos
                String numeroCorte = rs.getString("corte");
                r.setCorte(numeroCorte);
                r.setCantidadProgramada(rs.getString("cantidadProgramada"));
                r.setMarca(rs.getString("Marca"));
                r.setLinea(rs.getString("Linea"));
                r.setTipoTela(rs.getString("TipoTela"));
                r.setEntregaEncogimientos(rs.getString("EntregaEncogimientos"));
                // Formatear fecha de liberación
                if (rs.getObject("Liberacion") != null) {
                    java.util.Date fecha = new java.util.Date(rs.getLong("Liberacion"));
                    r.setLiberacion(formatoFecha.format(fecha));
                } else {
                    r.setLiberacion("");
                }

                // Formatear fecha de corte
                if (rs.getObject("fechaCorte") != null) {
                    java.util.Date fecha = new java.util.Date(rs.getLong("fechaCorte"));
                    r.setFechaCorte(formatoFecha.format(fecha));
                } else {
                    r.setFechaCorte("");
                }

                r.setCantidadCortada(rs.getString("cantidadCortada"));

                // Obtener el primer cliente disponible (si hay varios)
                try (Statement stmtCliente = conn.createStatement();
                     ResultSet rsCliente = stmtCliente.executeQuery("SELECT razonSocial FROM clientes LIMIT 1")) {

                    if (rsCliente.next()) {
                        r.setCliente(rsCliente.getString("razonSocial"));
                    } else {
                        r.setCliente("Sin cliente asignado");
                    }
                } catch (SQLException e) {
                    r.setCliente("Error al obtener cliente");
                    System.err.println("Error al obtener cliente: " + e.getMessage());
                }

                // Consultar datos reales de confección para el corte actual
                try (PreparedStatement psConfeccionDatos = conn.prepareStatement(
                        "SELECT CorteC, MaquileroConfeccion, CantidadAsignada, CantidadEntregada FROM confeccion WHERE CorteC = ?")) {

                    psConfeccionDatos.setString(1, numeroCorte);
                    System.out.println("Consultando datos de confección para corte: " + numeroCorte);

                    try (ResultSet rsConfeccionDatos = psConfeccionDatos.executeQuery()) {
                        if (rsConfeccionDatos.next()) {
                            // Obtener datos
                            String maquileroConfeccion = rsConfeccionDatos.getString("MaquileroConfeccion");
                            String cantAsignada = rsConfeccionDatos.getString("CantidadAsignada");
                            String cantEntregada = rsConfeccionDatos.getString("CantidadEntregada");

                            // Validar datos para evitar nulos
                            maquileroConfeccion = (maquileroConfeccion != null && !maquileroConfeccion.isEmpty())
                                    ? maquileroConfeccion
                                    : "Sin asignar";
                            cantAsignada = (cantAsignada != null && !cantAsignada.isEmpty()) ? cantAsignada : "0";
                            cantEntregada = (cantEntregada != null && !cantEntregada.isEmpty()) ? cantEntregada : "0";

                            System.out.println("Datos encontrados - Maquilero: " + maquileroConfeccion +
                                    ", Asignada: " + cantAsignada + ", Entregada: " + cantEntregada);

                            // Asignar valores
                            r.setConfeccion(maquileroConfeccion); // Establecer el nombre del maquilero
                            r.setCantidadAsignadaConfeccion(cantAsignada);
                            r.setCantidadEntregadaConfeccion(cantEntregada);

                            // Calcular faltantes
                            r.setFaltantesConfeccion(calcularFaltante(cantAsignada, cantEntregada));

                            // Calcular porcentaje
                            r.setPorcentajeConfeccion(calcularPorcentaje(cantAsignada, cantEntregada));
                        } else {
                            System.out.println("No se encontraron datos de confección para el corte: " + numeroCorte);

                            // Intentar obtener al menos el nombre de una maquila de confección (como antes)
                            try (PreparedStatement psMaquila = conn.prepareStatement(
                                    "SELECT NombreMaquila FROM maquilas WHERE AreaMaquila='Confeccion' LIMIT 1")) {

                                try (ResultSet rsMaquila = psMaquila.executeQuery()) {
                                    if (rsMaquila.next()) {
                                        r.setConfeccion(rsMaquila.getString("NombreMaquila"));
                                    } else {
                                        r.setConfeccion("Sin confección asignada");
                                    }
                                }
                            }

                            // Mantener los valores por defecto para los demás campos
                            r.setCantidadAsignadaConfeccion("0");
                            r.setCantidadEntregadaConfeccion("0");
                            r.setFaltantesConfeccion("0");
                            r.setPorcentajeConfeccion("0%");
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error al consultar datos de confección: " + e.getMessage());
                    e.printStackTrace();

                    // Intentar obtener al menos el nombre de una maquila de confección en caso de error
                    try (PreparedStatement psMaquila = conn.prepareStatement(
                            "SELECT NombreMaquila FROM maquilas WHERE AreaMaquila='Confeccion' LIMIT 1")) {

                        try (ResultSet rsMaquila = psMaquila.executeQuery()) {
                            if (rsMaquila.next()) {
                                r.setConfeccion(rsMaquila.getString("NombreMaquila"));
                            } else {
                                r.setConfeccion("Error: Sin maquila");
                            }
                        }
                    } catch (SQLException ex) {
                        r.setConfeccion("Error al obtener confección");
                        System.err.println("Error secundario: " + ex.getMessage());
                    }

                    // Mantener los valores por defecto en caso de error
                    r.setCantidadAsignadaConfeccion("0");
                    r.setCantidadEntregadaConfeccion("0");
                    r.setFaltantesConfeccion("0");
                    r.setPorcentajeConfeccion("0%");
                }
                // Consultar datos reales de lavandería para el corte actual
                try (PreparedStatement psLavanderiaData = conn.prepareStatement(
                        "SELECT MaquileroLavanderia, CantidadAsignadaL, CantidadEntregadaL FROM lavanderia WHERE CorteL = ?")) {

                    psLavanderiaData.setString(1, numeroCorte);
                    System.out.println("Consultando datos de lavandería para corte: " + numeroCorte);

                    try (ResultSet rsLavanderiaData = psLavanderiaData.executeQuery()) {
                        if (rsLavanderiaData.next()) {
                            // Obtener datos
                            String maquileroLavanderia = rsLavanderiaData.getString("MaquileroLavanderia");
                            String cantAsignadaL = rsLavanderiaData.getString("CantidadAsignadaL");
                            String cantEntregadaL = rsLavanderiaData.getString("CantidadEntregadaL");

                            // Validar datos para evitar nulos
                            maquileroLavanderia = (maquileroLavanderia != null && !maquileroLavanderia.isEmpty())
                                    ? maquileroLavanderia
                                    : "Sin asignar";
                            cantAsignadaL = (cantAsignadaL != null && !cantAsignadaL.isEmpty()) ? cantAsignadaL : "0";
                            cantEntregadaL = (cantEntregadaL != null && !cantEntregadaL.isEmpty()) ? cantEntregadaL : "0";

                            System.out.println("Datos encontrados - Maquilero Lavandería: " + maquileroLavanderia +
                                    ", Asignada: " + cantAsignadaL + ", Entregada: " + cantEntregadaL);

                            // Asignar valores
                            r.setLavanderia(maquileroLavanderia); // Establecer el nombre del maquilero de lavandería
                            r.setCantidadAsignadaLavanderia(cantAsignadaL);
                            r.setCantidadEntregadaLavanderia(cantEntregadaL);

                            // Calcular faltantes
                            r.setFaltantesLavanderia(calcularFaltante(cantAsignadaL, cantEntregadaL));

                            // Calcular porcentaje
                            r.setPorcentajeLavanderia(calcularPorcentaje(cantAsignadaL, cantEntregadaL));
                        } else {
                            System.out.println("No se encontraron datos de lavandería para el corte: " + numeroCorte);

                            // Intentar obtener al menos el nombre de una maquila de lavandería
                            try (PreparedStatement psMaquila = conn.prepareStatement(
                                    "SELECT NombreMaquila FROM maquilas WHERE AreaMaquila='Lavanderia' LIMIT 1")) {

                                try (ResultSet rsMaquila = psMaquila.executeQuery()) {
                                    if (rsMaquila.next()) {
                                        r.setLavanderia(rsMaquila.getString("NombreMaquila"));
                                    } else {
                                        r.setLavanderia("Sin lavandería asignada");
                                    }
                                }
                            }

                            // Mantener los valores por defecto para los demás campos
                            r.setCantidadAsignadaLavanderia("0");
                            r.setCantidadEntregadaLavanderia("0");
                            r.setFaltantesLavanderia("0");
                            r.setPorcentajeLavanderia("0%");
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error al consultar datos de lavandería: " + e.getMessage());
                    e.printStackTrace();

                    // Intentar obtener al menos el nombre de una maquila de lavandería en caso de error
                    try (PreparedStatement psMaquila = conn.prepareStatement(
                            "SELECT NombreMaquila FROM maquilas WHERE AreaMaquila='Lavanderia' LIMIT 1")) {

                        try (ResultSet rsMaquila = psMaquila.executeQuery()) {
                            if (rsMaquila.next()) {
                                r.setLavanderia(rsMaquila.getString("NombreMaquila"));
                            } else {
                                r.setLavanderia("Error: Sin maquila");
                            }
                        }
                    } catch (SQLException ex) {
                        r.setLavanderia("Error al obtener lavandería");
                        System.err.println("Error secundario: " + ex.getMessage());
                    }

                    // Mantener los valores por defecto en caso de error
                    r.setCantidadAsignadaLavanderia("0");
                    r.setCantidadEntregadaLavanderia("0");
                    r.setFaltantesLavanderia("0");
                    r.setPorcentajeLavanderia("0%");
                }

                // Consultar datos reales de empaque para el corte actual
                try (PreparedStatement psEmpaqueData = conn.prepareStatement(
                        "SELECT MaquileroEmpaque, cantidadAsignadaEmpaque, cantidadEntregadaEC, " +
                                "restosPrimerasEntregadas, segundasEntregadas " +
                                "FROM empaque WHERE CorteE = ?")) {

                    psEmpaqueData.setString(1, numeroCorte);
                    System.out.println("Consultando datos de empaque para corte: " + numeroCorte);

                    try (ResultSet rsEmpaqueData = psEmpaqueData.executeQuery()) {
                        if (rsEmpaqueData.next()) {
                            // Obtener datos
                            String maquileroEmpaque = rsEmpaqueData.getString("MaquileroEmpaque");
                            String cantAsignadaE = rsEmpaqueData.getString("cantidadAsignadaEmpaque");
                            String cantEntregadaCliente = rsEmpaqueData.getString("cantidadEntregadaEC");
                            String primerasEntregadas = rsEmpaqueData.getString("restosPrimerasEntregadas");
                            String segundasEntregadas = rsEmpaqueData.getString("segundasEntregadas");

                            // Validar datos para evitar nulos
                            maquileroEmpaque = (maquileroEmpaque != null && !maquileroEmpaque.isEmpty())
                                    ? maquileroEmpaque
                                    : "Sin asignar";
                            cantAsignadaE = (cantAsignadaE != null && !cantAsignadaE.isEmpty()) ? cantAsignadaE : "0";
                            cantEntregadaCliente = (cantEntregadaCliente != null && !cantEntregadaCliente.isEmpty()) ? cantEntregadaCliente : "0";
                            primerasEntregadas = (primerasEntregadas != null && !primerasEntregadas.isEmpty()) ? primerasEntregadas : "0";
                            segundasEntregadas = (segundasEntregadas != null && !segundasEntregadas.isEmpty()) ? segundasEntregadas : "0";

                            // Calcular total de entrega (suma de cliente, primeras y segundas)
                            int totalEntregado = 0;
                            try {
                                totalEntregado = Integer.parseInt(cantEntregadaCliente) +
                                        Integer.parseInt(primerasEntregadas) +
                                        Integer.parseInt(segundasEntregadas);
                            } catch (NumberFormatException e) {
                                System.err.println("Error al calcular total entregado: " + e.getMessage());
                            }

                            String totalEntregadoStr = String.valueOf(totalEntregado);

                            System.out.println("Datos encontrados - Maquilero Empaque: " + maquileroEmpaque +
                                    ", Asignada: " + cantAsignadaE +
                                    ", Cliente: " + cantEntregadaCliente +
                                    ", 1ras: " + primerasEntregadas +
                                    ", 2das: " + segundasEntregadas +
                                    ", Total: " + totalEntregadoStr);

                            // Asignar valores
                            r.setEmpaque(maquileroEmpaque); // Establecer el nombre del maquilero de empaque
                            r.setCantidadAsignadaEmpaque(cantAsignadaE);
                            r.setCantidadEntregadaClientes(cantEntregadaCliente);
                            r.setRestosPrimeras(primerasEntregadas);
                            r.setSegundasEntregadas(segundasEntregadas);
                            r.setTotalEmpaque(totalEntregadoStr);

                            // Calcular faltantes (asignada - total entregado)
                            r.setFaltantesEmpaque(calcularFaltante(cantAsignadaE, totalEntregadoStr));

                            // Calcular porcentaje
                            r.setPorcentajeEmpaque(calcularPorcentaje(cantAsignadaE, totalEntregadoStr));
                        } else {
                            System.out.println("No se encontraron datos de empaque para el corte: " + numeroCorte);

                            // Intentar obtener al menos el nombre de una maquila de empaque
                            try (PreparedStatement psMaquila = conn.prepareStatement(
                                    "SELECT NombreMaquila FROM maquilas WHERE AreaMaquila='Empaque' LIMIT 1")) {

                                try (ResultSet rsMaquila = psMaquila.executeQuery()) {
                                    if (rsMaquila.next()) {
                                        r.setEmpaque(rsMaquila.getString("NombreMaquila"));
                                    } else {
                                        r.setEmpaque("Sin empaque asignado");
                                    }
                                }
                            }

                            // Mantener los valores por defecto para los demás campos
                            r.setCantidadAsignadaEmpaque("0");
                            r.setCantidadEntregadaClientes("0");
                            r.setRestosPrimeras("0");
                            r.setSegundasEntregadas("0");
                            r.setTotalEmpaque("0");
                            r.setFaltantesEmpaque("0");
                            r.setPorcentajeEmpaque("0%");
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error al consultar datos de empaque: " + e.getMessage());
                    e.printStackTrace();

                    // Intentar obtener al menos el nombre de una maquila de empaque en caso de error
                    try (PreparedStatement psMaquila = conn.prepareStatement(
                            "SELECT NombreMaquila FROM maquilas WHERE AreaMaquila='Empaque' LIMIT 1")) {

                        try (ResultSet rsMaquila = psMaquila.executeQuery()) {
                            if (rsMaquila.next()) {
                                r.setEmpaque(rsMaquila.getString("NombreMaquila"));
                            } else {
                                r.setEmpaque("Error: Sin maquila");
                            }
                        }
                    } catch (SQLException ex) {
                        r.setEmpaque("Error al obtener empaque");
                        System.err.println("Error secundario: " + ex.getMessage());
                    }

                    // Mantener los valores por defecto en caso de error
                    r.setCantidadAsignadaEmpaque("0");
                    r.setCantidadEntregadaClientes("0");
                    r.setRestosPrimeras("0");
                    r.setSegundasEntregadas("0");
                    r.setTotalEmpaque("0");
                    r.setFaltantesEmpaque("0");
                    r.setPorcentajeEmpaque("0%");
                }

                // Obtener la primera mesa de corte disponible (si hay varias)
                try (Statement stmtMesa = conn.createStatement();
                     ResultSet rsMesa = stmtMesa.executeQuery(
                             "SELECT NombreMaquila FROM maquilas WHERE AreaMaquila = 'Mesa de corte' LIMIT 1")) {

                    if (rsMesa.next()) {
                        r.setMesaCorte(rsMesa.getString("NombreMaquila"));
                    } else {
                        r.setMesaCorte("Sin mesa de corte asignada");
                    }
                } catch (SQLException e) {
                    r.setMesaCorte("Error al obtener mesa de corte");
                    System.err.println("Error al obtener mesa de corte: " + e.getMessage());
                }

                // Verificar si existe registro de bordado para este corte
                try (PreparedStatement psBordado = conn.prepareStatement(
                        "SELECT COUNT(*) as contador FROM bordado WHERE CorteB = ?")) {

                    psBordado.setString(1, numeroCorte);

                    try (ResultSet rsBordado = psBordado.executeQuery()) {
                        if (rsBordado.next() && rsBordado.getInt("contador") > 0) {
                            r.setEstadoBordado("OK");
                        } else {
                            r.setEstadoBordado("NA");
                        }
                    }
                } catch (SQLException e) {
                    r.setEstadoBordado("Error");
                    System.err.println("Error al verificar bordado: " + e.getMessage());
                }


                lista.add(r);
            }

            System.out.println("Registros recuperados: " + lista.size());

        } catch (SQLException ex) {
            System.err.println("Error en la consulta SQL: " + ex.getMessage());
            ex.printStackTrace();
        }


        return lista;
    }

    // Método para cargar todos los clientes
    public ObservableList<String> cargarClientes() {
        ObservableList<String> clientes = FXCollections.observableArrayList();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT razonSocial FROM clientes")) {

            while (rs.next()) {
                clientes.add(rs.getString("razonSocial"));
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }

    // Método para cargar todas las maquilas de tipo "Mesa de corte"
    public ObservableList<String> cargarMesasDeCorte() {
        ObservableList<String> mesas = FXCollections.observableArrayList();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NombreMaquila FROM maquilas WHERE AreaMaquila = 'Mesa de corte'")) {

            while (rs.next()) {
                mesas.add(rs.getString("NombreMaquila"));
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar mesas de corte: " + e.getMessage());
            e.printStackTrace();
        }
        return mesas;
    }

    private String calcularFaltante(String asignado, String entregado) {
        try {
            return String.valueOf(Integer.parseInt(asignado) - Integer.parseInt(entregado));
        } catch (Exception e) {
            return "0";
        }
    }

    private String calcularPorcentaje(String asignado, String entregado) {
        try {
            double a = Double.parseDouble(asignado);
            double e = Double.parseDouble(entregado);
            return String.format("%.2f%%", (e / a) * 100);
        } catch (Exception ex) {
            return "0%";
        }
    }

    private int sumarNumeros(String... nums) {
        int sum = 0;
        for (String s : nums) {
            try { sum += Integer.parseInt(s); }
            catch (Exception ignored) {}
        }
        return sum;
    }
    // Añade una consulta simple de prueba para verificar datos
    private void verificarDatos() {
        String sql = "SELECT * FROM cortes";
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Verificando datos en tabla cortes:");
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("Corte encontrado: " + rs.getString("NumCorte"));
            }
            System.out.println("Total de cortes: " + count);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void exportToPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte PDF");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = fileChooser.showSaveDialog(reporteTable.getScene().getWindow());
        if (file == null) return;

        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            document.add(new Paragraph("REPORTE PROGRAMA DE PRODUCCIÓN"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(reporteTable.getColumns().size());
            table.setWidthPercentage(100);

            // Cabeceras
            reporteTable.getColumns().forEach(col -> table.addCell(col.getText()));

            // Filas
            for (ReporteProduccion r : data) {
                table.addCell(r.getCliente());
                table.addCell(r.getCorte());
                table.addCell(r.getCantidadProgramada());
                table.addCell(r.getMarca());
                table.addCell(r.getLinea());
                table.addCell(r.getTipoTela());
                table.addCell(r.getMesaCorte());
                table.addCell(r.getEntregaEncogimientos());
                table.addCell(r.getLiberacion());
                table.addCell(r.getFechaCorte());
                table.addCell(r.getCantidadCortada());
                table.addCell(r.getEstadoBordado());
                table.addCell(r.getConfeccion());
                table.addCell(r.getCantidadAsignadaConfeccion());
                table.addCell(r.getCantidadEntregadaConfeccion());
                table.addCell(r.getFaltantesConfeccion());
                table.addCell(r.getPorcentajeConfeccion());
                table.addCell(r.getLavanderia());
                table.addCell(r.getCantidadAsignadaLavanderia());
                table.addCell(r.getCantidadEntregadaLavanderia());
                table.addCell(r.getFaltantesLavanderia());
                table.addCell(r.getPorcentajeLavanderia());
                table.addCell(r.getEmpaque());
                table.addCell(r.getCantidadAsignadaEmpaque());
                table.addCell(r.getCantidadEntregadaClientes());
                table.addCell(r.getRestosPrimeras());
                table.addCell(r.getSegundasEntregadas());
                table.addCell(r.getTotalEmpaque());
                table.addCell(r.getPorcentajeEmpaque());
            }

            document.add(table);
            document.close();

        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
        }
    }



}
