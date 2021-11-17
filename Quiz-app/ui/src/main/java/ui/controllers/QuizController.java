package ui.controllers;

import core.Question;
import core.QuizSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import ui.APIClientService;
import ui.App;

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

    private final QuizSession quizSession;
    private List<RadioButton> radios;
    private APIClientService apiClientService = new APIClientService();

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
     * @param quizName the name of the quiz
     */
    public QuizController(String quizName) throws IOException, InterruptedException {
        quizSession = new QuizSession(apiClientService.getQuiz(quizName));
    }

    /**
     * Displays the current question to the GUI
     */
    @FXML
    public void displayQuestion() throws IOException {
        if (!quizSession.hasCurrent()) {
            endQuiz();
            return;
        }
        Question q = quizSession.getCurrentQuestion();
        submitAnswer.setDisable(true);
        questionLabel.setText(q.getQuestion());
        for (int i = 0; i < radios.size(); i++) {
            radios.get(i).setText(q.getChoice(i));
        }
    }

    /**
     * Submits the selected answer and progresses to the next question
     *
     * @throws IOException
     */
    @FXML
    public void submitQuestion() throws IOException {
        quizSession.submitAnswer(option.getToggles().indexOf(option.getSelectedToggle()));
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
        ResultPageController controller = new ResultPageController(quizSession.getNumberOfCorrect(),
                quizSession.getQuizLength(), quizSession.getQuizName());
        loader.setController(controller);
        submitAnswer.getScene().setRoot(loader.load());
    }


}
