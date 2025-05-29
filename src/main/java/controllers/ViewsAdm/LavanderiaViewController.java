package controllers.ViewsAdm;

import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Lavanderia;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class LavanderiaViewController {

    @FXML private TextField idLavanderiaField;
    @FXML private ComboBox<String> CorteLField;
    @FXML private ComboBox<String> MaquileroLavanderiaComboBox;
    @FXML private TextField CantidadAsignadaLField;
    @FXML private DatePicker FechaEntregaCorteLDate;
    @FXML private TextField PrecioLavanderiaField;
    @FXML private TextField CantidadEntregadaLField;

    @FXML private TableView<Lavanderia> lavanderiaTable;
    @FXML private TableColumn<Lavanderia, Integer> idLavanderiaColumn;
    @FXML private TableColumn<Lavanderia, String> CorteLColumn;
    @FXML private TableColumn<Lavanderia, String> MaquileroLavanderiaColumn;
    @FXML private TableColumn<Lavanderia, String> CantidadAsignadaLColumn;
    @FXML private TableColumn<Lavanderia, Date>   FechaEntregaCorteLColumn;
    @FXML private TableColumn<Lavanderia, String> PrecioLavanderiaColumn;
    @FXML private TableColumn<Lavanderia, String> CantidadEntregadaLColumn;
    @FXML private TableColumn<Lavanderia, String> diferenciaLColumn;

    private final ObservableList<Lavanderia> lavanderiaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ConexionDB.inicializar();
        cargarCortes();
        cargarMaquilas();
        configurarColumnas();
        cargarDatos();
        lavanderiaTable.setOnMouseClicked(e -> seleccionarLavado());
    }

    private void configurarColumnas() {
        idLavanderiaColumn       .setCellValueFactory(new PropertyValueFactory<>("idLavanderia"));
        CorteLColumn             .setCellValueFactory(new PropertyValueFactory<>("CorteL"));
        MaquileroLavanderiaColumn.setCellValueFactory(new PropertyValueFactory<>("maquileroLavanderia"));
        CantidadAsignadaLColumn  .setCellValueFactory(new PropertyValueFactory<>("cantidadAsignadaL"));
        FechaEntregaCorteLColumn .setCellValueFactory(new PropertyValueFactory<>("fechaEntregaCorteL"));
        PrecioLavanderiaColumn   .setCellValueFactory(new PropertyValueFactory<>("precio"));
        CantidadEntregadaLColumn .setCellValueFactory(new PropertyValueFactory<>("cantidadEntregadaL"));

        diferenciaLColumn.setCellValueFactory(cell -> {
            Lavanderia fila = cell.getValue();

            int    Lavada   = Integer.parseInt(fila.getCantidadEntregadaL());
            int    Confeccionada   = obtenerCantidadConfeccionada(fila.getIdLavanderia());
            int    diff      = Confeccionada - Lavada;
            String texto;
            if (diff > 0) {
                texto = "Faltante " + diff;
            } else if (diff < 0) {
                texto = "Excedente" + (-diff);
            } else {
                texto = "0";
            }
            return new javafx.beans.property.SimpleStringProperty(texto);
        });
    }

    private void cargarDatos() {
        lavanderiaList.clear();
        String sql = "SELECT * FROM lavanderia";
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                long ts = rs.getLong("FechaEntregaCorteL");
                Date fecha = new Date(ts);
                lavanderiaList.add(new Lavanderia(
                        rs.getInt("idLavanderia"),
                        rs.getString("CorteL"),
                        rs.getString("MaquileroLavanderia"),
                        rs.getString("CantidadAsignadaL"),
                        fecha,
                        rs.getString("precio"),
                        rs.getString("CantidadEntregadaL")
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
        try {
            int id = Integer.parseInt(idLavanderiaField.getText());
            if (existeId(id)) {
                mostrarAlerta("ID Existente", "Ya existe un registro con ese ID.");
                return;
            }
            String sql = "INSERT INTO lavanderia (" +
                    "idLavanderia, CorteL, MaquileroLavanderia, CantidadAsignadaL, FechaEntregaCorteL, precio, CantidadEntregadaL) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = ConexionDB.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                LocalDate ld = FechaEntregaCorteLDate.getValue();
                stmt.setInt(1, id);
                stmt.setString(2, CorteLField.getValue());
                stmt.setString(3, MaquileroLavanderiaComboBox.getValue());
                stmt.setString(4, CantidadAsignadaLField.getText());
                stmt.setLong(5, ld != null ? Date.valueOf(ld).getTime() : 0L);
                stmt.setString(6, PrecioLavanderiaField.getText());
                stmt.setString(7, CantidadEntregadaLField.getText());
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
    private void actualizarLavado() {
        if (idLavanderiaField.getText().isEmpty()) {
            mostrarAlerta("ID requerido", "Selecciona un registro para editar.");
            return;
        }
        String sql = "UPDATE lavanderia SET " +
                "CorteL=?, MaquileroLavanderia=?, CantidadAsignadaL=?, FechaEntregaCorteL=?, precio=?, CantidadEntregadaL=? " +
                "WHERE idLavanderia=?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            LocalDate ld = FechaEntregaCorteLDate.getValue();
            stmt.setString(1, CorteLField.getValue());
            stmt.setString(2, MaquileroLavanderiaComboBox.getValue());
            stmt.setString(3, CantidadAsignadaLField.getText());
            stmt.setLong(4, ld != null ? Date.valueOf(ld).getTime() : 0L);
            stmt.setString(5, PrecioLavanderiaField.getText());
            stmt.setString(6, CantidadEntregadaLField.getText());
            stmt.setInt(7, Integer.parseInt(idLavanderiaField.getText()));
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
            CorteLField.setValue(m.getCorteL());
            MaquileroLavanderiaComboBox.setValue(m.getMaquileroLavanderia());
            CantidadAsignadaLField.setText(m.getCantidadAsignadaL());
            FechaEntregaCorteLDate.setValue(
                    new java.sql.Date(m.getFechaEntregaCorteL().getTime()).toLocalDate()
            );
            PrecioLavanderiaField.setText(m.getPrecio());
            CantidadEntregadaLField.setText(m.getCantidadEntregadaL());
        }
    }

    private boolean existeId(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM lavanderia WHERE idLavanderia=?";
        try (PreparedStatement stmt = ConexionDB.conectar().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private void mostrarAlerta(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void limpiarCampos() {
        idLavanderiaField.clear();
        CorteLField.setValue(null);
        MaquileroLavanderiaComboBox.setValue(null);
        CantidadAsignadaLField.clear();
        FechaEntregaCorteLDate.setValue(null);
        PrecioLavanderiaField.clear();
        CantidadEntregadaLField.clear();
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

    private void cargarCortes() {
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NumCorte FROM cortes")) {
            ObservableList<String> cortes = FXCollections.observableArrayList();
            while (rs.next()) cortes.add(rs.getString("NumCorte"));
            CorteLField.setItems(cortes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int obtenerCantidadConfeccionada(int idLavanderia) {
        String sql = "SELECT CantidadAsignada FROM confeccion WHERE idConfeccion = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLavanderia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Integer.parseInt(rs.getString("CantidadAsignada"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
