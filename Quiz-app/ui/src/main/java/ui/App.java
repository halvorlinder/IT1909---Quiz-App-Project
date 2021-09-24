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

    /**
     *
     * @param FXMLFile a string that corresponds to the name of the fxml file
     * @return an FXMLLoader with the given file
     * @throws IOException
     */
    public static FXMLLoader getFXMLLoader(String FXMLFile) throws IOException {
        return new FXMLLoader(App.class.getResource(FXMLFile));
    }

    /**
     * Sets the root
     * @param FXMLFile the name of the fxml file containing the new root
     * @throws IOException
     */
    @FXML
    public static void setRoot(String FXMLFile) throws IOException { // Switches view to desired FXML file
        scene.setRoot(new FXMLLoader((App.class.getResource(FXMLFile))).load());
    }

    /**
     * Sets the root
     * @param loader an FXMLLoader containing the new root
     * @throws IOException
     */
    @FXML
    public static void setRoot(FXMLLoader loader) throws IOException { // Switches view to desired FXML file
        scene.setRoot(loader.load());
    }

    public static void main(String[] args) {
        launch();
    }
}