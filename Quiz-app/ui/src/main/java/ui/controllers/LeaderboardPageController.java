package ui.controllers;

import core.Leaderboard;
import core.Score;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.APIClientService;
import ui.User;

import java.io.IOException;

public class LeaderboardPageController extends GoBackController {

    @FXML
    private Label titleText;
    @FXML
    private VBox leaderboardList;
    @FXML
    private Button backButton;

    private final String quizName;
    private Leaderboard leaderboard;
    private APIClientService apiClientService;

    /**
     * @param quizName the name of the quiz which leaderboard we are viewing
     * @param user     the current user
     */
    public LeaderboardPageController(String quizName, User user) {
        super(user);
        this.quizName = quizName;
    }

    /**
     * initializes the page by filling in score rows and displaying name
     *
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException, InterruptedException {
        setBackButton(backButton);
        apiClientService = new APIClientService();
        display();
    }

    private void display() throws IOException, InterruptedException {
        leaderboardList.getChildren().clear();
        leaderboard = apiClientService.getLeaderboard(quizName);
        titleText.setText(quizName);
        for (int i = 0; i < leaderboard.getScoreLength(); i++) {
            addScoreElement(i);
        }
    }

    /**
     * adds a gui element representing a score for the quiz
     *
     * @param scoreId the id of the score
     */
    private void addScoreElement(int scoreId) {
        Score score = leaderboard.getSortedScores().get(scoreId);
        GridPane gridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(250);
        ColumnConstraints column3 = new ColumnConstraints(250);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);

        Label position = new Label();
        position.setText("" + (scoreId + 1));
        gridPane.add(position, 0, 0, 1, 1);

        Label name = new Label();
        name.setText(score.getName());
        gridPane.add(name, 1, 0, 1, 1);

        Label points = new Label();
        points.setText(score.getPoints() + "/" + leaderboard.getMaxScore());
        gridPane.add(points, 2, 0, 1, 1);

        gridPane.getStyleClass().add("light-grid");
        leaderboardList.getChildren().add(gridPane);
    }

}
