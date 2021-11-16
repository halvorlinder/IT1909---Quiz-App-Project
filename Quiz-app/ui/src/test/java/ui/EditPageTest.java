package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.SavePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.EditPageController;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class EditPageTest extends ApplicationTest {

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    @Override
    public void start(final Stage stage) throws Exception {
        SavePaths.enableTestMode();

        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b\",\"c\",\"d\"]}]}")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPage.fxml"));
        EditPageController editPageController = new EditPageController("x");
        loader.setController(editPageController);
        final Parent root = loader.load();
        wireMockServer.stop();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void startWireMockServerAndSetup() {
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b\",\"c\",\"d\"]}]}")
                        .withStatus(200)));
    }

    @Test
    public void testDataDisplay() {
        VBox vBox = lookup("#questionList").query();
        Assertions.assertDoesNotThrow(() -> {
            from(vBox).lookup((Label label) -> label.getText().equals("?")).query();
        });
        Label label = lookup("#titleText").query();
        Assertions.assertEquals("Endre x", label.getText());
    }

    @Test
    public void testAddQuestion() {
        clickOn("#newQuestionButton");
        Assertions.assertDoesNotThrow(() -> {
            lookup("#headline").query();
        });
    }

    @Test
    public void testDeleteQuestion() {
        stubFor(delete(urlEqualTo("/api/quizzes/x/0"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"questions\":[]}")
                        .withStatus(200)));
        VBox vBox = lookup("#questionList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Slett")).queryButton());
        Assertions.assertEquals(0, vBox.getChildren().size());
    }

    @Test
    public void testDeleteQuiz() {
        stubFor(delete(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(delete(urlEqualTo("/api/leaderboards/x"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[]")));
        clickOn(lookup("#deleteQuizButton").queryButton());
        Assertions.assertDoesNotThrow(() -> lookup("#quizList").query());
        Assertions.assertEquals(0, ((VBox) lookup("#quizList").query()).getChildren().size());
    }

    @Test
    public void testEditQuestion() {
        stubFor(put(urlEqualTo("/api/quizzes/x/0"))
                .withRequestBody(equalToJson("{\"question\":\"??\",\"answer\":0,\"choices\":[\"a\",\"b\",\"c\",\"d\"]}"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(delete(urlEqualTo("/api/leaderboards/x"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")));
        stubFor(post(urlEqualTo("/api/leaderboards"))
                .withRequestBody(equalToJson("{\"name\":\"x\",\"maxScore\":1,\"scores\":[]}"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        VBox vBox = lookup("#questionList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Endre")).queryButton());
        clickOn("#questionText").write("?");
        clickOn("#submitButton");
    }

    @AfterEach
    public void stopServer() {
        wireMockServer.stop();
    }
}
