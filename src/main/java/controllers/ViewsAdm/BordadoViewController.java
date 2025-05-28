package controllers.ViewsAdm;

import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Bordado;

import java.sql.*;
import java.time.LocalDate;

public class BordadoViewController {

    @FXML private TextField idBordadoField, CostoMaquilaBordadoField, CantidadEntregadaField;
    @FXML private ComboBox<String> CorteBField, MaquileroBordadoField;
    @FXML private DatePicker FechaEntradaBordadoDate, FechaSalidaBordadoDate;
    @FXML private TableView<Bordado> bordadoTable;
    @FXML private TableColumn<Bordado, Integer> idBordadoColumn;
    @FXML private TableColumn<Bordado, String> CorteBColumn, MaquileroBordadoColumn, FechaEntradaBordadoColumn, FechaSalidaBordadoColumn,CostoMaquilaBordadoColumn, CantidadEntregadaColumn, diferenciaBColumn;
    @FXML private TableColumn<Bordado, Date> FechaEntradaBordadoDateColumn, FechaSalidaBordadoDateColumn;

    private final ObservableList<Bordado> bordadoList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        MaquileroBordadoField.setItems(FXCollections.observableArrayList(
        ));

        cargarMaquilas();
        cargarCortes();
        ConexionDB.inicializar();
        configurarColumnas();
        cargarDatos();

