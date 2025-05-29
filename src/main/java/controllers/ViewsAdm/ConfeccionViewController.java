// controllers/ViewsAdm/ConfeccionViewController.java
package controllers.ViewsAdm;

import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Bordado;
import model.Confeccion;

import java.sql.*;
import java.time.LocalDate;

public class ConfeccionViewController {

    @FXML private TextField idConfeccionField;
    @FXML private ComboBox<String> MaquileroConfeccionComboBox, CorteCField;
    @FXML private TextField PrecioConfeccionField, CantidadEntregadaField;
    @FXML private TextField CantidadAsignadaField;
    @FXML private DatePicker FechaEntregaCorteDate;

    @FXML private TableView<Confeccion> confeccionTable;
    @FXML private TableColumn<Confeccion, Integer> idConfeccionColumn;
    @FXML private TableColumn<Confeccion, String> MaquileroConfeccionColumn;
    @FXML private TableColumn<Confeccion, String> PrecioConfeccionColumn;
    @FXML private TableColumn<Confeccion, String> CantidadAsignadaColumn;
    @FXML private TableColumn<Confeccion, String> CorteCColumn;
    @FXML private TableColumn<Confeccion, String> CantidadEntregadaColumn;
    @FXML private TableColumn<Confeccion, String> diferenciaCColumn;
    @FXML private TableColumn<Confeccion, Date> FechaEntregaCorteColumn;

    private final ObservableList<Confeccion> confeccionList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {


        ConexionDB.inicializar();
        cargarMaquilas();
        cargarCortes();
        configurarColumnas();
        cargarDatos();
        confeccionTable.setOnMouseClicked(e -> seleccionarCorte());
    }

