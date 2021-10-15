package ui;

import core.UserData;
import io.UserPersistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.LogInController;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogInTest extends ApplicationTest {

    private UserPersistence userPersistence;

    @Override
    public void start(final Stage stage) throws Exception {
        LogInController logInController = new LogInController("testUsers.json");
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInPageTest.fxml"));
        loader.setController(logInController);
        final Parent root = loader.load();
        userPersistence = new UserPersistence("testUsers.json");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setupItems() throws IOException {
        UserData userData = new UserData();
        userData.attemptRegister("halvor", "password");
        userPersistence.saveUserData(userData);
    }

    @Test
    public void testLogInOut() throws InterruptedException {
        clickOn("#logInUserName").write("halvor");
        clickOn("#logInPassword").write("password");
        clickOn("#logIn");
        Label name = lookup("#nameDisplay").query();
        assertEquals(name.getText(), "Logget inn som halvor");
        clickOn("#signOut");
        Label welcome = lookup("#welcome").query();
        assertEquals(welcome.getText(), "Velkommen til Quiz-appen");
    }

    @Test
    public void testFailedLogIn() throws InterruptedException {
        clickOn("#logInUserName").write("halvor");
        clickOn("#logInPassword").write("password1");
        clickOn("#logIn");
        Thread.sleep(1000);
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith("Brukernavn eller passord er feil")).query();
        });
    }

    @Test
    public void testRegister() {
        clickOn("#registerUserName").write("halvor1");
        clickOn("#registerPassword").write("password");
        clickOn("#register");
        Label name = lookup("#nameDisplay").query();
        assertEquals(name.getText(), "Logget inn som halvor1");
    }

    @Test
    public void testFailedRegister() {
        clickOn("#registerUserName").write("halvor");
        clickOn("#registerPassword").write("password");
        clickOn("#register");
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith("Brukernavn er tatt")).query();
        });
    }
}
