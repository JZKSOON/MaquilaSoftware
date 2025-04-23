package controllers.interfaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewAdmController implements Initializable {

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
        loadView("/Views/ViewsAdm/InicioView.fxml");
    }

    //Carga Los graficos del boton Inicio desde que se abre la view
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Simula el clic del botón al iniciar
        handleInicio(new javafx.event.ActionEvent());
    }

    @FXML
    public void handleCliente(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/ClienteView.fxml");
    }

    @FXML
    public void handleMaquilas(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/MaquilasView.fxml");
    }

    @FXML
    public void handleConfeccion(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/ConfeccionView.fxml");
    }

    @FXML
    public void handleLavanderia(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/LavanderiaView.fxml");
    }

    @FXML
    public void handleEmpaque(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/EmpaqueView.fxml");
    }

    @FXML
    public void handleRegistro(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/RegistroView.fxml");
    }

    @FXML
    public void handleCerrarSesion(javafx.event.ActionEvent actionEvent) {

    }
}