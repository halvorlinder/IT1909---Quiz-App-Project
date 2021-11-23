package ui.controllers;

import core.Score;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import ui.APIClientService;
import ui.User;
import ui.Utilities;
import ui.constants.FilePaths;

import java.io.IOException;

public class ResultPageController extends BaseController {
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
     * @param user     the current user
     */
    public ResultPageController(int score, int maxScore, String quizName, User user) {
        super(user);
        this.score = score;
        this.maxScore = maxScore;
        this.name = quizName;
    }

    /**
     * Displays the scores to the GUI
     */
    public void initialize() throws IOException {
        apiClientService = new APIClientService();
        apiClientService.postScore(name, new Score(getUser().getUsername(), score));
        result.setText("Du fikk %s/%s poeng!".formatted(score, maxScore));
    }

    /**
     * Sets the current root to be the homepage
     *
     * @param actionEvent
     * @throws IOException
     */
    public void returnToHomePage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = Utilities.getFXMLLoader(FilePaths.HOME_PAGE);
        loader.setController(new HomePageController(getUser()));
        ((Node) actionEvent.getSource()).getScene().setRoot(loader.load());
    }
}
