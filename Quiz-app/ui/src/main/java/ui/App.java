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
     *
     * @param args args
     */
    public static void main(String[] args) {
        launch();
    }
}