    private void configurarColumnas() {
        idConfeccionColumn        .setCellValueFactory(new PropertyValueFactory<>("idConfeccion"));
        CorteCColumn              .setCellValueFactory(new PropertyValueFactory<>("corteC"));
        MaquileroConfeccionColumn .setCellValueFactory(new PropertyValueFactory<>("MaquileroConfeccion"));
        PrecioConfeccionColumn    .setCellValueFactory(new PropertyValueFactory<>("PrecioConfeccion"));
        CantidadAsignadaColumn    .setCellValueFactory(new PropertyValueFactory<>("CantidadAsignadaConfeccion"));
        FechaEntregaCorteColumn   .setCellValueFactory(new PropertyValueFactory<>("FechaEntregaCorteConfeccion"));
        CantidadEntregadaColumn   .setCellValueFactory(new PropertyValueFactory<>("CantidadEntregada"));
        diferenciaCColumn.setCellValueFactory(cell -> {
            Confeccion fila      = cell.getValue();

            int    Confeccionada   = Integer.parseInt(fila.getCantidadEntregada());
            int    Cortada   = obtenerCantidadCortada(fila.getIdConfeccion());
            int    diff      = Confeccionada - Cortada;
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
        confeccionList.clear();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM confeccion")) {

            while (rs.next()) {
                long ts = rs.getLong("FechaEntregaCorte");
                Date fecha = new Date(ts);
                confeccionList.add(new Confeccion(
                        rs.getInt("idConfeccion"),
                        rs.getString("CorteC"),
                        rs.getString("MaquileroConfeccion"),
                        rs.getString("PrecioConfeccion"),
                        rs.getString("CantidadAsignada"),
                        fecha,
                        rs.getString("CantidadEntregada")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        confeccionTable.setItems(confeccionList);
    }

    @FXML
    private void guardarConfeccion() {
        if (idConfeccionField.getText().isEmpty()) {
            mostrarAlerta("Campos obligatorios", "ID obligatorio.");
            return;
        }
        try {
            int id = Integer.parseInt(idConfeccionField.getText());

            if (existeId(id)) {
                mostrarAlerta("ID Existente", "Ya existe un registro con ese ID.");
                return;
            }

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO confeccion " +
                             "(idConfeccion, CorteC, MaquileroConfeccion, PrecioConfeccion, CantidadAsignada, FechaEntregaCorte, CantidadEntregada) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            LocalDate ld = FechaEntregaCorteDate.getValue();

            stmt.setInt(1, id);
            stmt.setString(2, CorteCField.getValue());
            stmt.setString(3, MaquileroConfeccionComboBox.getValue());
            stmt.setString(4, PrecioConfeccionField.getText());
            stmt.setString(5, CantidadAsignadaField.getText());
            stmt.setLong(6, ld != null ? Date.valueOf(ld).getTime() : 0L);
            stmt.setString(7, CantidadEntregadaField.getText());

            stmt.executeUpdate();
            mostrarAlerta("Éxito", "Registro guardado correctamente.");
            limpiarCampos();
            cargarDatos();
            }
        } catch (NumberFormatException nf) {
            mostrarAlerta("Formato inválido", "ID debe ser numérico.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo guardar el registro.");
        }
    }

    @FXML
    private void actualizarConfeccion() {
        if (idConfeccionField.getText().isEmpty()) {
            mostrarAlerta("ID requerido", "Selecciona un registro para editar.");
            return;
        }
        String sql = "UPDATE confeccion SET " +
                "CorteC=?, MaquileroConfeccion=?, PrecioConfeccion=?, CantidadAsignada=?, FechaEntregaCorte=?, CantidadEntregada=? " +
                "WHERE idConfeccion=?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            LocalDate ld = FechaEntregaCorteDate.getValue();
            stmt.setString(1, CorteCField.getValue());
            stmt.setString(2, MaquileroConfeccionComboBox.getValue());
            stmt.setString(3, PrecioConfeccionField.getText());
            stmt.setString(4, CantidadAsignadaField.getText());
            stmt.setLong  (5, ld!=null ? Date.valueOf(ld).getTime() : 0L);
            stmt.setString(6, CantidadEntregadaField.getText());
            stmt.setInt   (7, Integer.parseInt(idConfeccionField.getText()));

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
    private void eliminarConfeccion() {
        Confeccion sel = confeccionTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAlerta("Sin selección", "Selecciona un registro para eliminar.");
            return;
        }
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM confeccion WHERE idConfeccion=?")) {
            stmt.setInt(1, sel.getIdConfeccion());
            stmt.executeUpdate();
            mostrarAlerta("Eliminado", "Registro eliminado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo eliminar el registro.");
        }
    }

    private void seleccionarCorte() {
        Confeccion m = confeccionTable.getSelectionModel().getSelectedItem();
        if (m != null) {
            idConfeccionField.setText(String.valueOf(m.getIdConfeccion()));
            CorteCField.setValue(m.getCorteC());
            MaquileroConfeccionComboBox.setValue(m.getMaquileroConfeccion());
            FechaEntregaCorteDate.setValue(
                    new java.sql.Date(m.getFechaEntregaCorteConfeccion().getTime())
                            .toLocalDate()
            );
            PrecioConfeccionField.setText(m.getPrecioConfeccion());
            CantidadAsignadaField.setText(m.getCantidadAsignadaConfeccion());
            CantidadEntregadaField.setText(m.getCantidadEntregada());
        }
    }

    private boolean existeId(int id) throws SQLException {
        try (PreparedStatement stmt = ConexionDB.conectar()
                .prepareStatement("SELECT COUNT(*) FROM confeccion WHERE idConfeccion=?")) {
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
        idConfeccionField.clear();
        MaquileroConfeccionComboBox.setValue(null);
        FechaEntregaCorteDate.setValue(null);
        PrecioConfeccionField.clear();
        CantidadAsignadaField.clear();
        CantidadEntregadaField.clear();
        CorteCField.setValue(null);
    }

    private void cargarMaquilas() {
        try (Statement stmt = ConexionDB.conectar().createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT NombreMaquila FROM maquilas WHERE AreaMaquila='Confeccion'"
             )) {
            ObservableList<String> items = FXCollections.observableArrayList();
            while (rs.next()) items.add(rs.getString("NombreMaquila"));
            MaquileroConfeccionComboBox.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarCortes() {
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NumCorte FROM cortes")) {

            ObservableList<String> cortes = FXCollections.observableArrayList();
            while (rs.next()) {
                cortes.add(rs.getString("NumCorte"));
            }
            CorteCField.setItems(cortes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int obtenerCantidadCortada(int idConfeccion) {
        String sql = "SELECT Cantidad FROM corte WHERE idCorte = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idConfeccion);
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
}
