// controllers/ViewsAdm/EmpaqueViewController.java
package controllers.ViewsAdm;

import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Empaque;

import java.sql.*;

public class EmpaqueViewController {

    @FXML private TextField idEmpaqueField;
    @FXML private ComboBox<String> MaquileroEmpaqueComboBox;
    @FXML private TextField PrecioEmpaqueField;
    @FXML private TextField CantidadEntregadaECField;
    @FXML private TextField RestosPrimerasEntregadasField;
    @FXML private TextField SegundasEntregadasCField;

    @FXML private TableView<Empaque> EmpaqueTable;
    @FXML private TableColumn<Empaque, String> idEmpaqueColumn;
    @FXML private TableColumn<Empaque, String> MaquileroEmpaqueColumn;
    @FXML private TableColumn<Empaque, String> PrecioEmpaqueColumn;
    @FXML private TableColumn<Empaque, String> CantidadEntregadaECColumn;
    @FXML private TableColumn<Empaque, String> RestosPrimerasEntregadasColumn;
    @FXML private TableColumn<Empaque, String> SegundasEntregadasColumn;

    private final ObservableList<Empaque> empaqueList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        cargarMaquilas();
        ConexionDB.inicializar();
        configurarColumnas();
        cargarDatos();
        EmpaqueTable.setOnMouseClicked(e -> seleccionarEmpaque());
    }

    private void configurarColumnas() {
        idEmpaqueColumn              .setCellValueFactory(new PropertyValueFactory<>("idEmpaque"));
        MaquileroEmpaqueColumn       .setCellValueFactory(new PropertyValueFactory<>("maquileroEmpaque"));
        PrecioEmpaqueColumn          .setCellValueFactory(new PropertyValueFactory<>("precioEmpaque"));
        CantidadEntregadaECColumn    .setCellValueFactory(new PropertyValueFactory<>("cantidadEntregadaEC"));
        RestosPrimerasEntregadasColumn.setCellValueFactory(new PropertyValueFactory<>("restosPrimerasEntregadas"));
        SegundasEntregadasColumn     .setCellValueFactory(new PropertyValueFactory<>("segundasEntregadas"));
    }

    private void cargarDatos() {
        empaqueList.clear();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM empaque")) {

            while (rs.next()) {
                empaqueList.add(new Empaque(
                        rs.getString("idEmpaque"),
                        rs.getString("maquileroEmpaque"),
                        rs.getString("precioEmpaque"),
                        rs.getString("cantidadEntregadaEC"),
                        rs.getString("restosPrimerasEntregadas"),
                        rs.getString("segundasEntregadas")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        EmpaqueTable.setItems(empaqueList);
    }

    @FXML
    private void guardarEmpaque() {
        if (idEmpaqueField.getText().isEmpty()) {
            alerta("Campos obligatorios", "El ID es obligatorio.");
            return;
        }
        String sql = """
            INSERT INTO empaque
              (idEmpaque, maquileroEmpaque, precioEmpaque,
               cantidadEntregadaEC, restosPrimerasEntregadas, segundasEntregadas)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idEmpaqueField.getText());
            stmt.setString(2, MaquileroEmpaqueComboBox.getValue());
            stmt.setString(3, PrecioEmpaqueField.getText());
            stmt.setString(4, CantidadEntregadaECField.getText());
            stmt.setString(5, RestosPrimerasEntregadasField.getText());
            stmt.setString(6, SegundasEntregadasCField.getText());
            stmt.executeUpdate();

            alerta("Éxito", "Registro guardado.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            alerta("Error BD", "No fue posible guardar el registro.");
        }
    }

    @FXML
    private void actualizarEmpaque() {
        if (idEmpaqueField.getText().isEmpty()) {
            alerta("ID requerido", "Selecciona un registro para editar.");
            return;
        }
        String sql = """
            UPDATE empaque SET
              maquileroEmpaque=?, precioEmpaque=?, cantidadEntregadaEC=?,
              restosPrimerasEntregadas=?, segundasEntregadas=?
            WHERE idEmpaque=?
        """;
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, MaquileroEmpaqueComboBox.getValue());
            stmt.setString(2, PrecioEmpaqueField.getText());
            stmt.setString(3, CantidadEntregadaECField.getText());
            stmt.setString(4, RestosPrimerasEntregadasField.getText());
            stmt.setString(5, SegundasEntregadasCField.getText());
            stmt.setString(6, idEmpaqueField.getText());
            stmt.executeUpdate();

            alerta("Actualizado", "Registro actualizado.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            alerta("Error BD", "No fue posible actualizar.");
        }
    }

    @FXML
    private void eliminarEmpaque() {
        Empaque sel = EmpaqueTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alerta("Sin selección", "Selecciona un registro para eliminar.");
            return;
        }
        String sql = "DELETE FROM empaque WHERE idEmpaque=?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sel.getIdEmpaque());
            stmt.executeUpdate();
            alerta("Eliminado", "Registro borrado.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            alerta("Error BD", "No fue posible eliminar.");
        }
    }

    private void seleccionarEmpaque() {
        Empaque m = EmpaqueTable.getSelectionModel().getSelectedItem();
        if (m != null) {
            idEmpaqueField.setText(m.getIdEmpaque());
            MaquileroEmpaqueComboBox.setValue(m.getMaquileroEmpaque());
            PrecioEmpaqueField.setText(m.getPrecioEmpaque());
            CantidadEntregadaECField.setText(m.getCantidadEntregadaEC());
            RestosPrimerasEntregadasField.setText(m.getRestosPrimerasEntregadas());
            SegundasEntregadasCField.setText(m.getSegundasEntregadas());
        }
    }

    private void cargarMaquilas() {
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT NombreMaquila FROM maquilas WHERE AreaMaquila='Empaque'")) {

            ObservableList<String> items = FXCollections.observableArrayList();
            while (rs.next()) {
                items.add(rs.getString("NombreMaquila"));
            }
            MaquileroEmpaqueComboBox.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void alerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }

    private void limpiarCampos() {
        idEmpaqueField.clear();
        MaquileroEmpaqueComboBox.setValue(null);
        PrecioEmpaqueField.clear();
        CantidadEntregadaECField.clear();
        RestosPrimerasEntregadasField.clear();
        SegundasEntregadasCField.clear();
    }
}
