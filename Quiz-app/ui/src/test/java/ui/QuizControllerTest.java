package ui;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.Quiz;
import io.QuizPersistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import ui.controllers.QuizController;

public class QuizControllerTest extends ApplicationTest {

    private QuizPersistence quizPersistence;
    private Quiz quiz;
    private QuizController controller;
    private int rightAnswer;

    @Override
    public void start(final Stage stage) throws Exception {
        quizPersistence = new QuizPersistence();
        try {
            this.quiz = quizPersistence.readQuiz(new InputStreamReader(getClass().getResourceAsStream("test-newQuestion.json")));
        } catch (IOException e) {
            fail("Couldn't load test-newQuestion.json");
        }
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionPage.fxml"));
        this.controller = new QuizController("test-newQuestion");
        loader.setController(this.controller);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void answerQuizCorrect(){
        rightAnswer = quiz.getQuestions().get(0).getAnswer();
        clickOn("#option"+(rightAnswer+1));
        clickOn("#submitAnswer");
        assertEquals(1, quiz.getCorrect());
    }

    @Test
    public void answerQuizWrong(){
        rightAnswer = quiz.getQuestions().get(0).getAnswer();
        clickOn("#option"+(rightAnswer));
        clickOn("#submitAnswer");
        assertEquals(0, quiz.getCorrect());
    }

}
