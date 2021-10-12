package ui.controllers;

import core.Question;
import core.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import ui.App;
import ui.Utilities;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class QuizController {
    @FXML
    private ToggleGroup option;
    @FXML
    private Label questionLabel;
    @FXML
    private RadioButton option1;
    @FXML
    private RadioButton option2;
    @FXML
    private RadioButton option3;
    @FXML
    private RadioButton option4;
    @FXML
    private Button submitAnswer;

    private final Quiz quiz;
    private List<RadioButton> radios;
    private int selected;

    /**
     * Initializes a new quiz
     *
     * @throws IOException
     */
    public void initialize() throws IOException {
        radios = Arrays.asList(option1, option2, option3, option4);
        radios.forEach(radio -> radio.setOnAction(ae -> submitAnswer.setDisable(false)));
        displayQuestion();
    }

    /**
     *
     * @param quiz the quiz presented to the user
     */
    public QuizController(Quiz quiz) {
        this.quiz = quiz;
    }

    /**
     * Displays the current question to the GUI
     *
     * @throws IOException
     */
    @FXML
    public void displayQuestion() throws IOException {
        Question q = quiz.getCurrentQuestion();
        submitAnswer.setDisable(true);
        if (q == null)
            endQuiz();
        else {
            questionLabel.setText(q.getQuestion());
            for (int i = 0; i < radios.size(); i++) {
                radios.get(i).setText(q.getChoice(i));
            }
        }
    }

    /**
     * Submits the selected answer and progresses to the next question
     *
     * @throws IOException
     */
    @FXML
    public void submitQuestion() throws IOException {
        quiz.submitAnswer(option.getToggles().indexOf(option.getSelectedToggle()));
        radios.forEach(radioButton -> radioButton.setSelected(false));
        displayQuestion();
    }

    /**
     * Ends the quiz by going to the result page
     *
     * @throws IOException
     */
    private void endQuiz() throws IOException {
        FXMLLoader loader = App.getFXMLLoader("ResultPage.fxml");
        ResultPageController controller = new ResultPageController(quiz.getCorrect(), quiz.getQuizLength());
        loader.setController(controller);
        submitAnswer.getScene().setRoot(Utilities.getFXMLLoader("HomePage.fxml").load());
    }


}
