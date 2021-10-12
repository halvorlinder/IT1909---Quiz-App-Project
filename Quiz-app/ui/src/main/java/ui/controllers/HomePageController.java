package ui.controllers;

import core.Quiz;
import io.QuizStorageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import ui.App;
import ui.Utilities;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Button startQuizButton;

    @FXML
    private Button newQuestionButton;

    @FXML
    private Button leaderboardButton;

    // App.setRoot needs to be completed
    // All FXML files need to be created and named accordingly

    /**
     * Sets the current root to be the question page
     *
     * @throws IOException
     */
    @FXML
    public void showStartQuiz() throws IOException { // Switch scene to StartQuiz
        QuizStorageHandler quizStorageHandler = new QuizStorageHandler("quiz101");
        Quiz quiz = quizStorageHandler.getQuiz();
        if (quiz.getQuizLength() == 0)
            return;
        FXMLLoader loader = App.getFXMLLoader("QuestionPage.fxml");
        QuizController controller = new QuizController(quiz);
        loader.setController(controller);
        newQuestionButton.getScene().setRoot(loader.load());
    }

    /**
     * Sets the current root to be the new question page
     *
     * @throws IOException
     */
    @FXML
    public void showNewQuestion(ActionEvent actionEvent) throws IOException { // Switch scene to StartQuiz

        newQuestionButton.getScene().setRoot(Utilities.getFXMLLoader("NewQuestion.fxml").load());
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


}
