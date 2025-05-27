package controllers.ViewsAdm;

import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Lavanderia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LavanderiaViewController {

    @FXML private TextField idLavanderiaField, PrecioLavanderiaField;
    @FXML private ComboBox<String> MaquileroLavanderiaComboBox;
    @FXML private TableView<Lavanderia> lavanderiaTable;
    @FXML private TableColumn<Lavanderia, Integer> idLavanderiaColumn;
    @FXML private TableColumn<Lavanderia, String> MaquileroLavanderiaColumn, PrecioLavanderiaColumn;

    private final ObservableList<Lavanderia> lavanderiaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ConexionDB.inicializar();
        cargarMaquilas();
        configurarColumnas();
        cargarDatos();
        lavanderiaTable.setOnMouseClicked(e -> seleccionarLavado());
    }

    private void configurarColumnas() {
        idLavanderiaColumn       .setCellValueFactory(new PropertyValueFactory<>("idLavanderia"));
        MaquileroLavanderiaColumn.setCellValueFactory(new PropertyValueFactory<>("maquilero"));
        PrecioLavanderiaColumn    .setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    private void cargarDatos() {
        lavanderiaList.clear();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM lavanderia")) {
            while (rs.next()) {
                lavanderiaList.add(new Lavanderia(
                        rs.getInt("idLavanderia"),
                        rs.getString("MaquileroLavanderia"),
                        rs.getString("PrecioLavanderia")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        lavanderiaTable.setItems(lavanderiaList);
    }

    @FXML
    private void guardarLavado() {
        if (idLavanderiaField.getText().isEmpty()) {
            mostrarAlerta("Campos obligatorios", "ID obligatorio.");
            return;
        }
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO lavanderia (idLavanderia, MaquileroLavanderia, PrecioLavanderia) VALUES (?, ?, ?)")) {
            int id = Integer.parseInt(idLavanderiaField.getText());
            if (existeId(id)) {
                mostrarAlerta("ID Existente", "Ya existe un registro con ese ID.");
                return;
            }
            stmt.setInt(1, id);
            stmt.setString(2, MaquileroLavanderiaComboBox.getValue());
            stmt.setString(3, PrecioLavanderiaField.getText());
            stmt.executeUpdate();
            mostrarAlerta("Éxito", "Entrada de lavandería guardada.");
            limpiarCampos();
            cargarDatos();
        } catch (NumberFormatException nf) {
            mostrarAlerta("Formato inválido", "ID debe ser numérico.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo guardar el registro.");
        }
    }

    @FXML
    private void actualizarLavado() {
        if (idLavanderiaField.getText().isEmpty()) {
            mostrarAlerta("ID requerido", "Selecciona un registro para editar.");
            return;
        }
        String sql = "UPDATE lavanderia SET MaquileroLavanderia=?, PrecioLavanderia=? WHERE idLavanderia=?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, MaquileroLavanderiaComboBox.getValue());
            stmt.setString(2, PrecioLavanderiaField.getText());
            stmt.setInt(3, Integer.parseInt(idLavanderiaField.getText()));
            stmt.executeUpdate();
            mostrarAlerta("Actualizado", "Registro actualizado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo actualizar el registro.");
        }
    }

    @FXML
    private void eliminarLavado() {
        Lavanderia sel = lavanderiaTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAlerta("Sin selección", "Selecciona un registro para eliminar.");
            return;
        }
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM lavanderia WHERE idLavanderia=?")) {
            stmt.setInt(1, sel.getIdLavanderia());
            stmt.executeUpdate();
            mostrarAlerta("Eliminado", "Registro eliminado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo eliminar el registro.");
        }
    }

    private void seleccionarLavado() {
        Lavanderia m = lavanderiaTable.getSelectionModel().getSelectedItem();
        if (m != null) {
            idLavanderiaField.setText(String.valueOf(m.getIdLavanderia()));
            MaquileroLavanderiaComboBox.setValue(m.getMaquilero());
            PrecioLavanderiaField.setText(m.getPrecio());
        }
    }

    private boolean existeId(int id) throws SQLException {
        try (PreparedStatement stmt = ConexionDB.conectar()
                .prepareStatement("SELECT COUNT(*) FROM lavanderia WHERE idLavanderia=?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }

    private void limpiarCampos() {
        idLavanderiaField.clear();
        MaquileroLavanderiaComboBox.setValue(null);
        PrecioLavanderiaField.clear();
    }

    private void cargarMaquilas() {
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NombreMaquila FROM maquilas WHERE AreaMaquila='Lavanderia'")) {
            ObservableList<String> items = FXCollections.observableArrayList();
            while (rs.next()) items.add(rs.getString("NombreMaquila"));
            MaquileroLavanderiaComboBox.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}