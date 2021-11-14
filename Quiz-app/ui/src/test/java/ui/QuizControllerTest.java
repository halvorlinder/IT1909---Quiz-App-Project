package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import core.Quiz;
import core.User;
import io.QuizPersistence;
import io.SavePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.QuizController;

import java.io.IOException;
import java.io.InputStreamReader;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class QuizControllerTest extends ApplicationTest {

    private QuizPersistence quizPersistence;
    private Quiz quiz;
    private QuizController controller;
    private int rightAnswer;

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    @Override
    public void start(final Stage stage) throws Exception {
        SavePaths.enableTestMode();
        quizPersistence = new QuizPersistence();
        User.setUserName("test");
        try {
            this.quiz = quizPersistence.readQuiz(new InputStreamReader(getClass().getResourceAsStream("test-newQuestion.json")));
        } catch (IOException e) {
            fail("Couldn't load test-newQuestion.json");
        }
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionPage.fxml"));
        this.controller = new QuizController(quiz);
        loader.setController(this.controller);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void answerQuizCorrect() {
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(post(urlEqualTo("/api/leaderboards/quiz_test"))
                .withRequestBody(equalToJson("{\"name\":\"" + User.getUserName() + "\",\"points\":1}"))
                .willReturn(aResponse()
                        .withBody("[\"quiz_test\"]")
                        .withStatus(200)));
        rightAnswer = quiz.getQuestions().get(0).getAnswer();
        clickOn("#option" + (rightAnswer + 1));
        clickOn("#submitAnswer");
        assertEquals(1, quiz.getCorrect());
    }

    @Test
    public void answerQuizWrong() {
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        stubFor(post(urlEqualTo("/api/leaderboards/quiz_test"))
                .withRequestBody(equalToJson("{\"name\":\"" + User.getUserName() + "\",\"points\":0}"))
                .willReturn(aResponse()
                        .withBody("[\"quiz_test\"]")
                        .withStatus(200)));
        rightAnswer = quiz.getQuestions().get(0).getAnswer();
        clickOn("#option" + (rightAnswer));
        clickOn("#submitAnswer");
        assertEquals(0, quiz.getCorrect());
    }

    @AfterEach
    public void stopServer() {
        if (wireMockServer != null)
            wireMockServer.stop();
    }

}