        // Detectar selección en la tabla
        bordadoTable.setOnMouseClicked(e -> seleccionarCorte());
    }

    private void configurarColumnas() {
        idBordadoColumn.setCellValueFactory(new PropertyValueFactory<>("idBordado"));
        CorteBColumn            .setCellValueFactory(new PropertyValueFactory<>("CorteB"));
        MaquileroBordadoColumn.setCellValueFactory(new PropertyValueFactory<>("MaquileroBordado"));
        CostoMaquilaBordadoColumn.setCellValueFactory(new PropertyValueFactory<>("CostoMaquilaBordado"));
        CantidadEntregadaColumn.setCellValueFactory(new PropertyValueFactory<>("CantidadEntregada"));
        FechaEntradaBordadoColumn.setCellValueFactory(new PropertyValueFactory<>("FechaEntradaBordado"));
        FechaSalidaBordadoColumn.setCellValueFactory(new PropertyValueFactory<>("FechaSalidaBordado"));
        diferenciaBColumn.setCellValueFactory(cell -> {
            Bordado fila      = cell.getValue();

            int    Bordada   = Integer.parseInt(fila.getCantidadEntregada());
            int    Cortada= obtenerCantidadCortada(fila.getIdBordado());
            int    diff      = Bordada - Cortada;
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
        bordadoList.clear();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM bordado")) {
            while (rs.next()) {

                long timestampEntrada = rs.getLong("FechaEntradaBordado");
                Date fechaEntrada = new Date(timestampEntrada);

                long timestampSalida = rs.getLong("FechaSalidaBordado");
                Date fechaSalida = new Date(timestampSalida);

                bordadoList.add(new Bordado(
                        rs.getInt("idBordado"),
                        rs.getString("CorteB"),
                        rs.getString("MaquileroBordado"),
                        fechaSalida,
                        fechaEntrada,
                        rs.getString("CostoMaquilaBordado"),
                        rs.getString("CantidadEntregada")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        bordadoTable.setItems(bordadoList);
    }

    @FXML
    private void guardarBordado() {
        if (idBordadoField.getText().isEmpty()) {
            mostrarAlerta("Campos obligatorios", "ID obligatorio.");
            return;
        }
        try {
            int id = Integer.parseInt(idBordadoField.getText());

            if (existeId(id)) {
                mostrarAlerta("ID Existente", "Ya existe un registro con ese ID.");
                return;
            }

            try (Connection conn = ConexionDB.conectar();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO bordado (idBordado, CorteB , MaquileroBordado, FechaEntradaBordado, FechaSalidaBordado, CostoMaquilaBordado, CantidadEntregada)" +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                LocalDate fechaEntrada = FechaEntradaBordadoDate.getValue();
                LocalDate fechaSalida = FechaSalidaBordadoDate.getValue();

                stmt.setInt(1, id);
                stmt.setString(2, CorteBField.getValue());
                stmt.setString(3, MaquileroBordadoField.getValue());
                stmt.setDate(4, fechaEntrada != null ? Date.valueOf(fechaEntrada) : null);
                stmt.setDate(5, fechaSalida != null ? Date.valueOf(fechaSalida) : null);
                stmt.setString(6, CostoMaquilaBordadoField.getText());
                stmt.setString(7, CantidadEntregadaField.getText());

                stmt.executeUpdate();
                mostrarAlerta("Éxito", "Proceso guardado correctamente.");
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
    private void actualizarBordado() {
        if (idBordadoField.getText().isEmpty()) {
            mostrarAlerta("ID requerido", "Selecciona un registro para editar.");
            return;
        }

        String sql = """
            UPDATE bordado SET
              MaquileroBordado=?, CorteB=?, FechaEntradaBordado=?, FechaSalidaBordado=:?, CostoMaquilaBordado=?,
              CantidadEntregada=?
            WHERE idBordado=?
        """.replace(":?", "?");

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, CorteBField.getValue());
            stmt.setString(2, MaquileroBordadoField.getValue());
            stmt.setDate(3, Date.valueOf(FechaEntradaBordadoDate.getValue()));
            stmt.setDate(4, Date.valueOf(FechaSalidaBordadoDate.getValue()));
            stmt.setString(5, CostoMaquilaBordadoField.getText());
            stmt.setString(6, CantidadEntregadaField.getText());
            stmt.setString(7, idBordadoField.getText());

            stmt.executeUpdate();
            mostrarAlerta("Actualizado", "Registro actualizado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo actualizar el proceso.");
        }
    }

    @FXML
    private void eliminarBordado() {
        Bordado sel = bordadoTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAlerta("Sin selección", "Selecciona un registro para eliminar.");
            return;
        }

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM bordado WHERE idBordado=?")) {
            stmt.setInt(1, sel.getIdBordado());
            stmt.executeUpdate();
            mostrarAlerta("Eliminado", "Corte eliminado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo eliminar el registro.");
        }
    }

    private void seleccionarCorte() {
        Bordado m = bordadoTable.getSelectionModel().getSelectedItem();
        if (m != null) {
            idBordadoField.setText(String.valueOf(m.getIdBordado()));
            CorteBField.setValue(m.getCorteB());
            MaquileroBordadoField.setValue(m.getMaquileroBordado());
            FechaEntradaBordadoDate.setValue(((java.sql.Date)m.getFechaEntradaBordado()).toLocalDate());
            FechaSalidaBordadoDate.setValue(((java.sql.Date)m.getFechaSalidaBordado()).toLocalDate());
            CostoMaquilaBordadoField.setText(String.valueOf(m.getCostoMaquilaBordado()));
            CantidadEntregadaField.setText(String.valueOf(m.getCantidadEntregada()));
        }
    }

    private boolean existeId(int id) {
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM bordado WHERE idBordado=?")) {
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
        idBordadoField.clear();
        MaquileroBordadoField.setValue(null);
        FechaEntradaBordadoDate.setValue(null);
        FechaSalidaBordadoDate.setValue(null);
        CostoMaquilaBordadoField.clear();
        CantidadEntregadaField.clear();
    }

    private void cargarCortes() {
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NumCorte FROM cortes")) {

            ObservableList<String> cortes = FXCollections.observableArrayList();
            while (rs.next()) {
                cortes.add(rs.getString("NumCorte"));
            }
            CorteBField.setItems(cortes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarMaquilas() {
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NombreMaquila FROM maquilas WHERE AreaMaquila = 'Bordado'")) {

            ObservableList<String> maquilas = FXCollections.observableArrayList();
            while (rs.next()) {
                maquilas.add(rs.getString("NombreMaquila"));
            }
            MaquileroBordadoField.setItems(maquilas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int obtenerCantidadCortada(int idBordado) {
        String sql = "SELECT Cantidad FROM corte WHERE idCorte = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBordado);
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
