package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controllers.LogInController;

import java.net.URI;
import java.net.http.HttpClient;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * JavaFX App
 */
public final class App extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        APIClientService apiClientService = new APIClientService();
        apiClientService.getQuiz("quiz101");
        final FXMLLoader loader = Utilities.getFXMLLoader("LogInPage.fxml");
        LogInController controller = new LogInController();
        loader.setController(controller);
        final Parent root = loader.load();
        Scene scene = new Scene(root);
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

