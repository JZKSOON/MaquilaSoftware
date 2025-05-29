package controllers.interfaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainViewAdmController implements Initializable {

    @FXML private AnchorPane contentPane;
    @FXML private VBox sidebar;

    // Todos los botones del sidebar
    @FXML private Button btnInicio;
    @FXML private Button btnCliente;
    @FXML private Button btnMaquilas;
    @FXML private Button btnCortes;
    @FXML private Button btnCorte;
    @FXML private Button btnBordado;
    @FXML private Button btnConfeccion;
    @FXML private Button btnLavanderia;
    @FXML private Button btnEmpaque;
    @FXML private Button btnCerrarSesion;

    private List<Button> sidebarButtons;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Creamos la lista con todos los botones
        sidebarButtons = List.of(
                btnInicio, btnCliente, btnMaquilas, btnCortes, btnCorte,
                btnBordado, btnConfeccion, btnLavanderia, btnEmpaque, btnCerrarSesion
        );

        // Seleccionamos “Inicio” por defecto
        handleInicio(null);
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();

            // Anclamos la nueva vista al contentPane
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
            contentPane.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Marca el botón activo y desmarca los demás */
    private void selectButton(Button active) {
        for (Button b : sidebarButtons) {
            b.getStyleClass().removeAll("button-active", "button-inactive");
            if (b.equals(active)) {
                b.getStyleClass().add("button-active");
            } else {
                b.getStyleClass().add("button-inactive");
            }
        }
    }

    @FXML
    public void handleInicio(javafx.event.ActionEvent actionEvent) {
        selectButton(btnInicio);
        loadView("/Views/ViewsAdm/InicioView.fxml");
    }

    @FXML
    public void handleCliente(javafx.event.ActionEvent actionEvent) {
        selectButton(btnCliente);
        loadView("/Views/ViewsAdm/ClienteView.fxml");
    }

    @FXML
    public void handleMaquilas(javafx.event.ActionEvent actionEvent) {
        selectButton(btnMaquilas);
        loadView("/Views/ViewsAdm/MaquilasView.fxml");
    }

    @FXML
    public void handleCortes(javafx.event.ActionEvent actionEvent) {
        selectButton(btnCortes);
        loadView("/Views/ViewsAdm/CortesView.fxml");
    }

    @FXML
    public void handleCorte(javafx.event.ActionEvent actionEvent) {
        selectButton(btnCorte);
        loadView("/Views/ViewsAdm/CorteView.fxml");
    }

    @FXML
    public void handleBordado(javafx.event.ActionEvent actionEvent) {
        selectButton(btnBordado);
        loadView("/Views/ViewsAdm/BordadoView.fxml");
    }

    @FXML
    public void handleConfeccion(javafx.event.ActionEvent actionEvent) {
        selectButton(btnConfeccion);
        loadView("/Views/ViewsAdm/ConfeccionView.fxml");
    }

    @FXML
    public void handleLavanderia(javafx.event.ActionEvent actionEvent) {
        selectButton(btnLavanderia);
        loadView("/Views/ViewsAdm/LavanderiaView.fxml");
    }

    @FXML
    public void handleEmpaque(javafx.event.ActionEvent actionEvent) {
        selectButton(btnEmpaque);
        loadView("/Views/ViewsAdm/EmpaqueView.fxml");
    }

    @FXML
    public void handleCerrarSesion(javafx.event.ActionEvent actionEvent) {
        selectButton(btnCerrarSesion);

        // Mostrar diálogo de confirmación
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Cerrar Sesión");
        confirmDialog.setHeaderText("¿Estás seguro de que deseas cerrar sesión?");

        // Personalizar botones del diálogo
        ButtonType btnSi = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmDialog.getButtonTypes().setAll(btnSi, btnNo);

        // Mostrar diálogo y esperar respuesta
        Optional<ButtonType> result = confirmDialog.showAndWait();

        if (result.isPresent() && result.get() == btnSi) {
            try {
                // Obtener la ventana actual
                Stage currentStage = (Stage) btnCerrarSesion.getScene().getWindow();

                // Cargar la vista de login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Interfaz/LoginView.fxml"));
                // Si usas una clase de rutas como en tu ejemplo:
                // FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.LoginView));
                Parent root = loader.load();

                // Crear nueva escena
                Scene scene = new Scene(root);

                // Aplicar CSS si lo tienes
                scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());

                // Crear una nueva Stage para el login
                Stage loginStage = new Stage();
                loginStage.setScene(scene);
                loginStage.setTitle("Inicio de Sesión");

                // Configurar dimensiones (puedes elegir entre estas opciones)
                // Opción 1: Maximizar
                loginStage.setMaximized(true);

                // Opción 2: Usar dimensiones específicas
                // loginStage.setWidth(800);  // Ajusta según necesites
                // loginStage.setHeight(600); // Ajusta según necesites

                // Opción 3: Centrar en la pantalla
                loginStage.centerOnScreen();

                // Mostrar la ventana de login
                loginStage.show();

                // Cerrar la ventana actual
                currentStage.close();

                // Opcional: Limpiar cualquier dato de sesión
                // SesionUsuario.getInstance().cerrarSesion();

            } catch (IOException e) {
                // Manejar errores de carga de la vista
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Error al cerrar sesión");
                errorAlert.setContentText("No se pudo cargar la pantalla de inicio de sesión: " + e.getMessage());
                errorAlert.showAndWait();
                e.printStackTrace();
            }
        }
    }
}
