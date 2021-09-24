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

    @FXML
    public void showStartQuiz(ActionEvent event) throws IOException { // Switch scene to StartQuiz
        App.setRoot("QuestionPage.fxml");
    }

    @FXML
    public void showNewQuestion(ActionEvent event) throws IOException { // Switch scene to StartQuiz
        App.setRoot("NewQuestion.FXML");
    }

    @FXML
    public void showLeaderboard(ActionEvent event) throws IOException { // Switch scene to StartQuiz
        App.setRoot("Leaderboard.FXML");
    }



}
