package controllers.ViewsAdm;

import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Corte;

import java.sql.*;

public class CorteViewController {

    @FXML private TextField idCorteField, EntregaEncogimientosField, LiberacionTrazoField, FechaCorteField, PrecioDeCorteField, CantidadCortadaField;
    @FXML private TableView<Corte> corteTable;
    @FXML private TableColumn<Corte, Integer> idCorteColumn;
    @FXML private TableColumn<Corte, String> EntregaEncogimientosColumn, LiberacionTrazoColumn, FechaCorteColumn, PrecioDeCorteColumn, CantidadCortadaColumn;

    private final ObservableList<Corte> corteList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ConexionDB.inicializar();
        configurarColumnas();
        cargarDatos();

        // Detectar selección en la tabla
        corteTable.setOnMouseClicked(e -> seleccionarCorte());
    }

    private void configurarColumnas() {
        idCorteColumn.setCellValueFactory(new PropertyValueFactory<>("idCorte"));
        EntregaEncogimientosColumn.setCellValueFactory(new PropertyValueFactory<>("EntregaEncogimientos"));
        LiberacionTrazoColumn.setCellValueFactory(new PropertyValueFactory<>("LiberacionTrazo"));
        FechaCorteColumn.setCellValueFactory(new PropertyValueFactory<>("FechaCorte"));
        PrecioDeCorteColumn.setCellValueFactory(new PropertyValueFactory<>("PrecioDeCorte"));
        CantidadCortadaColumn.setCellValueFactory(new PropertyValueFactory<>("CantidadCortada"));
    }

    private void cargarDatos() {
        corteList.clear();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM corte")) {
            while (rs.next()) {
                corteList.add(new Corte(
                        rs.getInt("idCorte"),
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
                         "INSERT INTO corte (idCorte, EntregaEncogimientos, Liberacion, Fecha, Precio, Cantidad)" +
                                 "VALUES (?, ?, ?, ?, ?, ?)")) {
                stmt.setInt(1, id);
                stmt.setString(2, EntregaEncogimientosField.getText());
                stmt.setString(3, LiberacionTrazoField.getText());
                stmt.setString(4, FechaCorteField.getText());
                stmt.setString(5, PrecioDeCorteField.getText());
                stmt.setString(6, CantidadCortadaField.getText());

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
              EntregaEncogimientos=?, Liberacion=?, Fecha=:?, Precio=?,
              Cantidad=?
            WHERE idCorte=?
        """.replace(":?", "?");

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, EntregaEncogimientosField.getText());
            stmt.setString(2, LiberacionTrazoField.getText());
            stmt.setString(3, FechaCorteField.getText());
            stmt.setString(4, PrecioDeCorteField.getText());
            stmt.setString(5, CantidadCortadaField.getText());
            stmt.setInt(6, Integer.parseInt(idCorteField.getText()));

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
            EntregaEncogimientosField.setText(m.getEntregaEncogimientos());
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
        EntregaEncogimientosField.clear();
        LiberacionTrazoField.clear();
        FechaCorteField.clear();
        PrecioDeCorteField.clear();
        CantidadCortadaField.clear();
    }
}
