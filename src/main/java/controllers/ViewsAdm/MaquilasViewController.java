package controllers.ViewsAdm;

import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Maquila;

import java.sql.*;

public class MaquilasViewController {

    @FXML private TextField idMField, NombreMaquila, nombreMField, celularMField,
            municipioMField, cpMField, emailMField, telefonoMField;
    @FXML private TextArea direccionMArea;
    @FXML private ComboBox<String> estadoMComboBox;
    @FXML private TableView<Maquila> maquilaTable;

    // Columnas con los fx:id que pusiste en el FXML:
    @FXML private TableColumn<Maquila, Integer> idMColumn;
    @FXML private TableColumn<Maquila, String>
            NombreMaquilaColumn,
            nombreMColumn,
            celularMColumn,
            direccionMColumn,
            estadoMColumn,
            municipioMColumn,
            cpMColumn,
            emailMColumn,
            telefonoMColumn;

    private final ObservableList<Maquila> maquilas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ConexionDB.inicializar();


        estadoMComboBox.setItems(FXCollections.observableArrayList(
                "Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas", "Chihuahua", "CDMX",
                "Coahuila", "Colima", "Durango", "Estado de México", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco",
                "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo",
                "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas"
        ));

        configurarColumnas();
        cargarDatos();
        maquilaTable.setOnMouseClicked(evt -> seleccionarMaquila());
    }

    private void configurarColumnas() {
        // *** Aquí van los nombres de propiedad que coinciden con tus getters ***
        idMColumn          .setCellValueFactory(new PropertyValueFactory<>("idM"));
        NombreMaquilaColumn.setCellValueFactory(new PropertyValueFactory<>("nombreMaquila"));
        nombreMColumn      .setCellValueFactory(new PropertyValueFactory<>("nombreM"));
        celularMColumn     .setCellValueFactory(new PropertyValueFactory<>("celularM"));
        direccionMColumn   .setCellValueFactory(new PropertyValueFactory<>("direccionM"));
        estadoMColumn      .setCellValueFactory(new PropertyValueFactory<>("estadoM"));
        municipioMColumn   .setCellValueFactory(new PropertyValueFactory<>("municipioM"));
        cpMColumn          .setCellValueFactory(new PropertyValueFactory<>("cpM"));
        emailMColumn       .setCellValueFactory(new PropertyValueFactory<>("emailM"));
        telefonoMColumn    .setCellValueFactory(new PropertyValueFactory<>("telefonoM"));
    }

    private void cargarDatos() {
        maquilas.clear();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM maquilas")) {

            while (rs.next()) {
                maquilas.add(new Maquila(
                        rs.getInt("idM"),
                        rs.getString("NombreMaquila"),
                        rs.getString("nombreM"),
                        rs.getString("celularM"),
                        rs.getString("direccionM"),
                        rs.getString("estadoM"),
                        rs.getString("municipioM"),
                        rs.getString("cpM"),
                        rs.getString("email"),
                        rs.getString("telefono")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        maquilaTable.setItems(maquilas);
    }

    @FXML
    private void guardarMaquila() {
        if (idMField.getText().isEmpty() || NombreMaquila.getText().isEmpty()) {
            mostrarAlerta("Campos obligatorios", "El campo ID y Nombre son obligatorios.");
            return;
        }

        try {
            int id = Integer.parseInt(idMField.getText());

            if (idExiste(id)) {
                mostrarAlerta("ID Existente", "Ya existe una maquila con ese ID.");
                return;
            }

            String sql = """
                INSERT INTO maquilas
                  (idM, NombreMaquila, nombreM, celularM,
                   direccionM, estadoM, municipioM, cpM, email, telefono)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            try (Connection conn = ConexionDB.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt   (1, id);
                stmt.setString(2, NombreMaquila.getText());
                stmt.setString(3, nombreMField.getText());
                stmt.setString(4, celularMField.getText());
                stmt.setString(5, direccionMArea.getText());
                stmt.setString(6, estadoMComboBox.getValue());
                stmt.setString(7, municipioMField.getText());
                stmt.setString(8, cpMField.getText());
                stmt.setString(9, emailMField.getText());
                stmt.setString(10, telefonoMField.getText());

                stmt.executeUpdate();
                mostrarAlerta("Éxito", "Maquila guardada correctamente.");
                limpiarCampos();
                cargarDatos();
            }

        } catch (NumberFormatException ex) {
            mostrarAlerta("Formato inválido", "El ID debe ser un número.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo guardar la maquila.");
        }
    }

    @FXML
    private void actualizarMaquila() {
        if (idMField.getText().isEmpty()) {
            mostrarAlerta("ID requerido", "Debe seleccionar una maquila para editar.");
            return;
        }

        String sql = """
            UPDATE maquilas SET
              NombreMaquila=?, nombreM=?, celularM=:?, direccionM=?,
              estadoM=?, municipioM=?, cpM=?, email=?, telefono=?
            WHERE idM=?
        """.replace(":?", "?"); // workaround para literal ?

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, NombreMaquila.getText());
            stmt.setString(2, nombreMField.getText());
            stmt.setString(3, celularMField.getText());
            stmt.setString(4, direccionMArea.getText());
            stmt.setString(5, estadoMComboBox.getValue());
            stmt.setString(6, municipioMField.getText());
            stmt.setString(7, cpMField.getText());
            stmt.setString(8, emailMField.getText());
            stmt.setString(9, telefonoMField.getText());
            stmt.setInt   (10, Integer.parseInt(idMField.getText()));

            stmt.executeUpdate();
            mostrarAlerta("Actualizado", "Maquila actualizada correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo actualizar la maquila.");
        }
    }

    @FXML
    private void eliminarMaquila() {
        Maquila sel = maquilaTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAlerta("Sin selección", "Por favor selecciona una maquila para eliminar.");
            return;
        }

        String sql = "DELETE FROM maquilas WHERE idM = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sel.getIdM());
            stmt.executeUpdate();
            mostrarAlerta("Eliminada", "Maquila eliminada correctamente.");
            cargarDatos();
            limpiarCampos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo eliminar la maquila.");
        }
    }

    private void seleccionarMaquila() {
        Maquila m = maquilaTable.getSelectionModel().getSelectedItem();
        if (m != null) {
            idMField.setText(String.valueOf(m.getIdM()));
            NombreMaquila.setText(m.getNombreMaquila());
            nombreMField.setText(m.getNombreM());
            celularMField.setText(m.getCelularM());
            direccionMArea.setText(m.getDireccionM());
            estadoMComboBox.setValue(m.getEstadoM());
            municipioMField.setText(m.getMunicipioM());
            cpMField.setText(m.getCpM());
            emailMField.setText(m.getEmailM());
            telefonoMField.setText(m.getTelefonoM());
        }
    }

    private boolean idExiste(int id) {
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM maquilas WHERE idM = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
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
        idMField.clear(); NombreMaquila.clear(); nombreMField.clear();
        celularMField.clear(); direccionMArea.clear();
        estadoMComboBox.getSelectionModel().clearSelection();
        municipioMField.clear(); cpMField.clear(); emailMField.clear(); telefonoMField.clear();
    }
}
