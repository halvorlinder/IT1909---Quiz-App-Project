package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import ui.App;
import ui.Question;
import ui.Quiz;

import java.io.IOException;
import java.util.ArrayList;
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

    private Quiz quiz;
    private List<RadioButton> radios;
    private int selected;

    /**
     * Initializes a new quiz
     * @throws IOException
     */
    public void initialize() throws IOException {
        //TODO Get data from the backend instead of hard coding
        radios = Arrays.asList(option1, option2, option3, option4);
        radios.forEach(radio -> radio.setOnAction(ae -> submitAnswer.setDisable(false)));
        Question q1 = new Question("Hva heter den??", new ArrayList<String>(Arrays.asList("a", "b", "c", "d")), 2);
        Question q2 = new Question("Hva heter den andre??", new ArrayList<String>(Arrays.asList("1", "2", "3", "4")), 2);
        quiz = new Quiz(List.of(new Question[]{q1, q2}));
        displayQuestion();
    }

    /**
     * Displays the current question to the GUI
     * @throws IOException
     */
    @FXML
    public void displayQuestion() throws IOException {
        Question q = quiz.getCurrentQuestion();
        submitAnswer.setDisable(true);
        if (q==null)
            endQuiz();
        else{
            questionLabel.setText(q.getQuestion());
            for(int i = 0; i<radios.size(); i++){
                radios.get(i).setText(q.getChoice(i));
            }
        }
    }

    /**
     * Submits the selected answer and progresses to the next question
     * @throws IOException
     */
    @FXML
    public void submitQuestion() throws IOException {
        quiz.submitQuestion(option.getToggles().indexOf(option.getSelectedToggle()));
        radios.forEach(radioButton -> radioButton.setSelected(false));
        displayQuestion();
    }

    /**
     * Ends the quiz by returning to the home page
     * @throws IOException
     */
    private void endQuiz() throws IOException {
        FXMLLoader loader = App.getFXMLLoader("ResultPage.fxml");
        ResultPageController controller = new ResultPageController(quiz.getCorrect(), quiz.getQuizLength());
        loader.setController(controller);
        App.setRoot(loader);
    }




}
