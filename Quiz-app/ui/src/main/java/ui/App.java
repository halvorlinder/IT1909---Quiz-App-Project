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
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("HomePage.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    private static Parent loadFXML(String FXMLfile) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(FXMLfile));
        return loader.load();
    }

    @FXML
    public static void setRoot(String FXMLfile) throws IOException { // Switches view to desired FXML file
        scene.setRoot(loadFXML(FXMLfile));
    }

    public static void main(String[] args) {
        launch();
    }
}