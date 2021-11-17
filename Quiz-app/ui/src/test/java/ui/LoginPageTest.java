package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import core.UserData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.LoginPageController;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPageTest extends ApplicationTest {


    private WireMockServer wireMockServer;

    @Override
    public void start(final Stage stage) throws Exception {
        LoginPageController logInPageController = new LoginPageController();
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        loader.setController(logInPageController);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setupItems() {
        WireMockConfiguration config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(post(urlEqualTo("/api/users/register"))
                .withRequestBody(equalToJson("{\"username\":\"h\", \"password\":" + UserData.hash("p") + "}"))
                .willReturn(aResponse()
                        .withStatus(403)));
        stubFor(post(urlEqualTo("/api/users/login"))
                .withRequestBody(equalToJson("{\"username\":\"h\", \"password\":" + UserData.hash("p") + "}"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(post(urlEqualTo("/api/users/register"))
                .withRequestBody(equalToJson("{\"username\":\"i\", \"password\":" + UserData.hash("p") + "}"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(post(urlEqualTo("/api/users/login"))
                .withRequestBody(equalToJson("{\"username\":\"h\", \"password\":" + UserData.hash("q") + "}"))
                .willReturn(aResponse()
                        .withStatus(403)));
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
    }

    @Test
    public void testLogInOut() {
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
    public void testFailedLogIn() {
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
