package controllers.interfaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Paths;

import java.io.IOException;

public class LoginViewController {
    @FXML private TextField TxtUsuario;
    @FXML private PasswordField TxtContraseña;
    @FXML private Button BtnIngresar;

    @FXML
    private void initialize() {
        // Inicialización si es necesaria
    }

    @FXML
    private void BtnIngresar(javafx.event.ActionEvent event) {
        String usuario = TxtUsuario.getText();
        String contraseña = TxtContraseña.getText();

        if (usuario.equals("1") && contraseña.equals("1")) {
            mostrarAlerta("Acceso Correcto", "Ingresando como administrador");
            try {
                AbrirViewAdmin((Stage) ((Node) event.getSource()).getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (usuario.equals("cliente") && contraseña.equals("1234")) {
            mostrarAlerta("Acceso Correcto", "Ingresando como cliente");
            try {
                AbrirViewMaq((Stage) ((Node) event.getSource()).getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Acceso Denegado", "Usuario o contraseña incorrecto");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void AbrirViewAdmin(Stage loginStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.MainViewAdm));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        // Aplicar CSS también en la vista de administrador
        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Vista Administrador");
        stage.setMaximized(true);
        stage.show();

        // Cerrar login
        loginStage.close();
    }

    public void AbrirViewMaq(Stage loginStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.MainMaqView));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        // Aplicar CSS también en la vista de cliente
        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Vista Cliente");
        stage.setMaximized(true);
        stage.show();

        // Cerrar login
        loginStage.close();
    }
}