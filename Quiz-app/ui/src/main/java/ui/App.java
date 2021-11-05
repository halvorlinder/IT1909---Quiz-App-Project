package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public final class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(getFXMLLoader("LogInPage.fxml").load());
        String css = this.getClass().getResource("css/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param fxmlFile a string that corresponds to the name of the fxml file
     * @return an FXMLLoader with the given file
     * @throws IOException if FXMLLoader cant get resource
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