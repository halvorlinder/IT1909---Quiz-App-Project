package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
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
import ui.constants.Errors;
import ui.controllers.HomePageController;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class HomePageTest extends ApplicationTest {

    private FXMLLoader loader;

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    @Override
    public void start(final Stage stage) throws Exception {
        //starts the server, sets up mock for fetching quizzes and
        //loads the homepage
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[]")));
        loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        HomePageController homePageController = new HomePageController(new User("user", ""));
        loader.setController(homePageController);
        final Parent root = loader.load();
        wireMockServer.stop();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //sets up mocks for posting a quiz, getting quizzes, and getting leaderboards,
    //and creates a quiz
    private void initQuiz() {
        stubFor(post(urlEqualTo("/api/quizzes"))
                .withRequestBody(equalToJson("{\"name\":\"x\",\"creator\":\"user\",\"questions\":[]}"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        stubFor(post(urlEqualTo("/api/leaderboards"))
                .withRequestBody(equalToJson("{\"name\":\"x\",\"maxScore\":0,\"scores\":[]}"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/leaderboards"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        clickOn("#quizNameField").write("x");
        clickOn("#addNewQuizButton");
    }

    //starts the server
    @BeforeEach
    public void startWireMockServerAndSetup() throws IOException, InterruptedException {
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
    }


    //tests if the correct error message is displayed when an empty name quiz is
    //attempted created
    @Test
    public void testCreateEmptyNameQuiz() {
        clickOn("#addNewQuizButton");
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith(Errors.EMPTY_NAME)).query();
        });
    }

    // tests if the correct request is sent when creating a quiz
    // and that the ui is updated correctly
    @Test
    public void testCreateQuiz() {
        initQuiz();
        VBox vBox = lookup("#quizList").query();
        Assertions.assertDoesNotThrow(() -> {
            from(vBox).lookup((Label label) -> label.getText().equals("x")).query();
        });
    }

    //tests that the correct error message is displayed when a questionless
    // quiz is attempted started
    @Test
    public void testStartEmptyQuiz() {
        initQuiz();
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"creator\":\"user\",\"questions\":[]}")
                        .withStatus(200)));
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Spill")).queryButton());
        //checks the error message
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith(Errors.EMPTY_QUIZ)).query();
        });
    }

    //tests that the correct request is sent when a quiz is started and that the quiz page
    //is loaded and displayed correctly
    @Test
    public void testStartQuiz() {
        initQuiz();
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"creator\":\"user\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b \",\"c \",\"d \"]}]}")
                        .withStatus(200)));
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Spill")).queryButton());
        Assertions.assertDoesNotThrow(() -> {
            lookup("#questionLabel").query();
        });
    }

    //tests the back system by traversing from homepage to editpage to newQuestionpage
    //and back to homepage again.
    @Test
    public void testTraversal() {
        initQuiz();
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"creator\":\"user\",\"questions\":[]}")
                        .withStatus(200)));
        //traverses
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Endre")).queryButton());
        Assertions.assertDoesNotThrow(() -> lookup("#newQuestionButton").query());
        clickOn(lookup("#newQuestionButton").queryButton());
        clickOn(lookup("#backButton").queryButton());
        clickOn(lookup("#backButton").queryButton());
        //checks that the homepage is loaded
        Assertions.assertDoesNotThrow(() -> lookup("#nameDisplay").query());
    }

    //stops the server
    @AfterEach
    public void stopServer() {
        wireMockServer.stop();
    }

}
