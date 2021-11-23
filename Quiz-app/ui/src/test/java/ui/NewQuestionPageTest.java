package ui;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.SavePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.constants.Errors;
import ui.controllers.EditPageController;
import ui.controllers.NewQuestionPageController;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class NewQuestionPageTest extends ApplicationTest {


    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    @Override
    public void start(final Stage stage) throws Exception {
        //starts the server and loads the new question page
        SavePaths.enableTestMode();
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("NewQuestionPage.fxml"));
        NewQuestionPageController controller = new NewQuestionPageController("a", new User("user", ""));

        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes/a"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"a\",\"creator\":\"user\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b \",\"c \",\"d \"]}]}")
                        .withStatus(200)));
        //loads editpage and sets it as the previous page
        EditPageController editPageController = new EditPageController("a", new User("", ""));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("EditPage.fxml"));
        loader2.setController(editPageController);
        controller.setPreviousPageInfo(editPageController, loader2.load());

        loader.setController(controller);
        final Parent root = loader.load();
        wireMockServer.stop();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //tests that the correct error is displayed
    // when a quiz with an empty choice is submitted
    @Test
    public void testSubmitEmptyFields() {
        writeQuestion("?", "1", "", "3", "4", 0);
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith(Errors.EMPTY_CHOICE_TEXT)).query();
        });
    }

    //tests that the correct error is displayed
    // when a quiz with an empty question text is submitted
    @Test
    public void testSubmitEmptyQuestion() {
        writeQuestion("", "1", "2", "3", "4", 0);
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith(Errors.EMPTY_QUESTION_TEXT)).query();
        });
    }

    //tests that the correct request is sent when a question is submitted and that
    //the edit page is loaded and that it is displayed correctly
    @Test
    public void testSubmitQuestion() {
        //starts the server
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        //mocks posting and getting a quiz, getting all quizzes
        stubFor(post(urlEqualTo("/api/quizzes/a"))
                .withRequestBody(equalToJson("{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b\",\"c\",\"d\"]}"))
                .willReturn(aResponse()
                        .withBody("[]")
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes/a"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"a\",\"creator\":\"user\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b\",\"c\",\"d\"]}]}")
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[]")));
        stubFor(delete(urlEqualTo("/api/leaderboards/a"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/leaderboards"))
                .willReturn(aResponse()
                        .withBody("[]")
                        .withStatus(200)));
        stubFor(post(urlEqualTo("/api/leaderboards"))
                .withRequestBody(equalToJson("{\"name\":\"a\",\"maxScore\":1,\"scores\":[]}"))
                .willReturn(aResponse()
                        .withBody("[\"a\"]")
                        .withStatus(200)));
        writeQuestion("?", "a", "b", "c", "d", 0);
    }

    //writes a question and submits it
    private void writeQuestion(String question, String choice1, String choice2, String choice3, String choice4, int rightAnswer) {
        clickOn("#questionText").write(question);
        clickOn("#choice1").write(choice1);
        clickOn("#choice2").write(choice2);
        clickOn("#choice3").write(choice3);
        clickOn("#choice4").write(choice4);
        if (rightAnswer >= 0)
            clickOn("#radioButton" + (rightAnswer + 1));
        clickOn("#submitButton");
    }

    //stops the server
    @AfterEach
    public void stopServer() {
        if (wireMockServer != null)
            wireMockServer.stop();
    }
}