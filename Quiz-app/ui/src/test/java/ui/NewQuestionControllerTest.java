package ui;


import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.Quiz;
import io.QuizPersistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


import ui.controllers.NewQuestionController;

import static org.junit.jupiter.api.Assertions.*;

public class NewQuestionControllerTest extends ApplicationTest {

    private NewQuestionController controller;
    private QuizPersistence quizPersistence;
    private String question;
    private String choice1, choice2, choice3, choice4;
    private Quiz quiz = null;
    private int rightAnswer;

    @Override
    public void start(final Stage stage) throws Exception {
        quizPersistence = new QuizPersistence();
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("NewQuestionTest.fxml"));
        this.controller = new NewQuestionController("testNewQuestion");
        loader.setController(this.controller);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @BeforeEach
    public void setupItems() {
        try {
            quiz = quizPersistence.readQuiz(new InputStreamReader(getClass().getResourceAsStream("test-newQuestion.json")));
        } catch (IOException e) {
            fail("Couldn't load test-newQuestion.json");
        }
        assertNotNull(quiz);
        writeQuestion("How many days in a year?", "121", "354", "360", "365", 3);
    }

    @Test
    public void testSubmitEmptyFields() {
        clickOn("#questionText").write(question);
        clickOn("#radioButton1");
        for(int i=1; i<5; i++){
            clickOn("#choice"+i).write("");
        }
        // Assertions.assertThrows(IllegalStateException.class, () -> {clickOn("#submitButton");});
    }

    @Test
    public void testQuestionList(){
        clickOn("#questionText").write(question);
        clickOn("#choice1").write(choice1);
        clickOn("#choice2").write(choice2);
        clickOn("#choice3").write(choice3);
        clickOn("#choice4").write(choice4);
        clickOn("#radioButton"+(rightAnswer+1));
        clickOn("#submitButton");
        assertEquals(quiz.getQuestions().get(0).getQuestion(),controller.getQuestion());
        for(int i = 0; i<quiz.getQuizLength(); i++){
            assertEquals(quiz.getQuestions().get(0).getChoice(i),controller.getListOfAnswers().get(0));
        }
        assertEquals(rightAnswer, quiz.getQuestions().get(0).getAnswer());
    }

    @AfterAll
    public static void deleteFile(){
        String fileName = System.getProperty("user.home") + "/QuizApp/testNewQuestion.json";
        try {
            Files.delete(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeQuestion(String question, String choice1, String choice2, String choice3, String choice4, int rightAnswer){
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.rightAnswer = rightAnswer;
    }
}