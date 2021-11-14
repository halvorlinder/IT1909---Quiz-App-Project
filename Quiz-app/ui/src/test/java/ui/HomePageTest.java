package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.SavePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.HomePageController;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class HomePageTest extends ApplicationTest {

    private FXMLLoader loader;

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    @Override
    public void start(final Stage stage) throws Exception {
        SavePaths.enableTestMode();
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[]")));
        loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        final Parent root = loader.load();
        wireMockServer.stop();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void initQuiz() {
        stubFor(post(urlEqualTo("/api/quizzes"))
                .withRequestBody(equalToJson("{\"name\":\"x\",\"questions\":[]}"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        clickOn("#quizNameField").write("x");
        clickOn("#addNewQuizButton");
    }

    @BeforeEach
    public void startWireMockServerAndSetup() throws IOException, InterruptedException {
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
    }


    @Test
    public void testCreateEmptyNameQuiz() {
        HomePageController controller = loader.getController();
        Assertions.assertThrows(IllegalArgumentException.class, controller::addNewQuizFile);
    }

    @Test
    public void testCreateQuiz() {
        initQuiz();
        VBox vBox = lookup("#quizList").query();
        Assertions.assertDoesNotThrow(() -> {
            from(vBox).lookup((Label label) -> label.getText().equals("x")).query();
        });
    }

    @Test
    public void testStartEmptyQuiz() {
        initQuiz();
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"questions\":[]}")
                        .withStatus(200)));
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Spill")).queryButton());
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith("Denne quizen har ingen spørsmål")).query();
        });
    }

    @Test
    public void testStartQuiz() {
        initQuiz();
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b \",\"c \",\"d \"]}]}")
                        .withStatus(200)));
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Spill")).queryButton());
        Assertions.assertDoesNotThrow(() -> {
            lookup("#questionLabel").query();
        });
    }

    @Test
    public void testTraversal() {
        initQuiz();
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"questions\":[]}")
                        .withStatus(200)));
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Endre")).queryButton());
        Assertions.assertDoesNotThrow(()->lookup("#newQuestionButton").query());
        clickOn(lookup("#newQuestionButton").queryButton());
        clickOn(lookup("#backButton").queryButton());
        clickOn(lookup("#backButton").queryButton());
        Assertions.assertDoesNotThrow(()->lookup("#nameDisplay").query());
    }

    @AfterEach
    public void stopServer() {
        wireMockServer.stop();
    }

}
