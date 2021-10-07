package ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public final class App extends Application {

    private static Scene scene; // **Declare static scene**

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(getFXMLLoader("HomePage.fxml").load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param fxmlFile a string that corresponds to the name of the fxml file
     * @return an FXMLLoader with the given file
     * @throws IOException
     */
    public static FXMLLoader getFXMLLoader(String fxmlFile) throws IOException {
        return new FXMLLoader(App.class.getResource(fxmlFile));
    }

    /**
     * Sets the root
     *
     * @param fxmlFile the name of the fxml file containing the new root
     * @throws IOException
     */
    @FXML
    public static void setRoot(String fxmlFile) throws IOException { // Switches view to desired FXML file
        scene.setRoot(new FXMLLoader((App.class.getResource(fxmlFile))).load());
    }

    /**
     * Sets the root
     *
     * @param loader an FXMLLoader containing the new root
     * @throws IOException
     */
    @FXML
    public static void setRoot(FXMLLoader loader) throws IOException { // Switches view to desired FXML file
        scene.setRoot(loader.load());
    }

    /**
     *
     * @param args args
     */
    public static void main(String[] args) {
        launch();
    }
}