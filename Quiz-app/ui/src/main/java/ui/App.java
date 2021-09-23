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
        scene = new Scene(loadFXML("HomePage.fxml"));
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String FXMLFile) throws IOException {
        System.out.println(FXMLFile);
        FXMLLoader loader = new FXMLLoader(App.class.getResource(FXMLFile));
        return loader.load();
    }

    @FXML
    public static void setRoot(String FXMLFile) throws IOException { // Switches view to desired FXML file
        scene.setRoot(new FXMLLoader((App.class.getResource(FXMLFile))).load());
    }

    public static void main(String[] args) {
        launch();
    }
}