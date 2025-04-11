package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController
{
    @FXML
    private TextField TxtUsuario;

    @FXML
    private PasswordField TxtContraseña;

    @FXML
    private Button BtnIngresar;

    @FXML
    private void initialize()
    {

    }

    @FXML
    private void ClickBoton(javafx.event.ActionEvent event) {
        String usuario = TxtUsuario.getText();
        String contraseña = TxtContraseña.getText();

        if (usuario.equals("admin")&& contraseña.equals("1234")){
            //AbrirMainView dependiendo si es administrador o usuario
            mostrarAlerta("Acceso Correcto", "Ingresando como administrador");
            Stage stage = (Stage) BtnIngresar.getScene().getWindow();
            try {
                AbrirViewAdmin((Stage) ((Node) event.getSource()).getScene().getWindow());
            } catch (Exception e) {}
        }
        else {
            mostrarAlerta("Acceso Denegado","Usuario o contraseña incorrecto");
        }
    }
    private void mostrarAlerta(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    public void AbrirViewAdmin(Stage window) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainViewAdm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Vista Administrador");
        stage.setMaximized(true);
        stage.show();

        //No necesario
        Stage loginStage = (Stage) BtnIngresar.getScene().getWindow();
        loginStage.close();
    }

}
