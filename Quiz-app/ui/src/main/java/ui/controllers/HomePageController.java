package ui.controllers;

import core.Question;
import core.Quiz;
import core.User;
import io.SavePaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.APIClientService;
import ui.App;
import ui.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class HomePageController {

    private static final String BASE_PATH = SavePaths.getBasePath() + "Quizzes/";

    @FXML
    private Button addNewQuizButton;

    @FXML
    private Label nameDisplay;

    @FXML
    private VBox quizList;

    @FXML
    private TextField quizNameField;

    private List<String> quizzes = new ArrayList<>();

    private APIClientService apiClientService;

    /**
     * sets the text to display username
     */
    @FXML
    public void initialize() throws IOException, InterruptedException {
        nameDisplay.setText("Logget inn som " + User.getUserName());
        apiClientService = new APIClientService();
        updateInitialQuizzes();
    }

    /**
     * adds all quiz names to a list so that they can be rendered
     */
    private void updateInitialQuizzes() throws IOException, InterruptedException {
        quizList.getChildren().clear();
        quizzes = apiClientService.getListOfQuizNames();
        for (String quizName : quizzes) {
            addQuizElement(quizName);
        }
    }

    /**
     * displays a quiz as a row on the page
     *
     * @param quizName the name of the quiz
     */
    private void addQuizElement(String quizName) {
        GridPane gridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints(300);
        ColumnConstraints column2 = new ColumnConstraints(100);
        ColumnConstraints column3 = new ColumnConstraints(100);
        ColumnConstraints column4 = new ColumnConstraints(100);
        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4);
        gridPane.getStyleClass().add("light-grid");
        Label name = new Label();
        name.setText(quizName);
        gridPane.add(name, 0, 0, 1, 1);

        Button playButton = new Button();
        playButton.setText("Spill");
        playButton.setOnAction((ActionEvent ae) -> startQuiz(quizName));
        playButton.getStyleClass().add("green-button");
        gridPane.add(playButton, 1, 0, 1, 1);

        Button editButton = new Button();
        editButton.setText("Endre");
        editButton.setOnAction((ActionEvent ae) -> showEditPage(quizName));
        editButton.getStyleClass().add("red-button");
        gridPane.add(editButton, 2, 0, 1, 1);

        Button leaderboardButton = new Button();
        leaderboardButton.setText("Ledertavle");
        leaderboardButton.getStyleClass().add("blue-button");
        leaderboardButton.setOnAction((ActionEvent ae) -> showLeaderboardPage(quizName));
        gridPane.add(leaderboardButton, 3, 0, 1, 1);

        quizList.getChildren().add(gridPane);
    }

    /**
     * displays the edit page for a given quiz
     *
     * @param quizName the name of the quiz
     */
    private void showEditPage(String quizName) {
        try {
            FXMLLoader loader = App.getFXMLLoader("EditPage.fxml");
            EditPageController controller = new EditPageController(quizName);
            loader.setController(controller);
            quizList.getScene().setRoot(loader.load());
        } catch (IOException ioException) {
            ioException.printStackTrace();
            Utilities.alertUser();
        }
    }

    /**
     * displays the leaderboard page for a given quiz
     *
     * @param quizName the name of the quiz
     */
    private void showLeaderboardPage(String quizName) {
        try {
            FXMLLoader loader = App.getFXMLLoader("Leaderboard.fxml");
            LeaderboardController controller = new LeaderboardController(quizName);
            loader.setController(controller);
            quizList.getScene().setRoot(loader.load());
        } catch (IOException ioException) {
            ioException.printStackTrace();
            Utilities.alertUser();
        }
    }

    /**
     * Sets the current root to be the question page
     *
     * @param quizName the name of the quiz to be played
     */
    @FXML
    public void startQuiz(String quizName) { // Switch scene to StartQuiz
        try {
            Quiz quiz = apiClientService.getQuiz(quizName);
            if (quiz.getQuizLength() == 0) {
                Utilities.alertUser("Denne quizen har ingen spørsmål");
                return;
            }
            FXMLLoader loader = App.getFXMLLoader("QuestionPage.fxml");
            QuizController controller = new QuizController(quiz);
            loader.setController(controller);
            quizList.getScene().setRoot(loader.load());
        } catch (IOException | InterruptedException ioException) {
            Utilities.alertUser();
        }
    }


    /**
     * Creates a new quiz file with a given name and displays it in the app
     *
     * @throws IOException
     */
    @FXML
    public void addNewQuizFile() throws IOException, InterruptedException {
        String newQuizName = quizNameField.getText();
        if (newQuizName.isEmpty()) {
            throw new IllegalArgumentException("You can't create a quiz with an empty name");
        }
        List<Question> noQuestions = new ArrayList<>();
        Quiz newQuiz = new Quiz(newQuizName, noQuestions);
        apiClientService.postQuiz(newQuiz);
        // apiClientService.postLeaderboard(newLeaderboard);
        updateInitialQuizzes();
    }

    /**
     * signs out of the application
     *
     * @param actionEvent
     */
    @FXML
    public void signOut(ActionEvent actionEvent) {
        try {
            final FXMLLoader loader = Utilities.getFXMLLoader("LogInPage.fxml");
            LogInController controller = new LogInController();
            loader.setController(controller);
            final Parent root = loader.load();
            // Scene scene = new Scene(root);
            ((Node) actionEvent.getSource()).getScene().setRoot(root);
            // ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("LogInPage.fxml").load());
            User.setUserName(null);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
