package ui.controllers;

import core.Quiz;
import core.User;
import io.QuizPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ui.App;
import ui.Utilities;

import java.io.IOException;


public final class HomePageController {

    @FXML
    private Button startQuizButton;

    @FXML
    private Button newQuestionButton;

    @FXML
    private Button leaderboardButton;

    @FXML
    private Label nameDisplay;

    /**
     * sets the text to display username
     */
    @FXML
    public void initialize() {
        nameDisplay.setText("Logget inn som " + User.getUserName());
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
        Quiz quiz = quizPersistence.loadQuiz("quiz101");
        if (quiz.getQuizLength() == 0)
            return;
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

        ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("NewQuestion.fxml").load());
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
     * signs out of the application
     * @param actionEvent
     */
    @FXML
    public void signOut(ActionEvent actionEvent) {
        try {
            ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("LogInPage.fxml").load());

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
