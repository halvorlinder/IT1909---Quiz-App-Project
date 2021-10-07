package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ui.App;

import java.io.IOException;

public class ResultPageController {
    @FXML
    private Label result;

    private final int score;
    private final int maxScore;

    /**
     * Takes in the score and maxScore of a completed quiz
     *
     * @param score    the achieved score
     * @param maxScore the highest possible score
     */
    public ResultPageController(int score, int maxScore) {
        this.score = score;
        this.maxScore = maxScore;
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
     * @throws IOException
     */
    public void returnToHomePage() throws IOException {
        App.setRoot("HomePage.fxml");
    }
}
