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
import ui.controllers.HomePageController;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class EditPageTest extends ApplicationTest {

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    @Override
    public void start(final Stage stage) throws Exception {
        SavePaths.enableTestMode();

        //Set up mock server to allow the initialization of the Editpage
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"creator\":\"user\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b\",\"c\",\"d\"]}]}")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPage.fxml"));
        EditPageController editPageController = new EditPageController("x", new User("user", ""));

        //Set previous page to Homepage
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")));
        HomePageController homePageController = new HomePageController(new User("", ""));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        loader2.setController(homePageController);
        editPageController.setPreviousPageInfo(homePageController, loader2.load());

        //Load the page and start the server
        loader.setController(editPageController);
        final Parent root = loader.load();
        wireMockServer.stop();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void startWireMockServerAndSetup() {
        //start wiremock and mock fetching quiz 'x'
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"creator\":\"user\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b\",\"c\",\"d\"]}]}")
                        .withStatus(200)));
    }

    //Tests that the data from the quiz is displayed correctly
    @Test
    public void testDataDisplay() {
        VBox vBox = lookup("#questionList").query();
        Assertions.assertDoesNotThrow(() -> {
            from(vBox).lookup((Label label) -> label.getText().equals("?")).query();
        });
        Label label = lookup("#titleText").query();
        Assertions.assertEquals("Endre x", label.getText());
    }

    //Tests that the add button loads the newQuestionPage successfully
    @Test
    public void testAddQuestion() {
        clickOn("#newQuestionButton");
        Assertions.assertDoesNotThrow(() -> {
            lookup("#headline").query();
        });
    }

    //Tests that the frontend sends the correct request to the api when a question
    //is deleted and that the ui is updated correctly
    @Test
    public void testDeleteQuestion() {
        //sets up mock responses for deletion and fetching
        stubFor(delete(urlEqualTo("/api/quizzes/x/0"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"creator\":\"user\",\"questions\":[]}")
                        .withStatus(200)));
        //deletes and checks ui
        VBox vBox = lookup("#questionList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Slett")).queryButton());
        Assertions.assertEquals(0, vBox.getChildren().size());
    }

    //Tests that the correct request is sent when deleting a quiz and that the homepage is
    //loaded correctly
    @Test
    public void testDeleteQuiz() {
        //sets up mocks for deleting and fetching quizzes
        stubFor(delete(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(delete(urlEqualTo("/api/leaderboards/x"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[]")));
        //Deletes and checks that the homepage is rendered correctly
        clickOn(lookup("#deleteQuizButton").queryButton());
        Assertions.assertDoesNotThrow(() -> lookup("#quizList").query());
        Assertions.assertEquals(0, ((VBox) lookup("#quizList").query()).getChildren().size());
    }

    //Tests that submitting an edited question sends the correct request
    @Test
    public void testEditQuestion() {
        //sets up mocks for editing a question and retrieving the new quiz
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
        //edits the question
        VBox vBox = lookup("#questionList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Endre")).queryButton());
        clickOn("#questionText").write("?");
        clickOn("#submitButton");
    }

    //stops the mock server
    @AfterEach
    public void stopServer() {
        wireMockServer.stop();
    }
}
