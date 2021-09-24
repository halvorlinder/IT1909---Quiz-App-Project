package ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene; // **Declare static scene**

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(getFXMLLoader("HomePage.fxml").load());
        stage.setScene(scene);
        stage.show();
    }

    public static FXMLLoader getFXMLLoader(String FXMLFile) throws IOException {
        return new FXMLLoader(App.class.getResource(FXMLFile));
    }

    @FXML
    public static void setRoot(String FXMLFile) throws IOException { // Switches view to desired FXML file
        scene.setRoot(new FXMLLoader((App.class.getResource(FXMLFile))).load());
    }
    @FXML
    public static void setRoot(FXMLLoader loader) throws IOException { // Switches view to desired FXML file
        scene.setRoot(loader.load());
    }

    public static void main(String[] args) {
        launch();
    }
}