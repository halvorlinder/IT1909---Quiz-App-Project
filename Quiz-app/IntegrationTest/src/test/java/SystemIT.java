import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.App;
import ui.controllers.LoginPageController;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SystemIT extends ApplicationTest {

    @Override
    public void start(final Stage stage) throws Exception {
        LoginPageController logInPageController = new LoginPageController();
        final FXMLLoader loader = App.getFXMLLoader("LoginPage.fxml");
        loader.setController(logInPageController);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testSystem(){
        assertDoesNotThrow(()->lookup("#logIn").query());
    }
}
