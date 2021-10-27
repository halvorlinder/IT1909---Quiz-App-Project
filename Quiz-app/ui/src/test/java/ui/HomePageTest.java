package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.HomePageController;


public class HomePageTest extends ApplicationTest {

    private FXMLLoader loader;

    @Override
    public void start(final Stage stage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testCreateEmptyNameQuiz() {
        HomePageController controller = loader.getController();
        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.addNewQuiz());
    }

}
