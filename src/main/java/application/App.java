package application;

import javafx.application.Application;
import database.ConexionDB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Paths;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Iniciar con Ventana de Login y base de datos
        database.ConexionDB.inicializar();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.LoginView));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }

}
