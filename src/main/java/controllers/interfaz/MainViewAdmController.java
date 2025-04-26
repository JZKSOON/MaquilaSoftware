package controllers.interfaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MainViewAdmController implements Initializable {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private VBox sidebar;

    private boolean sidebarVisible = true;

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();

            //Hace que las views se configuren al anchor pane
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

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

    //Metodos para mostrar las views de los botones
    @FXML
    public void handleCliente(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/ClienteView.fxml");
    }

    @FXML
    public void handleMaquilas(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/MaquilasView.fxml");
    }

    @FXML
    public void handleCortes(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/CortesView.fxml");
    }

    @FXML
    public void handleCorte(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/CorteView.fxml");
    }

    @FXML
    public void handleBordado(javafx.event.ActionEvent actionEvent) {
        loadView("/Views/ViewsAdm/BordadoView.fxml");
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
    public void handleCerrarSesion(javafx.event.ActionEvent actionEvent) {

    }
    /*Metodo para ocultar botones
    @FXML
    private void handleToggleSidebar() {
        if (sidebarVisible) {
            sidebar.setPrefWidth(50); // reducido
            // Aquí podrías ocultar texto de los botones si usas etiquetas
        } else {
            sidebar.setPrefWidth(200); // ancho completo normal
            // Mostrar texto de los botones de nuevo
        }
        sidebarVisible = !sidebarVisible;
    }*/

}