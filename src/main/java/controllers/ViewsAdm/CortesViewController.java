package controllers.ViewsAdm;

import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cortes;

import java.sql.*;

public class CortesViewController {

    @FXML private TextField idCortesField, NumCorteField, CantidadField, MarcaField, LineaField, TipoTelaField;
    @FXML private TableView<Cortes> cortesTable;
    @FXML private TableColumn<Cortes, Integer> idCortesColumn;
    @FXML private TableColumn<Cortes, String> NumCorteColumn, CantidadColumn, MarcaColumn, LineaColumn, TipoTelaColumn;

    private final ObservableList<Cortes> cortesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ConexionDB.inicializar();
        configurarColumnas();
        cargarDatos();

        // Detectar selección en la tabla
        cortesTable.setOnMouseClicked(e -> seleccionarCorte());
    }

    private void configurarColumnas() {
        idCortesColumn .setCellValueFactory(new PropertyValueFactory<>("idCortes"));
        NumCorteColumn .setCellValueFactory(new PropertyValueFactory<>("NumCorte"));
        CantidadColumn .setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
        MarcaColumn    .setCellValueFactory(new PropertyValueFactory<>("Marca"));
        LineaColumn    .setCellValueFactory(new PropertyValueFactory<>("Linea"));
        TipoTelaColumn .setCellValueFactory(new PropertyValueFactory<>("TipoTela"));
    }

    private void cargarDatos() {
        cortesList.clear();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cortes")) {
            while (rs.next()) {
                cortesList.add(new Cortes(
                        rs.getInt("idCortes"),
                        rs.getString("NumCorte"),
                        rs.getString("Cantidad"),
                        rs.getString("Marca"),
                        rs.getString("Linea"),
                        rs.getString("TipoTela")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        cortesTable.setItems(cortesList);
    }

    @FXML
    private void guardarCortes() {
        if (idCortesField.getText().isEmpty() || NumCorteField.getText().isEmpty()) {
            mostrarAlerta("Campos obligatorios", "ID y Número de Corte son obligatorios.");
            return;
        }
        try {
            int id = Integer.parseInt(idCortesField.getText());

            if (existeId(id)) {
                mostrarAlerta("ID Existente", "Ya existe un corte con ese ID.");
                return;
            }

            try (Connection conn = ConexionDB.conectar();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO cortes (idCortes, NumCorte, Cantidad, Marca, Linea, TipoTela)" +
                                 "VALUES (?, ?, ?, ?, ?, ?)")) {
                stmt.setInt(1, id);
                stmt.setString(2, NumCorteField.getText());
                stmt.setString(3, CantidadField.getText());
                stmt.setString(4, MarcaField.getText());
                stmt.setString(5, LineaField.getText());
                stmt.setString(6, TipoTelaField.getText());

                stmt.executeUpdate();
                mostrarAlerta("Éxito", "Corte guardado correctamente.");
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
    private void actualizarCortes() {
        if (idCortesField.getText().isEmpty()) {
            mostrarAlerta("ID requerido", "Selecciona un corte para editar.");
            return;
        }

        String sql = """
            UPDATE cortes SET
              NumCorte=?, Cantidad=?, Marca=:?, Linea=?,
              TipoTela=?
            WHERE idCortes=?
        """.replace(":?", "?");

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, NumCorteField.getText());
            stmt.setString(2, CantidadField.getText());
            stmt.setString(3, MarcaField.getText());
            stmt.setString(4, LineaField.getText());
            stmt.setString(5, TipoTelaField.getText());
            stmt.setInt(6, Integer.parseInt(idCortesField.getText()));

            stmt.executeUpdate();
            mostrarAlerta("Actualizado", "Corte actualizado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo actualizar el corte.");
        }
    }

    @FXML
    private void eliminarCortes() {
        Cortes sel = cortesTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAlerta("Sin selección", "Selecciona un corte para eliminar.");
            return;
        }

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM cortes WHERE idCortes=?")) {
            stmt.setInt(1, sel.getIdCortes());
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
        Cortes m = cortesTable.getSelectionModel().getSelectedItem();
        if (m != null) {
            idCortesField.setText(String.valueOf(m.getIdCortes()));
            NumCorteField.setText(m.getNumCorte());
            CantidadField.setText(m.getCantidad());
            MarcaField.setText(m.getMarca());
            LineaField.setText(m.getLinea());
            TipoTelaField.setText(m.getTipoTela());
        }
    }

    private boolean existeId(int id) {
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM cortes WHERE idCortes=?")) {
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
        idCortesField.clear();
        NumCorteField.clear();
        CantidadField.clear();
        MarcaField.clear();
        LineaField.clear();
        TipoTelaField.clear();
    }
}
