package ui.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ui.App;

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
     * @throws IOException
     */
    @FXML
    public void showStartQuiz() throws IOException { // Switch scene to StartQuiz
        App.setRoot("QuestionPage.fxml");
    }

    /**
     * Sets the current root to be the new question page
     * @throws IOException
     */
    @FXML
    public void showNewQuestion() throws IOException { // Switch scene to StartQuiz
        App.setRoot("NewQuestion.FXML");
    }

    /**
     * Sets the current root to be the leaderboard page
     * @throws IOException
     */
    @FXML
    public void showLeaderboard() throws IOException { // Switch scene to StartQuiz
        App.setRoot("Leaderboard.FXML");
    }



}
