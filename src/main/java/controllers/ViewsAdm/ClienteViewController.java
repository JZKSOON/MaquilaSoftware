package controllers.ViewsAdm;

import database.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cliente;

import java.sql.*;

public class ClienteViewController {

    @FXML private TextField idField, razonSocialField, nombreField, celularField, municipioField, cpField, emailField, telefonoField;
    @FXML private TextArea direccionArea;
    @FXML private ComboBox<String> estadoComboBox;
    @FXML private TableView<Cliente> clienteTable;
    @FXML private TableColumn<Cliente, Integer> idColumn;
    @FXML private TableColumn<Cliente, String> razonSocialColumn, nombreColumn, celularColumn, direccionColumn, estadoColumn, municipioColumn, cpColumn, emailColumn, telefonoColumn;

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ConexionDB.inicializar();

        estadoComboBox.setItems(FXCollections.observableArrayList(
                "Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas", "Chihuahua", "CDMX",
                "Coahuila", "Colima", "Durango", "Estado de México", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco",
                "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo",
                "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas"
        ));

        configurarColumnas();
        cargarDatos();

        // Detectar selección en la tabla
        clienteTable.setOnMouseClicked(event -> seleccionarCliente());
    }

    private void configurarColumnas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        razonSocialColumn.setCellValueFactory(new PropertyValueFactory<>("razonSocial"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        celularColumn.setCellValueFactory(new PropertyValueFactory<>("celular"));
        direccionColumn.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        municipioColumn.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        cpColumn.setCellValueFactory(new PropertyValueFactory<>("cp"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }

    private void cargarDatos() {
        clientes.clear();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM clientes")) {
            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("razonSocial"),
                        rs.getString("nombre"),
                        rs.getString("celular"),
                        rs.getString("direccion"),
                        rs.getString("estado"),
                        rs.getString("municipio"),
                        rs.getString("cp"),
                        rs.getString("email"),
                        rs.getString("telefono")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clienteTable.setItems(clientes);
    }

    @FXML
    private void guardarCliente() {
        if (idField.getText().isEmpty() || nombreField.getText().isEmpty()) {
            mostrarAlerta("Campos obligatorios", "El campo ID y Nombre son obligatorios.");
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText());

            if (idExiste(id)) {
                mostrarAlerta("ID Existente", "Ya existe un cliente con ese ID.");
                return;
            }

            try (Connection conn = ConexionDB.conectar();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO clientes (id, razonSocial, nombre, celular, direccion, estado, municipio, cp, email, telefono) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setInt(1, id);
                stmt.setString(2, razonSocialField.getText());
                stmt.setString(3, nombreField.getText());
                stmt.setString(4, celularField.getText());
                stmt.setString(5, direccionArea.getText());
                stmt.setString(6, estadoComboBox.getValue());
                stmt.setString(7, municipioField.getText());
                stmt.setString(8, cpField.getText());
                stmt.setString(9, emailField.getText());
                stmt.setString(10, telefonoField.getText());

                stmt.executeUpdate();
                mostrarAlerta("Éxito", "Cliente guardado correctamente.");
                limpiarCampos();
                cargarDatos();
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Formato inválido", "El ID debe ser un número.");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error BD", "No se pudo guardar el cliente.");
        }
    }

    @FXML
    private void actualizarCliente() {
        if (idField.getText().isEmpty()) {
            mostrarAlerta("ID requerido", "Debe seleccionar un cliente para editar.");
            return;
        }

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE clientes SET razonSocial=?, nombre=?, celular=?, direccion=?, estado=?, municipio=?, cp=?, email=?, telefono=? WHERE id=?")) {
            stmt.setString(1, razonSocialField.getText());
            stmt.setString(2, nombreField.getText());
            stmt.setString(3, celularField.getText());
            stmt.setString(4, direccionArea.getText());
            stmt.setString(5, estadoComboBox.getValue());
            stmt.setString(6, municipioField.getText());
            stmt.setString(7, cpField.getText());
            stmt.setString(8, emailField.getText());
            stmt.setString(9, telefonoField.getText());
            stmt.setInt(10, Integer.parseInt(idField.getText()));

            stmt.executeUpdate();
            mostrarAlerta("Actualizado", "Cliente actualizado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo actualizar el cliente.");
        }
    }

    @FXML
    private void eliminarCliente() {
        Cliente clienteSeleccionado = clienteTable.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Sin selección", "Por favor selecciona un cliente para eliminar.");
            return;
        }

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM clientes WHERE id = ?")) {
            stmt.setInt(1, clienteSeleccionado.getId());
            stmt.executeUpdate();
            mostrarAlerta("Eliminado", "Cliente eliminado correctamente.");
            limpiarCampos();
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo eliminar el cliente.");
        }
    }

    private void seleccionarCliente() {
        Cliente cliente = clienteTable.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            idField.setText(String.valueOf(cliente.getId()));
            razonSocialField.setText(cliente.getRazonSocial());
            nombreField.setText(cliente.getNombre());
            celularField.setText(cliente.getCelular());
            direccionArea.setText(cliente.getDireccion());
            estadoComboBox.setValue(cliente.getEstado());
            municipioField.setText(cliente.getMunicipio());
            cpField.setText(cliente.getCp());
            emailField.setText(cliente.getEmail());
            telefonoField.setText(cliente.getTelefono());
        }
    }

    private boolean idExiste(int id) {
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM clientes WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        idField.clear();
        razonSocialField.clear();
        nombreField.clear();
        celularField.clear();
        direccionArea.clear();
        estadoComboBox.getSelectionModel().clearSelection();
        municipioField.clear();
        cpField.clear();
        emailField.clear();
        telefonoField.clear();
    }
}
