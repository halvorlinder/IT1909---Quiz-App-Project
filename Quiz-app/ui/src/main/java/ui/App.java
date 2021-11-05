package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controllers.LogInController;

import java.io.IOException;

/**
 * JavaFX App
 */
public final class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        final FXMLLoader loader = Utilities.getFXMLLoader("LogInPage.fxml");
        LogInController controller = new LogInController();
        loader.setController(controller);
        final Parent root = loader.load();
        Scene scene = new Scene(root);
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
     * @param args args
     */
    public static void main(String[] args) {
        launch();
    }
}