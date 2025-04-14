package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainViewClientController {

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

}
