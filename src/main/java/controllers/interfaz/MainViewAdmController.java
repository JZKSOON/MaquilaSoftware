package controllers.interfaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainViewAdmController {

    @FXML
    private AnchorPane contentPane;

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleInicio(javafx.event.ActionEvent actionEvent) {
        loadView("/views/InicioView.fxml");
    }

    @FXML
    public void handleCliente(javafx.event.ActionEvent actionEvent) {
        loadView("/views/ClienteView.fxml");
    }

    @FXML
    public void handleMaquilas(javafx.event.ActionEvent actionEvent) {
        loadView("/views/MaquilasView.fxml");
    }

    @FXML
    public void handleConfeccion(javafx.event.ActionEvent actionEvent) {
        loadView("/views/ConfeccionView.fxml");
    }

    @FXML
    public void handleLavanderia(javafx.event.ActionEvent actionEvent) {
        loadView("/views/LavanderiaView.fxml");
    }

    @FXML
    public void handleEmpaque(javafx.event.ActionEvent actionEvent) {
        loadView("/views/EmpaqueView.fxml");
    }

    @FXML
    public void handleRegistro(javafx.event.ActionEvent actionEvent) {
        loadView("/views/RegistroView.fxml");
    }

    @FXML
    public void handleCerrarSesion(javafx.event.ActionEvent actionEvent) {

    }

}