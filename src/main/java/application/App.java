package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Iniciar con Ventana de Login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Interfaz/LoginView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }
}
