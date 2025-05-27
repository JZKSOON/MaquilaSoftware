package controllers.ViewsAdm;

import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Corte;

import java.sql.*;
import java.time.LocalDate;

public class CorteViewController {

    @FXML private TextField idCorteField, LiberacionTrazoField, FechaCorteField, PrecioDeCorteField, CantidadCortadaField;
    @FXML private ComboBox<String> CorteMCField, EntregaEncogimientosField;
    @FXML private DatePicker fechaPicker;
    @FXML private TableView<Corte> corteTable;
    @FXML private TableColumn<Corte, Integer> idCorteColumn;

    @FXML private TableColumn<Corte, String> CorteMCColumn, EntregaEncogimientosColumn, LiberacionTrazoColumn, FechaCorteColumn, PrecioDeCorteColumn, CantidadCortadaColumn,diferenciaColumn;

    private final ObservableList<Corte> corteList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        EntregaEncogimientosField.getItems().addAll("Fecha", "N/A", "OK");
        fechaPicker.setVisible(false);


        cargarCortes();
        ConexionDB.inicializar();
        configurarColumnas();
        cargarDatos();

        // Escucha el cambio de selección en el ComboBox
        EntregaEncogimientosField.setOnAction(e -> manejarSeleccion());

        // Detectar selección en la tabla
        corteTable.setOnMouseClicked(e -> seleccionarCorte());
    }

    private void configurarColumnas() {
        idCorteColumn            .setCellValueFactory(new PropertyValueFactory<>("idCorte"));
        CorteMCColumn            .setCellValueFactory(new PropertyValueFactory<>("CorteMC"));
        EntregaEncogimientosColumn.setCellValueFactory(new PropertyValueFactory<>("EntregaEncogimientos"));
        LiberacionTrazoColumn    .setCellValueFactory(new PropertyValueFactory<>("LiberacionTrazo"));
        FechaCorteColumn         .setCellValueFactory(new PropertyValueFactory<>("FechaCorte"));
        PrecioDeCorteColumn      .setCellValueFactory(new PropertyValueFactory<>("PrecioDeCorte"));
        CantidadCortadaColumn    .setCellValueFactory(new PropertyValueFactory<>("CantidadCortada"));
        diferenciaColumn.setCellValueFactory(cell -> {
            Corte  fila      = cell.getValue();
            int    cortada   = Integer.parseInt(fila.getCantidadCortada());
            int    programada= obtenerCantidadProgramada(fila.getIdCorte());
            int    diff      = cortada - programada;
            String texto;
            if (diff > 0) {
                texto = "Excedente " + diff;
            } else if (diff < 0) {
                texto = "Faltante " + (-diff);
            } else {
                texto = "0";
            }
            return new javafx.beans.property.SimpleStringProperty(texto);
        });
    }

    private void cargarDatos() {
        corteList.clear();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM corte")) {
            while (rs.next()) {
                corteList.add(new Corte(
                        rs.getInt("idCorte"),
                        rs.getString("CorteMC"),
                        rs.getString("EntregaEncogimientos"),
                        rs.getString("Liberacion"),
                        rs.getString("Fecha"),
                        rs.getString("Precio"),
                        rs.getString("Cantidad")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        corteTable.setItems(corteList);
    }

    @FXML
    private void guardarCorte() {
        if (idCorteField.getText().isEmpty()) {
            mostrarAlerta("Campos obligatorios", "ID obligatorio.");
            return;
        }
        try {
            int id = Integer.parseInt(idCorteField.getText());

            if (existeId(id)) {
                mostrarAlerta("ID Existente", "Ya existe un corte con ese ID.");
                return;
            }

            try (Connection conn = ConexionDB.conectar();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO corte (idCorte, CorteMC, EntregaEncogimientos, Liberacion, Fecha, Precio, Cantidad)" +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                stmt.setInt(1, id);
                stmt.setString(2, CorteMCField.getValue());
                stmt.setString(3, EntregaEncogimientosField.getValue());
                stmt.setString(4, LiberacionTrazoField.getText());
                stmt.setString(5, FechaCorteField.getText());
                stmt.setString(6, PrecioDeCorteField.getText());
                stmt.setString(7, CantidadCortadaField.getText());

                stmt.executeUpdate();
                mostrarAlerta("Éxito", "Proceso guardado correctamente.");
                limpiarCampos();
                cargarDatos();
            }

        } catch (NumberFormatException nf) {
            mostrarAlerta("Formato inválido", "ID debe ser numérico.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo guardar el corte.");
        }
    }

    @FXML
    private void actualizarCorte() {
        if (idCorteField.getText().isEmpty()) {
            mostrarAlerta("ID requerido", "Selecciona un corte para editar.");
            return;
        }

        String sql = """
            UPDATE corte SET
              CorteMC=?, EntregaEncogimientos=?, Liberacion=?, Fecha=:?, Precio=?,
              Cantidad=?
            WHERE idCorte=?
        """.replace(":?", "?");

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, CorteMCField.getValue());
            stmt.setString(2, EntregaEncogimientosField.getValue());
            stmt.setString(3, LiberacionTrazoField.getText());
            stmt.setString(4, FechaCorteField.getText());
            stmt.setString(5, PrecioDeCorteField.getText());
            stmt.setString(6, CantidadCortadaField.getText());
            stmt.setInt(7, Integer.parseInt(idCorteField.getText()));

            stmt.executeUpdate();
            mostrarAlerta("Actualizado", "Corte actualizado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo actualizar el proceso.");
        }
    }

    @FXML
    private void eliminarCorte() {
        Corte sel = corteTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAlerta("Sin selección", "Selecciona un corte para eliminar.");
            return;
        }

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM corte WHERE idCorte=?")) {
            stmt.setInt(1, sel.getIdCorte());
            stmt.executeUpdate();
            mostrarAlerta("Eliminado", "Corte eliminado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo eliminar el corte.");
        }
    }

    private void seleccionarCorte() {
        Corte m = corteTable.getSelectionModel().getSelectedItem();
        if (m != null) {
            idCorteField.setText(String.valueOf(m.getIdCorte()));
            CorteMCField.setValue(m.getCorteMC());
            EntregaEncogimientosField.setValue(m.getEntregaEncogimientos());
            LiberacionTrazoField.setText(m.getLiberacionTrazo());
            FechaCorteField.setText(m.getFechaCorte());
            PrecioDeCorteField.setText(m.getPrecioDeCorte());
            CantidadCortadaField.setText(m.getCantidadCortada());
        }
    }

    private boolean existeId(int id) {
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM corte WHERE idCorte=?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void mostrarAlerta(String t, String m) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(t);
        alert.setHeaderText(null);
        alert.setContentText(m);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        idCorteField.clear();
        CorteMCField.setValue(null);
        EntregaEncogimientosField.setValue(null);
        LiberacionTrazoField.clear();
        FechaCorteField.clear();
        PrecioDeCorteField.clear();
        CantidadCortadaField.clear();
    }

    //Recupera la cantidad programada de la tabla `cortes` (campo Cantidad) usando el mismo id que el corte Devuelve 0 si no existe o hay error
    private int obtenerCantidadProgramada(int idCorte) {
        String sql = "SELECT Cantidad FROM cortes WHERE idCortes = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCorte);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Integer.parseInt(rs.getString("Cantidad"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void cargarCortes() {
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NumCorte FROM cortes")) {

            ObservableList<String> cortes = FXCollections.observableArrayList();
            while (rs.next()) {
                cortes.add(rs.getString("NumCorte"));
            }
            CorteMCField.setItems(cortes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void manejarSeleccion() {
        String seleccion = EntregaEncogimientosField.getValue();
        if ("Fecha".equals(seleccion)) {
            fechaPicker.setVisible(true);

            // Capturamos la selección de fecha desde el DatePicker
            fechaPicker.setOnAction(event -> {
                LocalDate fechaSeleccionada = fechaPicker.getValue();
                if (fechaSeleccionada != null) {
                    // Convertimos la fecha seleccionada a texto y la asignamos al ComboBox
                    String fechaTexto = fechaSeleccionada.toString();
                    EntregaEncogimientosField.setValue(fechaTexto);

                    // Ocultamos el DatePicker después de la selección
                    fechaPicker.setVisible(false);
                }
            });
        } else {
            fechaPicker.setVisible(false);
        }
    }


}
