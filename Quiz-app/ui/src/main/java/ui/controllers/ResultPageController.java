package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import ui.Utilities;

import java.io.IOException;

public class ResultPageController {
    @FXML
    private Label result;

    private final int score;
    private final int maxScore;
    private final String name;
    private APIClientService apiClientService;

    /**
     * Takes in the score and maxScore of a completed quiz
     *
     * @param score    the achieved score
     * @param maxScore the highest possible score
     * @param quizName the name of the quiz
     */
    public ResultPageController(int score, int maxScore, String quizName) {
        this.score = score;
        this.maxScore = maxScore;
        this.name = quizName;
    }

    /**
     * Displays the scores to the GUI
     */
    public void initialize() {
        result.setText("Du fikk %s/%s poeng!".formatted(score, maxScore));
    }

    /**
     * Sets the current root to be the homepage
     *
     * @param actionEvent
     * @throws IOException
     */
    public void returnToHomePage(ActionEvent actionEvent) throws IOException {
        ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("HomePage.fxml").load());
    }
}
