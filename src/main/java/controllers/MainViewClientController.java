package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MainViewClientController {

    

    public Button btnData;
    public Button btnSettings;
    public Button btnBackup;
    public Button btnSalir;
    public Pane Pane2;
    public Pane Pane1;
    @FXML
    private Button btnCliente;

    @FXML
    private Button btnInicio;

    @FXML
    void btnCliente(ActionEvent event) {
        System.out.println("Cliente");
    }

    @FXML
    void btnInicio(ActionEvent event) {
        System.out.println("Inicio");
    }

    public void btnData(ActionEvent actionEvent) {
    }

    public void btnBackup(ActionEvent actionEvent) {
    }

    public void btnSalir(ActionEvent actionEvent) {
    }

    public void btnSettings(ActionEvent actionEvent) {
    }
}
