package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import core.UserData;
import core.UserRecord;
import io.UserPersistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.LogInController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogInTest extends ApplicationTest {

    private UserPersistence userPersistence;

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    @Override
    public void start(final Stage stage) throws Exception {
        File file = new File(System.getProperty("user.home") + "/QuizApp/testUsers.json");
        userPersistence = new UserPersistence("testUsers.json");
        if (!file.exists())
            file.createNewFile();
        UserData userData = new UserData();
        UserRecord userRecord = new UserRecord("h","p");
        userData.attemptRegister(userRecord);
        userPersistence.saveUserData(userData);
        LogInController logInController = new LogInController();
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInPage.fxml"));
        loader.setController(logInController);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @AfterAll
    public static void deleteFile() {
        String fileName = System.getProperty("user.home") + "/QuizApp/testUsers.json";
        try {
            Files.delete(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setupItems() throws IOException {
        UserData userData = new UserData();
        UserRecord userRecord = new UserRecord("h","p");
        userData.attemptRegister(userRecord);
        userPersistence.saveUserData(userData);

        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[]")));
    }

    @Test
    public void testLogInOut() throws InterruptedException {
        clickOn("#logInUserName").write("h");
        clickOn("#logInPassword").write("p");
        clickOn("#logIn");
        Label name = lookup("#nameDisplay").query();
        assertEquals(name.getText(), "Logget inn som h");
        clickOn("#signOut");
        Label welcome = lookup("#welcome").query();
        assertEquals(welcome.getText(), "Velkommen til Quiz-appen");
    }

    @Test
    public void testFailedLogIn() throws InterruptedException {
        clickOn("#logInUserName").write("h");
        clickOn("#logInPassword").write("q");
        clickOn("#logIn");
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith("Brukernavn eller passord er feil")).query();
        });
    }

    @Test
    public void testRegister() {
        clickOn("#registerUserName").write("i");
        clickOn("#registerPassword").write("p");
        clickOn("#register");
        Label name = lookup("#nameDisplay").query();
        assertEquals(name.getText(), "Logget inn som i");
    }

    @Test
    public void testFailedRegister() {
        clickOn("#registerUserName").write("h");
        clickOn("#registerPassword").write("p");
        clickOn("#register");
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith("Brukernavn er tatt")).query();
        });
    }

    @AfterEach
    public void stopServer() {
        wireMockServer.stop();
    }
}
