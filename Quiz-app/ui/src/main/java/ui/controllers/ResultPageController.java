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

    public ResultPageController(int score, int maxScore){
        this.score = score;
        this.maxScore = maxScore;
    }

    public void initialize(){
        result.setText("Du fikk %s/%s poeng!".formatted(score, maxScore));
    }

    public void returnToHomePage() throws IOException {
        App.setRoot("HomePage.fxml");
    }
}
