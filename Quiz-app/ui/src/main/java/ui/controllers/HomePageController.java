package ui.controllers;

import core.Question;
import core.Quiz;
import core.User;
import io.QuizPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.App;
import ui.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class HomePageController {

    private static final String BASE_PATH = System.getProperty("user.home") + "/QuizApp/Quizzes/";

    @FXML
    private Button startQuizButton;

    @FXML
    private Button newQuestionButton;

    @FXML
    private Button leaderboardButton;

    @FXML
    private Button addNewQuizButton;

    @FXML
    private TextField questionNameField;

    @FXML
    private Label nameDisplay;

    @FXML
    private ChoiceBox choiceBox;

    /**
     * sets the text to display username
     */
    @FXML
    public void initialize() {

        nameDisplay.setText("Logget inn som " + User.getUserName());
        updateQuizzes();
    }

    private void updateQuizzes() {
        File[] files = new File(BASE_PATH).listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile()) {
                choiceBox.getItems().add(file.getName().replaceFirst("[.][^.]+$", ""));
            }
        }
    }

    /**
     * Sets the current root to be the question page
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void showStartQuiz(ActionEvent actionEvent) throws IOException { // Switch scene to StartQuiz
        QuizPersistence quizPersistence = new QuizPersistence();
        String currentQuiz = (String) choiceBox.getSelectionModel().getSelectedItem();
        System.out.println(currentQuiz);
        if (currentQuiz == null) {
            // TODO Alert user that a quiz needs to be selected
            return;
        }
        Quiz quiz = quizPersistence.loadQuiz(currentQuiz);
        if (quiz.getQuizLength() == 0) {
            // TODO Alert user that quiz is too short
            return;
        }
        FXMLLoader loader = App.getFXMLLoader("QuestionPage.fxml");
        QuizController controller = new QuizController(quiz);
        loader.setController(controller);
        ((Node) actionEvent.getSource()).getScene().setRoot(loader.load());
    }

    /**
     * Sets the current root to be the new question page
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void showNewQuestion(ActionEvent actionEvent) throws IOException { // Switch scene to StartQuiz
        String currentQuiz = (String) choiceBox.getSelectionModel().getSelectedItem();
        System.out.println(currentQuiz);
        if (currentQuiz == null) {
            // TODO Alert user that a quiz needs to be selected
            return;
        }
        FXMLLoader loader = App.getFXMLLoader("NewQuestion.fxml");
        NewQuestionController controller = new NewQuestionController(currentQuiz);
        loader.setController(controller);
        ((Node) actionEvent.getSource()).getScene().setRoot(loader.load());
    }

    /**
     * Sets the current root to be the leaderboard page
     *
     * @throws IOException
     */
    @FXML
    public void showLeaderboard() throws IOException { // Switch scene to StartQuiz
        //newQuestionButton.getScene().setRoot(Utilities.getFXMLLoader(".fxml").load());
    }

    /**
     * Creates a new quiz with a given name
     *
     * @param actionEvent
     * @throws IOException
     */

    // TODO Make sure that the name passed is suitable as a file name

    @FXML
    public void addNewQuiz(ActionEvent actionEvent) throws IOException {
        System.out.println(questionNameField.getText());
        if (questionNameField.getText().isEmpty()) {
            // TODO Alert user that they cant add a quiz with that name
            return;
        }
        String newQuizName = questionNameField.getText();
        List<Question> noQuestions = new ArrayList<>();
        Quiz newQuiz = new Quiz(newQuizName, noQuestions);
        QuizPersistence quizPersistence = new QuizPersistence();
        quizPersistence.saveQuiz(newQuiz);
        updateQuizzes();
    }

    /**
     * signs out of the application
     * @param actionEvent
     */
    @FXML
    public void signOut(ActionEvent actionEvent) {
        try {
            ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("LogInPage.fxml").load());
            User.setUserName(null);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
