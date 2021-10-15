package ui;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import core.Quiz;
import io.QuizPersistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


import ui.controllers.NewQuestionController;

import static org.junit.jupiter.api.Assertions.*;

public class NewQuestionControllerTest extends ApplicationTest {

    private NewQuestionController controller;
    private Map<String, Object> fxmlNamespace;
    private List<TextField> textFieldList = new ArrayList<>();
    private Stage stage;

    @Override
    public void start(final Stage stage) throws Exception {
        quizPersistence = new QuizPersistence();
        this.stage = stage;
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("NewQuestionTest.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        this.fxmlNamespace = loader.getNamespace();
        toggleGroupRadioButton = (ToggleGroup) fxmlNamespace.get("$radioButton");
        for(int i=0; i<4; i++){
            this.textFieldList.add((TextField) fxmlNamespace.get("choice"+i));
        }
        submitButton = lookup("#submitQuestion").query();
        stage.setScene(new Scene(root));
        stage.show();


    }

    private QuizPersistence quizPersistence;
    private String question;
    private String choice1, choice2, choice3, choice4;
    private int rightAnswer;
    private ToggleGroup toggleGroupRadioButton;
    private Button submitButton;
    private Quiz quiz = null;

    @BeforeEach
    public void setupItems() {
        try {
            quiz = quizPersistence.readQuiz(new InputStreamReader(getClass().getResourceAsStream("test-newQuestion.json")));
        } catch (IOException e) {
            fail("Couldn't load test-newQuestion.json");
        }
        assertNotNull(quiz);
        writeQuestion("How many days in a year?", "121", "354", "360", "365", 3);
        for(Object radioButton : toggleGroupRadioButton.getToggles()){
            ((RadioButton) radioButton).setSelected(false);
        }
    }

    @Test
    public void testSubmitUncheckedRadio() {
        Assertions.assertTrue(submitButton.isDisabled());
        /*
        Check if the root is the same
         */
        clickOn("#submitButton");
        assertEquals("NewQuestionTest.fxml", stage.getScene().getRoot());

    }

    @Test
    public void testSubmitEmptyFields() {
        toggleGroupRadioButton.getToggles().get(0).setSelected(true);
        for(TextField textField : textFieldList){
            textField.setText("");
        }
        Assertions.assertThrows(IllegalArgumentException.class, () -> {clickOn("#submitButton");});
    }

    @Test
    public void testQuestionList(){
        clickOn("#questionText").write(question);
        clickOn("#choice1").write(choice1);
        clickOn("#choice2").write(choice2);
        clickOn("#choice3").write(choice3);
        clickOn("#choice4").write(choice4);
        toggleGroupRadioButton.getToggles().get(3).setSelected(true);
        clickOn("#submitButton");
        assertEquals(quiz.getQuestions().get(0).getQuestion(),controller.getQuestion());
        for(int i = 0; i<quiz.getQuizLength(); i++){
            assertEquals(quiz.getQuestions().get(0).getChoice(i),controller.getListOfAnswers().get(0));
        }
        assertEquals(quiz.getCorrect(),controller.getCheckedId()-1);


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